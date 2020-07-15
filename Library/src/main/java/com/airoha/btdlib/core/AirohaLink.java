package com.airoha.btdlib.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.multidex.BuildConfig;

import com.airoha.btdlib.constant.CommonUUID;
import com.airoha.btdlib.util.ByteHelper;
import com.google.common.io.BaseEncoding;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 1. handles the GATT communication<p>
 * 2. provide API for OTA update<p>
 *
 */

public class AirohaLink {

    private static final String TAG = "AirohaLink";

    class SUPPORTED_SERVICE {
        boolean OTA;
    }

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private boolean mIsConnected = false;

    private String mBluetoothDeviceAddress;
    private Timer mConnectTimer;
    private static int CONNECT_TIMEOUT = 6000;
    private static int CONNECT_RETRY_MAX = 1;
    private int mRetryCount = 0;

//    private static final long SCAN_PERIOD = 15000;
//    private int mConnectionState = STATE_DISCONNECTED;

    private GattStateListenerMgr mGattStateListenerMgr;

    private Handler mHandler;

    private BluetoothGattCharacteristic mOtaWriteCharc;

    Context mCtx;

    private Object mLock;

    private static final int ORIGIN_MTU = 23;
    // if not set by system callback, use origin
    private int mMTU = ORIGIN_MTU;



    class CHARC_DATA {
        public BluetoothGattCharacteristic mCharc;
        public byte[] mData;

        public CHARC_DATA(BluetoothGattCharacteristic charc, byte[] data) {
            mCharc = charc;
            mData = data;
        }
    }

    private ConcurrentLinkedQueue<CHARC_DATA> mCharcWriteQueue;


    public AirohaLink(Context ctx) {

        if (ctx == null)
            throw new IllegalArgumentException("args can't be null");

        Log.d(TAG, "ver.:" + BuildConfig.VERSION_NAME);
        mCtx = ctx;

        mHandler = new Handler();
        mCharcWriteQueue = new ConcurrentLinkedQueue<>();
        mGattStateListenerMgr = new GattStateListenerMgr();

        mLock = new Object();
    }

    public void addGattStateListener(String name, GattStateListener listener) {
        mGattStateListenerMgr.addListener(name, listener);
    }

    public void removeGattStateListener(String name) {
        mGattStateListenerMgr.removeListener(name);
    }

