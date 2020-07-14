package com.airoha.libfota.core;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.core.GattStateListener;
import com.airoha.btdlib.util.AirohaLog;
import com.airoha.btdlib.util.ByteHelper;
import com.airoha.libfota.constant.AirohaOtaUUID;
import com.airoha.libfota.constant.BinAddrs;
import com.airoha.libfota.constant.ErrorCodeManager;
import com.airoha.libfota.constant.FlashAddress;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;

/**
 * {@link AirohaLink} holds this. SDK users don't need to call it directly.
 */
public class AirohaOtaMgr implements GattStateListener {

    private static final String TAG = "AirohaOtaMgr";

    private final AirohaLink mAirohaLink;

    private AirohaOtaWAResolver mAirohaOtaWorkingAreaResolver;

    private FotaTask mCurrentFotaTask;
    private Queue<FotaTask> mTaskQueue;

    private String mOtaBinFileName = "/sdcard/Airoha1600OTA.bin";
    private byte[] mOtaBinFileRaw;

    private FlashAddress mFlashAddress;

    /**
     *  Length: 4 bytes
     */
    private static final int BIN_LENGTH_OFFSET = 4;

    private int mCmdMaxDataLength = 14;  // MTU=20, 20- 6(header)

    /**
     * offset: BIN_START_OFFSET + 4
     * 4bytes, little endian
     */
    private final byte[] mBinBytesArea1Length = new byte[4];
    /**
     * offset: BIN_START_OFFSET + 4 + 4
     * 4bytes, little endian
     */
    private final byte[] mBinBytesArea1Rev = new byte[4];

    /**
     * offset: BIN_START_OFFSET + mIntAreaLength
     * 4bytes, little endian
     */
    private final byte[] mBinBytesArea2Length = new byte[4];

    /**
     * offset: BIN_START_OFFSET + mIntAreaLength + 4
     * 4bytes, little endian
     */
    private final byte[] mBinBytesArea2Rev = new byte[4];


    /**
     * @see #handleCheckBootCodeVer(byte[])
     */
    private byte[] mBinBootCodeVer; // bin file 0x003F8~0x003FB

    //    private final int BIN_FIXED_CODE_AREA_1_START = 0x10000;

    private int mBinCodeArea1Addr;
    private int mBinCodeArea2Addr;

    /**
     * @see #handleCheckBootCodeVer(byte[])
     */
    private byte[] mCurrnetBootCodeVer;

    private int mCurrentCodeArea1Addr;
    private int mCurrentCodeArea2Addr;

    /**
     * @see #handleCheckBootCodeVer(byte[])
     */
    private boolean mIsCheckBootCodeSupported = false;

    private boolean mIsCheckCodeAreaSupported = false;

    private final Object mWaitLock = new Object();

    private static final int TIMEOUT = 3000;

    private Thread mStartThread;

    /**
     *
     */
    private byte[] mBinArea1Data;
    /**
     *
     */
    private byte[] mBinArea2Data;


    private byte[] mBinArea1CheckSum;
    private byte[] mBinArea2CheckSum;
    private int mBinArea1Length;
    private long mBinLongArea1Rev;
    private long mBinLongArea2Rev;
    private int mBinArea2Length;
    private int mSlidingWindow = 3;

    private BluetoothGattCharacteristic mTargetWriteCharc;
    private BluetoothGattCharacteristic mBatteryLevelCharc;

    // flow control
    private Timer mTimerForRespTimeout;

    private static final int AIROHA_1600_MTU = 115;

    @Override
    public void onGattConnected(BluetoothGatt gatt) {

    }

    @Override
    public void onGattDisconnected(BluetoothGatt gatt) {
        cleanForDisconnection();

        mOnAirohaOtaEventListener.OnGattDisconnected();
    }

    @Override
    public void onRequestMtuChangeStatus(boolean isAccepted) {
        mOnAirohaOtaEventListener.OnRequestMtuChangeStatus(isAccepted);
    }

