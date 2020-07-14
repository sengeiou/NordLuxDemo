package com.airoha.btdlib.util;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Daniel.Lee on 2016/8/31.
 */
public class ByteHelper {

    /**
     *
     * @param bytesLittle, bytes in Little Endian
     * @return int
     */
    public static int bytesToInt(byte[] bytesLittle){
        byte[] temp = new byte[4];
        System.arraycopy(bytesLittle, 0, temp, 0, 4);
        ArrayUtils.reverse(temp);

        return Ints.fromByteArray(temp);
    }

    public static long bytesToLong(byte[] bytesLittle){
        byte[] temp = new byte[8];
        System.arraycopy(bytesLittle, 0, temp, 0, 4);
        ArrayUtils.reverse(temp);

        return Longs.fromByteArray(temp);
    }

    public static void reverseBytes(byte[] data) {
        ArrayUtils.reverse(data);
    }

    /**
     *
     * @param value, int
     * @return bytes in Little Endian
     */
    public static byte[] intToBytes(int value){
        byte[] hexbytes = Ints.toByteArray(value); // Big Endian
        // covert to Little Endian
        ArrayUtils.reverse(hexbytes);
        return hexbytes;
    }

    public static String toHex(byte[] bytes){
        return BaseEncoding.base16().encode(bytes);
    }


    public static byte[] addLittleEndian(byte[] a, byte[] b){
        byte [] tmp = new byte[a.length];

        int carry = 0;

        for(int i = 0; i< a.length; i++) {
            int sum = (a[i]&0xFF) + (b[i]&0xFF) + carry;
            tmp[i] = (byte) (sum & 0xFF);
            carry = sum >> 8;
        }
        return tmp;
    }

    public static boolean compare(byte[] bytes1, byte[] bytes2){
        if(bytes1.length != bytes2.length)
            return false;

        for(int i = 0; i< bytes1.length; i++){
            if(bytes1[i] != bytes2[i])
                return false;
        }

        return true;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];

        for(int i = 0; i < len; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }
}