    public void discoverServices(){
        mBluetoothGatt.discoverServices();
    }

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            Log.d(TAG,"onConnectionStateChange GATT addr:" + gatt.getDevice().getAddress() + " status:" + status + " newState:" + newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "Connected to GATT server.");
                mIsConnected = true;

                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Delay 600 ms after connected, then start discovery.");
                        mGattStateListenerMgr.onGattConnected(gatt);
                        gatt.discoverServices();
                    }
                }, 600);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mIsConnected = false;
                if(mConnectTimer == null){
                    Log.d(TAG, "onGattDisconnected");
                    mGattStateListenerMgr.onGattDisconnected(gatt);
                }
                else if(mRetryCount >= CONNECT_RETRY_MAX){
                    mConnectTimer.cancel();
                    mConnectTimer = null;
                    Log.d(TAG, "Retry max, onGattDisconnected");
                    mGattStateListenerMgr.onGattDisconnected(gatt);
                }
                else{
                    Log.d(TAG, "Disconnected from GATT server.");
                    close();
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onServicesDiscovered status: " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                mGattStateListenerMgr.onServicesDiscovered(gatt, status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            synchronized (mCharcWriteQueue) {
                if (mCharcWriteQueue.size() > 0) {
                    mCharcWriteQueue.remove();
                }
                if (mCharcWriteQueue.size() > 0) {
                    CHARC_DATA charcData = mCharcWriteQueue.peek();
                    writeCharacteristic(charcData.mCharc, charcData.mData);
                }
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            final byte[] data = characteristic.getValue();

            final String uuid = characteristic.getUuid().toString();
            Log.d(TAG, "notifi charc.:" + uuid + ";get value: " + BaseEncoding.base16().encode(data));

            mGattStateListenerMgr.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            Log.d(TAG, "onMtuChanged, mtu: " + mtu + ", status: " + status);

            mMTU = mtu;

            mGattStateListenerMgr.onNewMtu(mtu);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(TAG,"onDescriptorWrite, GATT addr:" + gatt.getDevice().getAddress() + " status:" + status );

            synchronized (mLock) {
                mLock.notifyAll();
            }
        }

    };

    public boolean isConnected() {
        return mIsConnected;
    }

    public synchronized boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, byte[] value) {
        if (!mIsConnected || characteristic == null)
            return false;

        characteristic.setValue(value);
        return mBluetoothGatt.writeCharacteristic(characteristic);
    }


    public void addWriteCharacteristicTask(BluetoothGattCharacteristic characteristic, byte[] value) {
        final String uuid = characteristic.getUuid().toString();
        Log.d(TAG, "writeCharacteristic uuid" + uuid + ",  data = " + ByteHelper.toHex(value));
        synchronized (mCharcWriteQueue) {
            mCharcWriteQueue.add(new CHARC_DATA(characteristic, value));
            if (mCharcWriteQueue.size() == 1) {
                boolean ret = writeCharacteristic(characteristic, value);
                Log.d(TAG, "writeCharacteristic ret = " + ret);
            }
        }
    }


    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully.
     * The connection result is reported asynchronously
     */
    public boolean connect(final String address) {
        Log.d(TAG, "Start Connect");
        mBluetoothDeviceAddress = address;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        if (mBluetoothGatt != null) {
            synchronized (mBluetoothGatt) {
                if (mBluetoothGatt != null) {
                    Log.d(TAG, "Disconnect/Close before Connect");
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                    mIsConnected = false;
                    SystemClock.sleep(600);
                }
            }
        }

        mCharcWriteQueue.clear();

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        Log.d(TAG, "Trying to create a new connection. " + address);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // We want to directly connect to the device, so we are setting the autoConnect
            // parameter to false.
            mBluetoothGatt = device.connectGatt(mCtx, false, mGattCallback);
        } else {
            // 2018.1.26 Daniel: use BluetoothDevice.TRANSPORT_LE to force to use BLE transport layer, but the min API level is 23.
            mBluetoothGatt = device.connectGatt(mCtx, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
        }

        if(mConnectTimer != null){
            Log.d(TAG, "mConnectTimer cancel");
            mConnectTimer.cancel();
            mConnectTimer = null;
        }

        Log.d(TAG, "Start Connection Timer");
        mRetryCount = 0;
        mConnectTimer = new Timer();
        mConnectTimer.schedule(new ConnectTimerTask(), CONNECT_TIMEOUT, CONNECT_TIMEOUT);


//        Log.d(TAG, "Trying to scan device!!");
//        mBluetoothDeviceAddress = address;
//        mConnectionState = STATE_CONNECTING;
//        mBluetoothAdapter.startLeScan(mLeConnectingScanCallback);

        return true;
    }

    private class ConnectTimerTask extends TimerTask{
        public void run(){
            Log.d(TAG, "ConnectTimerTask");
            if(!mIsConnected && mRetryCount < CONNECT_RETRY_MAX){
                mRetryCount++;
                close();

                Log.d(TAG, "Reconnect " + mRetryCount);
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mBluetoothDeviceAddress);
                if (device == null) {
                    Log.w(TAG, "Device not found.  Unable to connect.");
                    return;
                }
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    mBluetoothGatt = device.connectGatt(mCtx, false, mGattCallback);
                } else {
                    mBluetoothGatt = device.connectGatt(mCtx, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
                }
            }
            else {
                Log.d(TAG, "Stop Connection Timer");
                mConnectTimer.cancel();
                mConnectTimer = null;
            }
        }
    }

    private BluetoothAdapter.LeScanCallback mLeConnectingScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (mBluetoothDeviceAddress != null) {
                if (device.getAddress().toUpperCase().equals(mBluetoothDeviceAddress.toUpperCase())) {
                    mBluetoothAdapter.stopLeScan(mLeConnectingScanCallback);
                    mBluetoothDeviceAddress = null;

                    {
                        try {
                            Thread.sleep(2000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "Trying to create a new connection.");
                                    mBluetoothGatt = device.connectGatt(mCtx, false, mGattCallback);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                }
            }

        }
    };

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through
     */
    public void disconnect() {
//            if (mBluetoothAdapter == null) {
//                Log.w(TAG, "BluetoothAdapter not initialized");
//                return;
//            } else if (mBluetoothGatt == null) {
//                Log.w(TAG, "Bluetooth Gatt is not connected");
//                return;
//            }
        Log.d(TAG, "Trying to disconnect.");

        if(mConnectTimer != null){
            Log.d(TAG, "Cancel Connection Timer");
            mRetryCount = 0;
            mConnectTimer.cancel();
            mConnectTimer = null;
        }
        if (mBluetoothGatt != null) {
            synchronized (mBluetoothGatt) {
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.disconnect();
                    SystemClock.sleep(200);
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
            }
        }
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        mIsConnected = false;
        if (mBluetoothGatt != null) {
            synchronized (mBluetoothGatt) {
                if (mBluetoothGatt != null) {
                    Log.d(TAG, "Disconnect/Close mBluetoothGatt");
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
            }
        }
    }

    public void requestChangeMtu(int mtu) {
        if (Build.VERSION.SDK_INT >= 21) {
            Log.d(TAG, "requestMtu=" + mtu);

            if (mBluetoothGatt != null) {
                mBluetoothGatt.requestMtu(mtu);
            }
        }
    }

    public Context getContext() {
        return mCtx;
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification.  False otherwise.
     */
    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                                 boolean enabled) {
        boolean ret = false;

        if (mBluetoothAdapter == null) {
            Log.e(TAG, "BluetoothAdapter not initialized");
            return ret;
        } else if (mBluetoothGatt == null) {
            Log.e(TAG, "Bluetooth Gatt is not connected");
            return ret;
        }

        // Check characteristic property
        final int properties = characteristic.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY) == 0) {
            Log.e(TAG, "Cannot find PROPERTY_NOTIFY");
            return ret;
        }

        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        /// add delay
        SystemClock.sleep(200);

        BluetoothGattDescriptor desc = characteristic.getDescriptor(CommonUUID.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);

        if (desc == null) {
            Log.e(TAG, "getDescriptor return null");
            return ret;
        }

        desc.setValue(enabled ?
                BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE :
                BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);

        final BluetoothGattCharacteristic parentCharacteristic = desc.getCharacteristic();
        final int originalWriteType = parentCharacteristic.getWriteType();
        parentCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
        ret = mBluetoothGatt.writeDescriptor(desc);
        synchronized (mLock) {
            try {
                mLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        parentCharacteristic.setWriteType(originalWriteType);


        Log.d(TAG,"setCharacteristicNotification done" );

        return ret;
    }
}