    @Override
    public void onNewMtu(int mtu) {
        mOnAirohaOtaEventListener.OnNewMtu(mtu);

        setCmdMaxDataLength(mtu);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.d(TAG, "onServicesDiscovered" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            List<BluetoothGattService> tmp = gatt.getServices();
            SearchAirohaOTAServiceThread thread = new SearchAirohaOTAServiceThread(tmp);
            Log.d(TAG, "SearchAirohaOTAServiceThread start");
            thread.start();
        } else {
            Log.w(TAG, "onServicesDiscovered received: " + status);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        if(characteristic.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.DEFAULT_OTA_NOTIFY_CHARC_UUID)) {

            final byte[] data = characteristic.getValue();

            handleCharcChanged(data);
        }


        if(characteristic.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.SIG_BATTERY_CHARC_UUID)){
            byte[] value = characteristic.getValue();

            if(value!=null) {
                Log.d(TAG, "battery level:" + ByteHelper.toHex(value));

                int batteryLevel = characteristic.getIntValue(FORMAT_UINT8, 0);
                mOnAirohaOtaEventListener.OnBatteryLevel(batteryLevel);
            }
        }
    }


    /**
     * set the Bin File's full path before calling {@link #startFota()} }. If not set, the default value will be "/sdcard/Airoha1600OTA.bin"
     *
     * @param str the full file path
     */
    public void setOtaBinFileName(String str){
        mOtaBinFileName = str;
    }

    /**
     * set the Bin File's raw data before calling {@link #startFota()} }."
     *
     * @param raw the full file path
     */
    public void setOtaBinFileRaw(byte[] raw){
        mOtaBinFileRaw = raw;
    }

    private OnAirohaOtaEventListener mOnAirohaOtaEventListener;

    public OnAirohaOtaEventListener getEventListener() {
        return mOnAirohaOtaEventListener;
    }

    private static byte[] getCheckSum(byte[] binBytes){
        byte[] sum = new byte[4];
        int cycles = binBytes.length/4;
        for (int i = 0; i < cycles; i++){
            // take 4 bytes at once
            byte[] temp = new byte[4];
            System.arraycopy(binBytes, i*4,  temp, 0, 4);
            sum = ByteHelper.addLittleEndian(sum, temp);
        }
        return sum;
    }


    private int mTargetArea = 0;

    public AirohaOtaMgr(AirohaLink airohaLink) {
        mAirohaLink = airohaLink;

        mAirohaLink.addGattStateListener(TAG, this);
    }

    public void setListener(@NonNull OnAirohaOtaEventListener otaEventListener){
        mOnAirohaOtaEventListener = otaEventListener;
    }


    public String getWorkingArea(){
        return ""+ mAirohaOtaWorkingAreaResolver.mWorkingArea;
    }

    private String getArea1Rev(){
        return mAirohaOtaWorkingAreaResolver.getArea1Rev();
    }

    private String getArea2Rev(){
        return mAirohaOtaWorkingAreaResolver.getArea2Rev();
    }

    private boolean getArea1Stat(){
        return mAirohaOtaWorkingAreaResolver.getArea1Stat();
    }

    private boolean getArea2Stat(){
        return mAirohaOtaWorkingAreaResolver.getArea2Stat();
    }

    public boolean isBinFileInfoParsed(byte[] raw) {

//        try {
            //final byte[] otaBinFileBytes = Files.toByteArray(new File(filename));
            final byte[] otaBinFileBytes = raw;

            // 2017.03.28 Daniel check boot code
            mBinBootCodeVer = new byte[4];
            System.arraycopy(otaBinFileBytes, BinAddrs.BIN_BOOTCODE_START_ADDR, mBinBootCodeVer, 0, 4);
            Log.d(TAG, "Bin Boot Code Ver: " + ByteHelper.toHex(mBinBootCodeVer));

            // 2017.05.23 Daniel check code section
            // Read fixed position of FW Bin file to calculate (- 0x80000)
            // Code Section1 Start:  read 0x25E7->  0x90000 - 0x80000 = 0x10000
            byte[] bytesCode1Addr = new byte[4];
            System.arraycopy(otaBinFileBytes, BinAddrs.BIN_CODE_AREA_1_ADDR, bytesCode1Addr, 0, 4);
            mBinCodeArea1Addr = ByteHelper.bytesToInt(bytesCode1Addr) - BinAddrs.getBinCodeAreraAddrOffset();
            // Code Section2 Start:  read 0x25F0->  0xC8000 - 0x80000 = 0x48000
            byte[] bytesCode2Addr = new byte[4];
            System.arraycopy(otaBinFileBytes, BinAddrs.BIN_CODE_AREA_2_ADDR, bytesCode2Addr, 0,4);
            mBinCodeArea2Addr = ByteHelper.bytesToInt(bytesCode2Addr) - BinAddrs.getBinCodeAreraAddrOffset();
            //-------------------------------------------------------------//

            // At 0x10000, there are Area1 length and Area1 revision number ahead, and their size are 4 bytes.
            // At (0x10000 + Area1 length), there are Area2 length and Area2 revision number ahead, and their size are 4 bytes.
                System.arraycopy(otaBinFileBytes, mBinCodeArea1Addr, mBinBytesArea1Length, 0, 4);

            mBinArea1Length = ByteHelper.bytesToInt(mBinBytesArea1Length);
            Log.d(TAG, "Bin Area 1 length:" + mBinArea1Length);
            AirohaLog.LogToFile("Bin Area 1 length:" + mBinArea1Length);

            System.arraycopy(otaBinFileBytes, mBinCodeArea1Addr + BIN_LENGTH_OFFSET, mBinBytesArea1Rev, 0, 4);
            ByteHelper.reverseBytes(mBinBytesArea1Rev);
            mBinLongArea1Rev = ByteHelper.bytesToLong(mBinBytesArea1Rev);
            Log.d(TAG, "Bin Area 1 rev:" + mBinLongArea1Rev);
            AirohaLog.LogToFile("Bin Area 1 rev:" + mBinLongArea1Rev);

            final int binAddrArea2Start = mBinCodeArea1Addr + mBinArea1Length;
            Log.d(TAG, "Bin Area 2 start addr:" + binAddrArea2Start);
            AirohaLog.LogToFile("Bin Area 2 start addr:" + binAddrArea2Start);

            System.arraycopy(otaBinFileBytes, binAddrArea2Start, mBinBytesArea2Length, 0, 4);

            mBinArea2Length = ByteHelper.bytesToInt(mBinBytesArea2Length);
            Log.d(TAG, "Bin Area 2 length:" + mBinArea2Length);
            AirohaLog.LogToFile("Bin Area 2 length:" + mBinArea2Length);

            System.arraycopy(otaBinFileBytes, binAddrArea2Start + BIN_LENGTH_OFFSET, mBinBytesArea2Rev, 0, 4);
            ByteHelper.reverseBytes(mBinBytesArea2Rev);
            mBinLongArea2Rev = ByteHelper.bytesToLong(mBinBytesArea2Rev);
            Log.d(TAG, "Bin Area 2 rev:" + mBinLongArea2Rev);
            AirohaLog.LogToFile("Bin Area 2 rev:" + mBinLongArea2Rev);

            mFlashAddress = new FlashAddress(mBinCodeArea1Addr, mBinCodeArea2Addr, mBinArea1Length, mBinArea2Length);

//            Log.d(TAG, "Bin Area 1 data size:" + mBinArea1Length);
            mBinArea1Data = new byte[mBinArea1Length];
            System.arraycopy(otaBinFileBytes, mFlashAddress.getFlashArea1Start(), mBinArea1Data, 0, mBinArea1Length);

            mBinArea1CheckSum = getCheckSum(mBinArea1Data);
            Log.d(TAG, "Bin Area 1 checksum:" + ByteHelper.toHex(mBinArea1CheckSum));
            AirohaLog.LogToFile("Bin Area 1 checksum:" + ByteHelper.toHex(mBinArea1CheckSum));

//            Log.d(TAG, "Bin Area 2 data size:" + mBinArea2Length);
            mBinArea2Data = new byte[mBinArea2Length];
            System.arraycopy(otaBinFileBytes, binAddrArea2Start, mBinArea2Data, 0, mBinArea2Length);

            mBinArea2CheckSum = getCheckSum(mBinArea2Data);
            Log.d(TAG, "Bin Area 2 checksum:" + ByteHelper.toHex(mBinArea2CheckSum));
            AirohaLog.LogToFile("Bin Area 2 checksum:" + ByteHelper.toHex(mBinArea2CheckSum));

/*        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IndexOutOfBoundsException e){
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }*/

        return true;
    }

    public FlashAddress getmFlashAddress() {
        return mFlashAddress;
    }

    public byte[] getmBinArea1Data() {
        return mBinArea1Data;
    }

    public byte[] getmBinArea2Data() {
        return mBinArea2Data;
    }

    public byte[] getmBinArea1CheckSum() {
        return mBinArea1CheckSum;
    }

    public byte[] getmBinArea2CheckSum() {
        return mBinArea2CheckSum;
    }

    private boolean isBootCodeCompatible()  {
        // compare
        if(!ByteHelper.compare(mBinBootCodeVer, mCurrnetBootCodeVer)){
            return false;
        }else {
            return true;
        }
    }

    private boolean isCodeAreaAddressCompatible() {
        // compare
        if(mBinCodeArea1Addr == mCurrentCodeArea1Addr && mBinCodeArea2Addr == mCurrentCodeArea2Addr){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Execute FOTA on the non-working area
     */
    public void startFota(){
        if (mStartThread!=null)
            return;

        mStartThread = new Thread(new Runnable() {
            @Override
            public void run() {

                if(mAirohaOtaWorkingAreaResolver.mWorkingArea == 0x01){
                    // set to update to 2
                    mTargetArea = 2;
                }else if(mAirohaOtaWorkingAreaResolver.mWorkingArea == 0x02){
                    // set to update to 2
                    mTargetArea = 1;
                }else {
                    // error, wrong
                    return;
                }

                if(isBinFileInfoParsed(mOtaBinFileRaw)){
                    Log.d(TAG, "done parsing bin file; querying boot code of FW");
                }else {

                    mOnAirohaOtaEventListener.OnBinFileParseException();

                    return;
                }


                // 2017.03.27 Daniel, new mechanism
                writeCharc(new byte[]{AirohaOtaCmd.CMD_CHECK_BOOTCODE_VER});

                synchronized (mWaitLock){
                    try {
                        mWaitLock.wait(TIMEOUT);
                        if(mIsCheckBootCodeSupported){
                            if(!isBootCodeCompatible()) {
                                mOnAirohaOtaEventListener.OnHandleBootCodeNotMatching();
                                return;
                            }

                            writeCharc(new byte[]{AirohaOtaCmd.CMD_CHECK_CODE_AREA_ADDR});

                            mWaitLock.wait(TIMEOUT);
                            if(mIsCheckCodeAreaSupported) {
                                if (!isCodeAreaAddressCompatible()) {
                                    mOnAirohaOtaEventListener.OnHandleCodeAreaAddrNotMatching();
                                    return;
                                }
                            }
                        }

                        startEraseProgramTask(mTargetArea);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mStartThread.start();
    }

    private void startEraseProgramTask(int areaNum){
        mTaskQueue = new ConcurrentLinkedQueue<>();
        mTaskQueue.offer(new FotaTaskErase(this, areaNum, mAirohaLink, mSlidingWindow));
        mTaskQueue.offer(new FotaTaskProgram(this, areaNum, mAirohaLink, mSlidingWindow));

        mCurrentFotaTask = mTaskQueue.poll();
        mCurrentFotaTask.startPrePoll();
    }

    /**
     * change working area
     *
     * @param areaNum, should be 1 or 2
     */
    public void changeWorkingArea(int areaNum){
        Log.d(TAG, "Step 3, start change");
        byte bArea = (byte) areaNum;
        byte[] cmd = new byte[]{AirohaOtaCmd.CMD_CHANGE_WORKING_AREA, bArea};

        writeCharc(cmd);
    }

    /**
     * call this FW will apply the changes and auto reset
     */
    public void applyNewFw(){
        Log.d(TAG, "Step 4, apply new fw");

        writeCharc(new byte[]{AirohaOtaCmd.CMD_APPLY_NEW_FW});
    }

    public void handleCharcChanged(byte[] resp){

        Log.d(TAG, "[Rx] handle resp: " + ByteHelper.toHex(resp));

        // 2017.10.30 Daniel: one byte 0xFF to indicate OTA function is disabled
        handleError(resp);

        handleReadStatus(resp);
        handleCheckBootCodeVer(resp);
        handleCheckCodeAreaAddr(resp);
        handleChangeResp(resp);


        if(mCurrentFotaTask != null){
            mCurrentFotaTask.handleResp(resp);

            if(mCurrentFotaTask.isAllRespSuccess()){
                if(mTaskQueue.size() > 0 ){
                    mCurrentFotaTask = mTaskQueue.poll();

                    mCurrentFotaTask.startPrePoll();
                }else {

                    mCurrentFotaTask = null;

                    changeWorkingArea(mTargetArea);
                }
            }
        }
    }

    private void handleReadStatus(final byte[] data) {
        // check length = 20
        if(data==null || data.length!=20)
            return;

        if(data[0]!= AirohaOtaCmd.CMD_READ_STATUS)
            return;

        mAirohaOtaWorkingAreaResolver = new AirohaOtaWAResolver(data);

        mOnAirohaOtaEventListener.OnWorkingAreaStatus(getWorkingArea(), getArea1Rev(), getArea1Stat(), getArea2Rev(), getArea2Stat());
    }

    private void handleError(byte[] resp){
        if(resp[0] == ErrorCodeManager.RES_FAIL){
            // notify the user
            mOnAirohaOtaEventListener.OnHandleOtaDisabled(ErrorCodeManager.RES_FAIL);
        }
    }

    /**
     *  command response: 0x06 ## ## ## ##, little endian
     * @param resp
     */
    private void handleCheckBootCodeVer(byte[] resp) {
        // check length
        if(resp.length != 5)
            return;
        // check header
        if(resp[0] != AirohaOtaCmd.CMD_CHECK_BOOTCODE_VER)
            return;

        mCurrnetBootCodeVer = new byte[4];

        System.arraycopy(resp, 1, mCurrnetBootCodeVer, 0, 4);

        mIsCheckBootCodeSupported = true;
        synchronized (mWaitLock){
            mWaitLock.notify();
        }
    }

    private void handleCheckCodeAreaAddr(byte[] resp){
        // Response format: --- 07 ## ## ## ## @@ @@ @@ @@
        // ## ## ## ##: code 1 start
        // @@ @@ @@ @@: code 2 start

        // check length
        if(resp.length != 9)
            return;
        // check header
        if(resp[0] != AirohaOtaCmd.CMD_CHECK_CODE_AREA_ADDR)
            return;

        byte[] area1Addr = new byte[4];
        System.arraycopy(resp, 1, area1Addr, 0, 4);
        mCurrentCodeArea1Addr = ByteHelper.bytesToInt(area1Addr);

        byte[] area2Addr = new byte[4];
        System.arraycopy(resp, 5, area2Addr, 0, 4);
        mCurrentCodeArea2Addr = ByteHelper.bytesToInt(area2Addr);

        mIsCheckCodeAreaSupported = true;

        synchronized (mWaitLock){
            mWaitLock.notify();
        }
    }


    private void handleChangeResp(byte[] resp){
        if(resp.length != 2)
            return;
        if(resp[0] != AirohaOtaCmd.CMD_CHANGE_WORKING_AREA)
            return;

        if(resp[1] == ErrorCodeManager.RES_SUCCESS){
            Log.d(TAG, "Step 3, finish change");

            mOnAirohaOtaEventListener.OnWorkingAreaChanged();
        }
    }

    public static int getAddressInt(byte[] packet){
        return ByteHelper.bytesToInt(new byte[]{packet[1], packet[2], packet[3], packet[4]});
    }

    public int getCmdMaxDataLength() {
        return mCmdMaxDataLength;
    }

    public void setCmdMaxDataLength(int mtu) {
        this.mCmdMaxDataLength = mtu - 3 - 6;
    }


    class RetryTask extends TimerTask {

        @Override
        public void run() {
            if (!mAirohaLink.isConnected())
                return;

            if(mCurrentFotaTask != null){
                Log.d(TAG, "retry since resp timeout");

                boolean canRetry = mCurrentFotaTask.retry();
                if(!canRetry)
                    mOnAirohaOtaEventListener.OnRetryFailed();
            }
        }
    }

    public void stopRespTimer() {
        if (mTimerForRespTimeout != null) {
            mTimerForRespTimeout.cancel();
        }
    }

    public void startRespTimer() {
        if (mTimerForRespTimeout != null) {
            mTimerForRespTimeout.cancel();
        }

        mTimerForRespTimeout = new Timer();
        mTimerForRespTimeout.schedule(new RetryTask(), TIMEOUT_RACE_CMD_NOT_RESP);
    }

    private int TIMEOUT_RACE_CMD_NOT_RESP = 5000;

    public void cleanForDisconnection() {
        if (mTimerForRespTimeout != null) {
            mTimerForRespTimeout.cancel();
        }

        if(mStartThread != null){
            mStartThread = null;
        }
    }

    public void close() {
        mAirohaLink.removeGattStateListener(TAG);
    }

    public void readStatus() {
        writeCharc(new byte[]{AirohaOtaCmd.CMD_READ_STATUS});
    }

    class SearchAirohaOTAServiceThread extends Thread{
        final List<BluetoothGattService> mmGattServices;

        public SearchAirohaOTAServiceThread(List<BluetoothGattService> gattServices){
            mmGattServices = gattServices;
        }

        @Override
        public void run() {
            Log.d(TAG, "isFoundOTAServiceCharc");
            boolean isOtaSupported = isFoundOTAServiceCharc(mmGattServices);

            Log.d(TAG, "isOtaSupported: " + isOtaSupported);

            if (isOtaSupported) {
                if (Build.VERSION.SDK_INT >= 21) {
                    mAirohaLink.requestChangeMtu(AIROHA_1600_MTU);
                    SystemClock.sleep(1000);
                }

                mOnAirohaOtaEventListener.OnGattConnected();
                readStatus();
            }else {
                mOnAirohaOtaEventListener.OnOtaServiceNotFound();
            }
        }

        private boolean isFoundOTAServiceCharc(List<BluetoothGattService> gattServices) {
            boolean foundWriteCharc = false;
            boolean foundNotifyCharc = false;

            // Loops through available GATT Services.
            for (BluetoothGattService gattService : gattServices) {
                if(gattService.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.DEFAULT_OTA_SERVICE_UUID)) {
                    Log.d(TAG, "DEFAULT_OTA_SERVICE_UUID found");
                    List<BluetoothGattCharacteristic> gattCharacteristics =
                            gattService.getCharacteristics();
                    // Loops through available Characteristics.
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                        if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.DEFAULT_OTA_WRITE_CHARC_UUID)){
                            Log.d(TAG, "DEFAULT_OTA_WRITE_CHARC_UUID found");
                            foundWriteCharc = true;
                            // enable write
                            mTargetWriteCharc = gattCharacteristic;
                        }
                        if(gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.DEFAULT_OTA_NOTIFY_CHARC_UUID)){
                            Log.d(TAG, "DEFAULT_OTA_NOTIFY_CHARC_UUID found");
                            foundNotifyCharc = true;
                            // enable notif
                            boolean result = mAirohaLink.setCharacteristicNotification(gattCharacteristic, true);

                        }
                    }
                }

                // TODO 2018.11.12 testing SIG Battery
                if(gattService.getUuid().toString().equalsIgnoreCase(AirohaOtaUUID.SIG_BATTERY_SERVICE_UUID)) {
                    Log.d(TAG, "SIG_BATTERY_SERVICE_UUID found");
                    mBatteryLevelCharc = gattService.getCharacteristic(UUID.fromString(AirohaOtaUUID.SIG_BATTERY_CHARC_UUID));
                    Log.d(TAG, "SIG_BATTERY_CHARC_UUID found");

                    if(mBatteryLevelCharc!=null){
                        byte[] value = mBatteryLevelCharc.getValue();
                        if(value!=null){
                            Log.d(TAG, "battery level:" + ByteHelper.toHex(value));
                        }
                    }
                }
            }

            if(foundNotifyCharc && foundWriteCharc){
                return true;
            }
            return false;
        }
    }

    public void writeCharc(byte[] value) {
        mAirohaLink.addWriteCharacteristicTask(mTargetWriteCharc, value);
    }

    public boolean isNewFw(byte[] raw) {
        boolean ret = false;
        if(isBinFileInfoParsed(raw)){
            // comparing

            if(mAirohaOtaWorkingAreaResolver.mWorkingArea == 0x01){
                // compare FW Area 1 and Bin Area 1

                long fwBinArea1Rev = mAirohaOtaWorkingAreaResolver.getLongArea1Rev();

                Log.d(TAG, "fwBinArea1Rev:" + fwBinArea1Rev);
                Log.d(TAG, "fileBinArea1Rev:" + mBinLongArea1Rev);
                if(mBinLongArea1Rev >= fwBinArea1Rev){
                    ret = true;
                }else {
                    ret = false;
                }
            }else {
                // compare FW Area 2 and Bin Area 2
                long fwBinArea2Rev = mAirohaOtaWorkingAreaResolver.getLongArea2Rev();
                Log.d(TAG, "fwBinArea2Rev:" + fwBinArea2Rev);
                Log.d(TAG, "fileBinArea2Rev:" + mBinLongArea2Rev);
                if(mBinLongArea2Rev >= fwBinArea2Rev) {
                    ret = true;
                }else {
                    ret = false;
                }
            }
        }

        return ret;
    }
}
