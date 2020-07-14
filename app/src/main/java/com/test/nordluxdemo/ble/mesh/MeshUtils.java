package com.test.nordluxdemo.ble.mesh;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Tina.Shen on 2017/3/2.
 */

public class MeshUtils {
    private static final String TAG = "MeshUtils";
    public static final int mTimeoutDiscoverServices = 3000;
    public static final int mRetryDiscoverServices = 3;

    public static String MODLE_ID_ON_OFF = "1000";
    public static String MODLE_ID_LIGHTNESS = "1300";
    public static String MODLE_ID_COLOR = "1307";
    public static String MODLE_ID_COLOR_TEMPERATURE = "1306";
    public static String FLASH_FILE_NAME = "flash_mesh.bin";

    public static class NETKEY_INFO {
        public Short mNetkworkIndex;
        public String mNetworkName;
        public byte[] mNetKey;

        public NETKEY_INFO(short index, String name, byte[] key) {
            mNetkworkIndex = index;
            mNetworkName = name + "_" + index;
            mNetKey = key;
        }
    }

    public static class GROUP_INFO {
        public String mGroupAddress;
        public String mGroupName;
        public Short mNetkworkIndex;
        public HashMap<String, LinkedList<String>> mElementModelMap;

        public GROUP_INFO(String address, String name, short networkIndex) {
            mGroupAddress = address;
            mGroupName = name;
            mNetkworkIndex = networkIndex;
            mElementModelMap = new HashMap<>();
        }
    }

//    public static final Short GROUP_INDEX[] = {(short)0xC000, (short)0xC001};
//    public static final String GROUP_NAME[] = {"Default Group", "My Group"};

    /// provisioned device information
    public static class PD_INFO {
        public LinkedList<Short> mNekworkIndexArray;
        /// model and group mapping
        public HashMap<String, LinkedList<String>> mElementModelMap;
        public HashMap<String, LinkedList<String>> mModelGroupMap;
        public HashMap<String, LinkedList<String>> mPrimaryElementsMap;
        public HashMap<String, LinkedList<String>> mElementModelsMap;
        public String mDeviceName;
        public String mDeviceBdAddr;
        public byte[] mDeviceUUID;
        public Short mUnicastAddr;
        public byte[] mDeviceKey;
    }

    public static class MODEL_INFO implements Serializable {
        short mElementID;
        int mModelID;
        String mHexElementID;
        String mHexModelID;

        public MODEL_INFO(short elementID, int modelID) {
            mElementID = elementID;
            mModelID = modelID;
            mHexElementID = MeshUtils.shortToHexString(mElementID);
            mHexModelID = MeshUtils.intToHexString(mModelID);
        }
    }

    public static final byte[] DEV_UUID = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

    public static final byte[] APP_KEY = {
            0x63, (byte) 0x96, 0x47, 0x71, 0x73, 0x4f, (byte) 0xbd, 0x76,
            (byte) 0xe3, (byte) 0xb4, 0x05, 0x19, (byte) 0xd1, (byte) 0xd9, 0x4a, 0x48
    };
    public static final byte[] NET_KEY = {
            0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88,
            (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0x0F
    };

    public static final byte[] NET_KEY_1 = {
            0x50, 0x70, (byte)0x95, (byte) 0x96, (byte)0xCA, 0x2D, (byte)0xDF, 0x3C,
            (byte)0xFD, (byte)0xA9, 0x43, (byte)0xFD, (byte)0x90, 0x79, 0x65, 0x15
    };
    public static final byte[] NET_KEY_2 = {
            0x61, (byte)0xB9, 0x5D, 0x46, 0x5F, (byte)0xCF, (byte)0xD6, (byte)0xDC,
            (byte)0x87, 0x77, 0x68, (byte) 0xB3, (byte)0xE4, 0x24, 0x63, (byte)0x89
    };

    public static final int RESET_NODE_RESULT_CODE = -7788;

    public static final Short PROVISIONER_ADDR = 0x7FFF;

    public static final String NET_NAME = "Default Network";
    public static final String TEST_NET_1_NAME = "Test_Network_1";
    public static final String TEST_NET_2_NAME = "Test_Network_2";
    public static final short NET_KEY_INDEX = 0;
    public static final String DEFAULT_GROUP_NAME = "Default Group";
    public static final String DEFAULT_GROUP_ADDR = "C000";


    public static final short APP_KEY_INDEX = 0x123;

    public static final Short ELEMENT_IDNEX = 0;

    public static final String EXTRAS_NETWORK_NAME = "NETWORK_NAME";
    public static final String EXTRAS_NETWORK_KEY = "NETWORK_KEY";
    public static final String EXTRAS_APPLICATION_KEYIDX = "APPLICATION_KEYIDX";
    public static final String EXTRAS_NETWORK_KEYIDX = "NETWORK_KEYIDX";
    public static final String EXTRAS_GROUP_ADDR = "EXTRAS_GROUP_ADDR";
    public static final String EXTRAS_MODEL_ID = "EXTRAS_MODEL_ID";
    public static final String EXTRAS_BT_DEVICE = "BT_DEVICE";
    public static final String EXTRAS_BT_NAME = "BT_NAME";
    public static final String EXTRAS_BT_BD_ADDR = "EXTRAS_BT_BD_ADDR";
    public static final String EXTRAS_DEVICE_UUID = "DEVICE_UUID";
    public static final String EXTRAS_DEVICE_KEY = "DEVICE_KEY";
    public static final String EXTRAS_IVINDEX = "IV_INDEX";
    public static final String EXTRAS_UNICAST_ADDR = "UNICAST_ADDR";
    public static final String EXTRA_NUMBER_ELEMENTS = "NUMBER_ELEMENTS";

    public static class AddrType {
        public static final byte UNASSIGNED = 0;
        public static final byte UNICAST = 1;
        public static final byte VIRTUAL = 2;
        public static final byte GROUP = 3;
    }

    public static class GroupType {
        public static final byte PROXIES = 1;
        public static final byte FRIENDS = 2;
        public static final byte RELAYS = 3;
        public static final byte BROADCAST = 4;
        public static final byte RFU = 5;
    }

    public static final int MESH_UUID_LENGTH = 16;

    private static short mAddrSelf;
    private static short mAddrClient = 0x1000;
    private static short mTid = 0;

    public static int gIvIndex;
    public static ArrayList<NETKEY_INFO> gNetkeyInfoList = new ArrayList<>();
    public static ArrayList<GROUP_INFO> gGroupInfoList = new ArrayList<>();
    public static ArrayList<PD_INFO> gPdInfoList = new ArrayList<>();
    public static ArrayAdapter<String> gLogAdapter;

    // Tid should be different for each command
    public static byte getTid() {
        mTid += 1;
        if (mTid >= 255) {
            mTid = 0;
        }
        return (byte)mTid;
    }

    public static NETKEY_INFO getNetkeyInfo(short netkeyIndex) {
        NETKEY_INFO ret = null;
        for (NETKEY_INFO info : gNetkeyInfoList) {
            if (info.mNetkworkIndex == netkeyIndex) {
                ret = info;
                break;
            }
        }
        return ret;
    }

    public static GROUP_INFO getGroupInfo(String groupAddr) {
        GROUP_INFO ret = null;
        if(gGroupInfoList.size() == 0){
            gGroupInfoList.add(new GROUP_INFO(DEFAULT_GROUP_ADDR, DEFAULT_GROUP_NAME, NET_KEY_INDEX));
        }
        for (GROUP_INFO info : gGroupInfoList) {
            if (info.mGroupAddress.equalsIgnoreCase(groupAddr)) {
                ret = info;
                break;
            }
        }
        return ret;
    }

    public static GROUP_INFO getGroupInfoByGroupName(String groupName) {
        GROUP_INFO ret = null;
        for (GROUP_INFO info : gGroupInfoList) {
            if (info.mGroupName.equalsIgnoreCase(groupName)) {
                ret = info;
                break;
            }
        }
        return ret;
    }

    public static boolean isGroupExisted(String groupName, String groupAddr) {
        for (GROUP_INFO info : gGroupInfoList) {
            if (info.mGroupName.equalsIgnoreCase(groupName) ||
                    info.mGroupAddress.equalsIgnoreCase(groupAddr)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<PD_INFO> getPdInfoList(String groupAddr) {
        ArrayList<PD_INFO> ret = new ArrayList<>();
        if(MeshUtils.getGroupInfo(groupAddr) == null){
            return ret;
        }
        for (String element_addr : MeshUtils.getGroupInfo(groupAddr).mElementModelMap.keySet()) {
            for (PD_INFO pdInfo : gPdInfoList) {
                for (HashMap.Entry<String, LinkedList<String>> entry : pdInfo.mPrimaryElementsMap.entrySet())
                    for (String item : entry.getValue()) {
                        if (item.equalsIgnoreCase(element_addr)) {
                            ret.add(pdInfo);
                        }

                    }
            }
        }
        return ret;
    }

    public static ArrayList<PD_INFO> getPdInfoList(String groupAddr, String modelId) {
        ArrayList<PD_INFO> ret = new ArrayList<>();
        if(MeshUtils.getGroupInfo(groupAddr) == null){
            return ret;
        }
        for (HashMap.Entry<String, LinkedList<String>> entry : MeshUtils.getGroupInfo(groupAddr).mElementModelMap.entrySet()) {
            if(!entry.getValue().contains(modelId)){
                continue;
            }
            String element_addr = entry.getKey();
            for (PD_INFO pdInfo : gPdInfoList) {
                for (HashMap.Entry<String, LinkedList<String>> entry1 : pdInfo.mPrimaryElementsMap.entrySet()) {
                    for (String item : entry1.getValue()) {
                        if (item.equalsIgnoreCase(element_addr)) {
                            ret.add(pdInfo);
                        }
                    }
                }
            }
        }
        return ret;
    }

    public static PD_INFO getPdInfoByElements(String elementAddr) {
        PD_INFO ret = null;
        int element = Integer.valueOf(elementAddr, 16);
        for (PD_INFO pdInfo : gPdInfoList) {
            for (HashMap.Entry<String, LinkedList<String>> entry : pdInfo.mPrimaryElementsMap.entrySet()) {
                if(entry.getValue().contains(elementAddr)){
                    ret = pdInfo;
                    break;
                }
            }
        }
        return ret;
    }

    public static PD_INFO getPdInfo(String unicastAddr) {
        PD_INFO ret = null;
        int uniAddr = Integer.valueOf(unicastAddr, 16);
        for (PD_INFO pdInfo : gPdInfoList) {
            if (pdInfo.mUnicastAddr == uniAddr) {
                ret = pdInfo;
                break;
            }
        }
        return ret;
    }

    public static PD_INFO getPdInfo(short unicastAddr) {
        PD_INFO ret = null;
        for (PD_INFO pdInfo : gPdInfoList) {
            if (pdInfo.mUnicastAddr == unicastAddr) {
                ret = pdInfo;
                break;
            }
        }
        return ret;
    }

    public static void removePdInfo(String bdAddr) {
        for (PD_INFO pdInfo : gPdInfoList) {
            if (pdInfo.mDeviceBdAddr.equals(bdAddr)) {
                gPdInfoList.remove(pdInfo);
                break;
            }
        }
    }

    public static byte BLE_MESH_IV_UPDATE_STATE_NORMAL = 0x00;
//    public static int initMesh(BluetoothLeService myService, boolean isFirstRunApp) {
//
//        int ret = myService.getAirohaMeshMgr().getMeshCore().init(FLASH_FILE_NAME, MeshUtils.DEV_UUID);
//
//        if(isFirstRunApp && isFirstRunApp){
//            ret = myService.getAirohaMeshMgr().getMeshConfig().setIvIndex(0, BLE_MESH_IV_UPDATE_STATE_NORMAL);
//        }
//        if (ret == 0) {
//            ret = myService.getAirohaMeshMgr().getMeshConfig().setProvisionerAddr(MeshUtils.PROVISIONER_ADDR);
//        }
//
//        if (ret == 0) {
//            ret = myService.getAirohaMeshMgr().getMeshConfig().addNetKey(MeshUtils.NET_KEY, Short.valueOf(MeshUtils.NET_KEY_INDEX));
//        }
//
//        if (ret == 0) {
//            ret = myService.getAirohaMeshMgr().getMeshConfig().addAppKey(Short.valueOf(MeshUtils.NET_KEY_INDEX), MeshUtils.APP_KEY, Short.valueOf(MeshUtils.APP_KEY_INDEX));
//        }
//
//        if (ret == 0) {
//            for (MeshUtils.PD_INFO pdInfo : MeshUtils.gPdInfoList) {
//                ret = myService.getAirohaMeshMgr().getMeshConfig().addDevKey(pdInfo.mDeviceKey, pdInfo.mUnicastAddr);
//            }
//        }
//
//        return ret;
//    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    public static byte[] hexStringToBytes(String s) {
        if (s != null) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i + 1), 16));
            }
            return data;
        }
        return null;
    }

    public static byte getByte(int in, int field) {
        int shift = (4 - field) * 8;
        int tmp = (in & (0xFF << shift)) >> shift;
        return (byte) (tmp > 0 ? tmp : tmp + 256);
    }

    public static byte getByte(short in, int field) {
        int shift = (2 - field) * 8;
        int tmp = (in & (0xFF << shift)) >> shift;
        return (byte) (tmp > 0 ? tmp : tmp + 256);
    }

    public static int setByte(byte b1, byte b2, byte b3, byte b4) {
        int out = ((b1 >= 0 ? b1 : b1 + 256) << 24)
                | ((b2 >= 0 ? b2 : b2 + 256) << 16)
                | ((b3 >= 0 ? b3 : b3 + 256) << 8)
                | (b4 >= 0 ? b4 : b4 + 256);
        return out;
    }

    public static boolean isLocalAddress(short dstAddr) {
        return dstAddr == mAddrSelf;
    }

    public static short getSelfAddress() {
        return mAddrSelf;
    }

    public static void setSelfAddress(short addr) {
        mAddrSelf = addr;
    }

    public static byte getAddressType(short addr) {
        if (addr == 0x0000) {
            return AddrType.UNASSIGNED;
        } else if ((addr & 0x8000) == 0x0000) {
            return AddrType.UNICAST;
        } else {
            if ((addr & 0xC0000) == 0x8000) {
                return AddrType.VIRTUAL;
            } else {
                return AddrType.GROUP;
            }
        }
    }

    public static byte getAddressGroupType(short addr) {
        if (addr == 0xFFFF) {
            return GroupType.BROADCAST;
        } else if (addr == 0xFFFC) {
            return GroupType.PROXIES;
        } else if (addr == 0xFFFE) {
            return GroupType.RELAYS;
        } else if (addr == 0xFFFD) {
            return GroupType.FRIENDS;
        } else {
            return GroupType.RFU;
        }
    }

    public static short getEmptyAddress() {
        Log.d(TAG, "getEmptyAddress: 0x" + Integer.toHexString(mAddrClient));
        return mAddrClient++;
    }

    /**
     * Converts a byte array to short value.
     * Equivalent to byteArrayToShort(paRawBytes, 0, pbBigEndian);
     *
     * @param paRawBytes  the byte array
     * @param pbBigEndian true if the bytes are in Big-endian order; false otherwise
     * @return short representation of the bytes
     */
    public static short byteArrayToShort(byte[] paRawBytes, boolean pbBigEndian) {
        return byteArrayToShort(paRawBytes, 0, pbBigEndian);
    }

    /**
     * Converts a portion of a byte array with given offset to short value.
     *
     * @param paRawBytes  the byte array
     * @param piOffset    offset in the original array to start reading bytes from
     * @param pbBigEndian true if the bytes are in Big-endian order; false otherwise
     * @return short representation of the bytes
     */
    public static short byteArrayToShort(byte[] paRawBytes, int piOffset, boolean pbBigEndian) {
        int iRetVal = -1;

        // TODO: revisit this: should we silently add missing byte and should
        // we ingnore excessing bytes?
        if (paRawBytes.length < piOffset + 2)
            return -1;

        int iLow;
        int iHigh;

        if (pbBigEndian) {
            iLow = paRawBytes[piOffset + 1];
            iHigh = paRawBytes[piOffset + 0];
        } else {
            iLow = paRawBytes[piOffset + 0];
            iHigh = paRawBytes[piOffset + 1];
        }

        // Merge high-order and low-order byte to form a 16-bit double value.
        iRetVal = (iHigh << 8) | (0xFF & iLow);

        return (short) iRetVal;
    }

    public static byte[] shortToByteArray(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static String shortToHexString(short value) {
        byte[] tmp = shortToByteArray(value);
        return bytesToHexString(tmp);
    }

    public static String intToHexString(int value) {
        byte[] tmp = intToByteArray(value);
        return bytesToHexString(tmp);
    }

    public static String byteToHexString(byte value) {
        return Integer.toHexString(value & 0xFF);
    }

    public static String getFullAddrStr(short addr){
        String Addr = Integer.toHexString(addr);
        String full_Addr = "";
        for (int i = 0; i < 4 - Addr.length(); ++i) {
            full_Addr += "0";
        }
        full_Addr += Addr.toUpperCase();

        return full_Addr;
    }

    public static String getFullAddrStr(int addr){
        String Addr = Integer.toHexString(addr);
        String full_Addr = "";
        for (int i = 0; i < 4 - Addr.length(); ++i) {
            full_Addr += "0";
        }
        full_Addr += Addr.toUpperCase();

        return full_Addr;
    }

}
