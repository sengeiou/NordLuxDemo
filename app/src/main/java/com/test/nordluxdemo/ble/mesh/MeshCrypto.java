package com.test.nordluxdemo.ble.mesh;

import android.util.Log;

import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.util.Arrays;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Tina.Shen on 2017/3/2.
 */

public class MeshCrypto {
    private final static boolean DEBUG = false;
    private final static String TAG = "MeshCrypto";

    static {
        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
    }

    private static byte[] mTestText = new byte[] {0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77,
            (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
    private static byte[] mTestKey = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    private static byte[] mTestCcmKey = new byte[] {
            (byte) 0xC0, (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xC7,
            (byte) 0xC8, (byte) 0xC9, (byte) 0xCA, (byte) 0xCB, (byte) 0xCC, (byte) 0xCD, (byte) 0xCE, (byte) 0xCF
    };
    private static byte[] mTestCcmText = new byte[] {
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
            0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E
    };
    private static byte[] mTestCcmAdd = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07
    };
    private static byte[] mTestCcmNonce = new byte[] {
            0x00, 0x00, 0x00, 0x03, 0x02, 0x01, 0x00, (byte) 0xa0,
            (byte) 0xa1, (byte) 0xa2, (byte) 0xa3, (byte) 0xa4, (byte) 0xa5
    };
    private static byte[] mTestCmacKey = new byte[] {
            0x2b, 0x7e, 0x15, 0x16, 0x28, (byte)0xae, (byte)0xd2, (byte)0xa6,
            (byte)0xab, (byte)0xf7, 0x15, (byte)0x88, 0x09, (byte)0xcf, 0x4f, 0x3c
    };
    private static byte[] mTestCmacText40 = new byte[] {
            0x6b, (byte)0xc1, (byte)0xbe, (byte)0xe2, 0x2e, 0x40, (byte)0x9f, (byte)0x96,
            (byte)0xe9, 0x3d, 0x7e, 0x11, 0x73, (byte)0x93, 0x17, 0x2a,
            (byte)0xae, 0x2d, (byte)0x8a, 0x57, 0x1e, 0x03, (byte)0xac, (byte)0x9c,
            (byte)0x9e, (byte)0xb7, 0x6f, (byte)0xac, 0x45, (byte)0xaf, (byte)0x8e, 0x51,
            0x30, (byte)0xc8, 0x1c, 0x46, (byte)0xa3, 0x5c, (byte)0xe4, 0x11,
            (byte)0xe5, (byte)0xfb, (byte)0xc1, 0x19, 0x1a, 0x0a, 0x52, (byte)0xef,
            (byte)0xf6, (byte)0x9f, 0x24, 0x45, (byte)0xdf, 0x4f, (byte)0x9b, 0x17,
            (byte)0xad, 0x2b, 0x41, 0x7b, (byte)0xe6, 0x6c, 0x37, 0x10
    };
    private static byte[] mTestS1Text = new byte[] {
            't', 'e', 's', 't'
    };
    private static byte[] mTestK1N = new byte[] {
            0x32, 0x16, (byte)0xd1, 0x50, (byte)0x98, (byte)0x84, (byte)0xb5, 0x33,
            0x24, (byte)0x85, 0x41, 0x79, 0x2b, (byte)0x87, 0x7f, (byte)0x98
    };
    private static byte[] mTestK1Salt = new byte[] {
            0x2b, (byte)0xa1, 0x4f, (byte)0xfa, 0x0d, (byte)0xf8, 0x4a, 0x28,
            0x31, (byte)0x93, (byte)0x8d, 0x57, (byte)0xd2, 0x76, (byte)0xca, (byte)0xb4
    };
    private static byte[] mTestK1P = new byte[] {
            0x5a, 0x09, (byte)0xd6, 0x07, (byte)0x97, (byte)0xee, (byte)0xb4, 0x47,
            (byte)0x8a, (byte)0xad, (byte)0xa5, (byte)0x9d, (byte)0xb3, 0x35, 0x2a, 0x0d
    };
    private static byte[] mTestK2N = new byte[] {
            (byte)0xf7, (byte)0xa2, (byte)0xa4, 0x4f, (byte)0x8e, (byte)0x8a, (byte)0x80, 0x29,
            0x06, 0x4f, 0x17, 0x3d, (byte)0xdc, 0x1e, 0x2b, 0x00
    };
    private static byte[] mTestK2P_m = new byte[] {
            0x00
    };
    private static byte[] mTestK2P_f = new byte[] {
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09
    };
    private static byte[] mTestK3N = new byte[] {
            (byte)0xf7, (byte)0xa2, (byte)0xa4, 0x4f, (byte)0x8e, (byte)0x8a, (byte)0x80, 0x29,
            0x06, 0x4f, 0x17, 0x3d, (byte)0xdc, 0x1e, 0x2b, 0x00
    };
    private static byte[] mTestK4N = new byte[] {
            0x32, 0x16, (byte)0xd1, 0x50, (byte)0x98, (byte)0x84, (byte)0xb5, 0x33,
            0x24, (byte)0x85, 0x41, 0x79, 0x2b, (byte)0x87, 0x7f, (byte)0x98
    };
    // Update the CBC-MAC state in y using a block in b
    // (Always using b as the source helps the compiler optimise a bit better.)
    private static void UPDATE_CBC_MAC(Cipher cipher, byte[] y, byte[] b) {
        for (int i = 0; i < 16; i++) {
            y[i] ^= b[i];
        }

        byte[] result = cipher.update(y);
        System.arraycopy(result, 0, y, 0, 16);
        //Log.d(TAG, "y:  " + MeshUtils.bytesToHexString(y));
    }

    private static void generateSubkey(byte[] key, byte[] k1, byte[] k2)
    {
        //    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //    +                    Algorithm Generate_Subkey                      +
        //    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //    +                                                                   +
        //    +   Input    : K (128-bit key)                                      +
        //    +   Output   : K1 (128-bit first subkey)                            +
        //    +              K2 (128-bit second subkey)                           +
        //    +-------------------------------------------------------------------+
        //    +                                                                   +
        //    +   Constants: const_Zero is 0x00000000000000000000000000000000     +
        //    +              const_Rb   is 0x00000000000000000000000000000087     +
        //    +   Variables: L          for output of AES-128 applied to 0^128    +
        //    +                                                                   +
        //    +   Step 1.  L := AES-128(K, const_Zero);                           +
        //    +   Step 2.  if MSB(L) is equal to 0                                +
        //    +            then    K1 := L << 1;                                  +
        //    +            else    K1 := (L << 1) XOR const_Rb;                   +
        //    +   Step 3.  if MSB(K1) is equal to 0                               +
        //    +            then    K2 := K1 << 1;                                 +
        //    +            else    K2 := (K1 << 1) XOR const_Rb;                  +
        //    +   Step 4.  return K1, K2;                                         +
        //    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        try {
            // Step 1
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/ECB/NoPadding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);
            byte[] zero = new byte[16];
            byte[] result = mCipher.doFinal(zero);
            // Step 2
            for(int i = 0; i < 15; i++) {
                k1[i] = (byte) (result[i] << 1);
                k1[i] |= (byte) (result[i+1] >> 7) & 0x01;
            }
            k1[15] = (byte) (result[15] << 1);
            if((result[0] & 0x80) > 0) {
                k1[15] ^= 0x87;
            }
            //Log.d(TAG, "k1:" + MeshUtils.bytesToHexString(k1));
            // Step 3
            for(int i = 0; i < 15; i++) {
                k2[i] = (byte) (k1[i] << 1);
                k2[i] |= (byte) (k1[i+1] >> 7) & 0x01;
            }
            k2[15] = (byte) (k1[15] << 1);
            if ((k1[0] & 0x80) > 0){
                k2[15] ^= 0x87;
            }
            //Log.d(TAG, "k2:" + MeshUtils.bytesToHexString(k2));
            mCipher.doFinal();
        } catch(Exception ex) {
            Log.e(TAG, "Exception: " + ex);
            return;
        }
    }

    public static boolean aesCcm(byte[] key, byte[] n, byte[] tag, byte[] a, byte[] input, byte[] output, boolean encrypt) {
        int i, q;
        int len_left, olen;
        byte[] b = new byte[16];
        byte[] y = new byte[16];
        byte[] ctr = new byte[16];

        int srcidx = 0, dstidx = 0;
        /*
        Log.d(TAG, "CCM key[" + key.length + "]   :" + MeshUtils.bytesToHexString(key));
        Log.d(TAG, "CCM nonce[" + n.length + "] :" + MeshUtils.bytesToHexString(n));
        Log.d(TAG, "CCM tag[" + tag.length + "]    :" + MeshUtils.bytesToHexString(tag));
        Log.d(TAG, "CCM input[" + input.length + "] :" + MeshUtils.bytesToHexString(input));
        Log.d(TAG, "CCM output[" + output.length + "]:" + MeshUtils.bytesToHexString(output));
        */
        if (a == null) {
            a = new byte[0];
        }

        try {
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/ECB/NoPadding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);

            // Check length requirements: SP800-38C A.1
            // Additional requirement: a < 2^16 - 2^8 to simplify the code.
            // 'length' checked later (when writing it to the first block)
            if (tag.length < 4 || tag.length > 16 || tag.length % 2 != 0) {
                Log.e(TAG, "invalid tag length " + tag.length);
                return false;
            }
            // Also implies q is within bounds
            if (n.length < 7 || n.length > 13) {
                Log.e(TAG, "invalid nonce length " + n.length);
                return false;
            }
            if (a.length > 0xFF00) {
                Log.e(TAG, "invalid additional data length " + a.length);
                return false;
            }

            q = 16 - 1 - n.length;
            b[0] = 0;
            b[0] |= ((a.length > 0) ? 1 : 0) << 6;
            b[0] |= ((tag.length - 2) / 2) << 3;
            b[0] |= q - 1;
            System.arraycopy(n, 0, b, 1, n.length);

            for (i = 0, len_left = input.length; i < q; i++, len_left >>= 8)
                b[15 - i] = (byte) (len_left & 0xFF);

            if (len_left > 0)
                return false;

            // Start CBC-MAC with first block
            for(int j = 0; j < 16; j++) {
                y[j] = 0;
            }
            UPDATE_CBC_MAC(mCipher, y, b);

            // If there is additional data, update CBC-MAC with
            // add_len, add, 0 (padding to a block boundary)
            if (a.length > 0) {
                int use_len;
                len_left = a.length;
                srcidx = 0;

                for (int j = 0; j < 16; j++) {
                    b[j] = 0;
                }

                b[0] = (byte) ((a.length >> 8) & 0xFF);
                b[1] = (byte) ((a.length) & 0xFF);

                use_len = len_left < 16 - 2 ? len_left : 16 - 2;
                System.arraycopy(a, srcidx, b, 2, use_len);
                len_left -= use_len;
                srcidx += use_len;

                UPDATE_CBC_MAC(mCipher, y, b);

                while (len_left > 0) {
                    use_len = len_left > 16 ? 16 : len_left;

                    for (int j = 0; j < 16; j++) {
                        b[j] = 0;
                    }
                    System.arraycopy(a, srcidx, b, 0, use_len);
                    UPDATE_CBC_MAC(mCipher, y, b);

                    len_left -= use_len;
                    srcidx += use_len;
                }
            }

            // Prepare counter block for encryption:
            // 0        .. 0        flags
            // 1        .. iv_len   nonce (aka iv)
            // iv_len+1 .. 15       counter (initially 1)
            //
            // With flags as (bits):
            // 7 .. 3   0
            // 2 .. 0   q - 1
            ctr[0] = (byte) (q - 1);
            System.arraycopy(n, 0, ctr, 1, n.length);
            for (int j = 0; j < q; j++) {
                ctr[j + 1 + n.length] = 0;
            }
            ctr[15] = 1;

            // Authenticate and {en,de}crypt the message.
            //
            // The only difference between encryption and decryption is
            // the respective order of authentication and {en,de}cryption.
            len_left = input.length;
            srcidx = 0;
            dstidx = 0;

            while (len_left > 0) {
                int use_len = len_left > 16 ? 16 : len_left;

                if (encrypt) {
                    for (int j = 0; j < 16; j++) { b[j] = 0; }
                    System.arraycopy(input, srcidx, b, 0, use_len);
                    UPDATE_CBC_MAC(mCipher, y, b);
                }

                mCipher.update(ctr, 0, ctr.length, b);
                for(int j = 0; j < use_len; j++ ) {
                    output[j + dstidx] = (byte) (input[j + srcidx] ^ b[j]);
                }

                if (!encrypt) {
                    for (int j = 0; j < 16; j++) {
                        b[j] = 0;
                    }
                    System.arraycopy(output, dstidx, b, 0, use_len);
                    UPDATE_CBC_MAC(mCipher, y, b);
                }

                dstidx += use_len;
                srcidx += use_len;
                len_left -= use_len;

                // Increment counter.
                // No need to check for overflow thanks to the length check above.
                for (i = 0; i < q; i++)
                    if (++ctr[15 - i] != 0)
                        break;
            }

            // Authentication: reset counter and crypt/mask internal tag
            for (i = 0; i < q; i++)
                ctr[15 - i] = 0;

            mCipher.update(ctr, 0, ctr.length, b);
            for(int j = 0; j < 16; j++ ) {
                y[j] = (byte) (y[j] ^ b[j]);
            }
            mCipher.doFinal();

            if (!encrypt) {
                for (i = 0; i < tag.length; i++) {
                    if (y[i] != tag[i]) {
                        output = null;
                        Log.w(TAG, "aesCCM failed, tag not match. 0x" + MeshUtils.bytesToHexString(y));
                        return false;
                    }
                }
            }
            System.arraycopy(y, 0, tag, 0, tag.length);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, "aesCCM OUT: " + MeshUtils.bytesToHexString(output));
            Log.d(TAG, "aesCCM TAG: " + MeshUtils.bytesToHexString(tag));
        }
        return true;
    }

    public static byte[] aesCmac(byte[] key, byte[] text) {
        try {
            // Step 1.  (K1,K2) := Generate_Subkey(K)
            byte[] k1 = new byte[16];
            byte[] k2 = new byte[16];
            generateSubkey(key, k1, k2);

            // start cmac
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/ECB/NoPadding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);

            int len_left = text.length;
            int srcidx = 0;
            int counter = 0;
            byte[] block = new byte[16];
            //Log.d(TAG, "key["+key.length+"]:" + MeshUtils.bytesToHexString(key));
            //Log.d(TAG, "text["+text.length+"]:" + MeshUtils.bytesToHexString(text));

            if (text.length == 0) {
                byte[] m = new byte[16];
                m[0] = (byte)0x80;

                for(int i = 0; i < 16; i++) {
                    m[i] ^= k2[i];
                }

                for(int i = 0; i < 16; i++) {
                    m[i] ^= block[i];
                }
                block = mCipher.update(m);
            }
            while (len_left > 0) {
                //Log.d(TAG, "["+counter+"]len_left=" + len_left);
                byte[] m = new byte[16];
                if (len_left >= 16)
                {
                    System.arraycopy(text, srcidx, m, 0, 16);
                    //Log.d(TAG, "m_"+counter+": " + MeshUtils.bytesToHexString(m));
                    len_left -= 16;
                    srcidx += 16;
                    if (len_left == 0) //last block with 16 byte size
                    {
                        for(int i = 0; i < 16; i++) {
                            m[i] ^= k1[i];
                        }
                        //Log.d(TAG, "m xor k1: " + MeshUtils.bytesToHexString(m));
                    }
                }
                else //last block with incomplete size
                {
                    //Log.d(TAG, "CMAC last block incomplete");
                    System.arraycopy(text, srcidx, m, 0, len_left);
                    m[len_left] = (byte)0x80;
                    srcidx += len_left;
                    len_left = 0;
                    for(int i = 0; i < 16; i++) {
                        m[i] ^= k2[i];
                    }
                    //Log.d(TAG, "m xor k2: " + MeshUtils.bytesToHexString(m));
                }

                for(int i = 0; i < 16; i++) {
                    m[i] ^= block[i];
                }
                //Log.d(TAG, "m_"+counter+" xor block:" + MeshUtils.bytesToHexString(m));
                block = mCipher.update(m);
                //Log.d(TAG, "block_"+counter+":" + MeshUtils.bytesToHexString(block));
                counter++;
            }
            mCipher.doFinal();
            return block;
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Exception: " + ex);
            return null;
        }
    }

    public static byte[] aesCipher(byte[] key,byte[] text, boolean encrypt) {
        try
        {
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/ECB/NoPadding");
            if (encrypt) {
                mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);
            } else {
                mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec);
            }
            byte[] result = mCipher.doFinal(text);
            //Log.d(TAG, "aes-Cipher: " + MeshUtils.bytesToHexString(result));
            return result;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public static byte[] k1(byte[] n, byte[] salt, byte[] p) {
        //Log.d(TAG, "n: " + MeshUtils.bytesToHexString(n));
        //Log.d(TAG, "salt: " + MeshUtils.bytesToHexString(salt));
        //Log.d(TAG, "p: " + MeshUtils.bytesToHexString(p));
        byte[] T = aesCmac(salt, n);
        //Log.d(TAG, "T: " + MeshUtils.bytesToHexString(T));
        byte[] result = aesCmac(T, p);
        Log.d(TAG, "k1 :" + MeshUtils.bytesToHexString(result));
        return result;
    }

    public static byte[] k2(byte[] n, byte[] p) {
        byte[] salt = s1(new byte[] {'s','m','k','2'});
        byte[] T = aesCmac(salt, n);
        byte[] tmp = new byte[p.length+1];
        System.arraycopy(p, 0, tmp, 0, p.length);
        tmp[tmp.length-1] = 0x01;
        byte[] T1 = aesCmac(T, tmp);

        tmp = new byte[T1.length + p.length + 1];
        System.arraycopy(T1, 0, tmp, 0, T1.length);
        System.arraycopy(p, 0, tmp, T1.length, p.length);
        tmp[tmp.length-1] = 0x02;
        byte[] T2 = aesCmac(T, tmp);

        tmp = new byte[T2.length + p.length + 1];
        System.arraycopy(T2, 0, tmp, 0, T2.length);
        System.arraycopy(p, 0, tmp, T2.length, p.length);
        tmp[tmp.length-1] = 0x03;
        byte[] T3 = aesCmac(T, tmp);

        byte[] result = new byte[33];
        result[0] = (byte) (T1[15] & 0x7F);
        System.arraycopy(T2, 0, result, 1, T2.length);
        System.arraycopy(T3, 0, result, 1+T2.length, T3.length);

        if (DEBUG) {
            Log.d(TAG, "\tk2 T :" + MeshUtils.bytesToHexString(T));
            Log.d(TAG, "\tk2 T1 :" + MeshUtils.bytesToHexString(T1));
            Log.d(TAG, "\tk2 T2 :" + MeshUtils.bytesToHexString(T2));
            Log.d(TAG, "\tk2 T3 :" + MeshUtils.bytesToHexString(T3));
            Log.d(TAG, "\tk2 :" + MeshUtils.bytesToHexString(result));
        }
        return result;
    }

    public static byte[] k3(byte[] n) {
        byte[] salt = s1(new byte[] {'s','m','k','3'});
        byte[] T = aesCmac(salt, n);
        byte[] tmp = new byte[] {'i', 'd', '6', '4', 0x01};
        byte[] result = new byte[8];
        tmp = aesCmac(T, tmp);
        System.arraycopy(tmp, 8, result, 0, 8);

        if (DEBUG) {
            Log.d(TAG, "\tk3 T :" + MeshUtils.bytesToHexString(T));
            Log.d(TAG, "\tk3 :" + MeshUtils.bytesToHexString(result));
        }
        return result;
    }

    public static byte k4(byte[] n) {
        byte[] salt = s1(new byte[] {'s','m','k','4'});
        byte[] T = aesCmac(salt, n);
        byte[] tmp = new byte[] {'i', 'd', '6', 0x01};
        byte[] result = aesCmac(T, tmp);

        if (DEBUG) {
            Log.d(TAG, "\tk4 T :" + MeshUtils.bytesToHexString(T));
            Log.d(TAG, "\tk4 :" + MeshUtils.bytesToHexString(result));
        }
        result[15] &= 0x3F;
        return result[15];
    }

    public static byte[] s1(byte[] m) {
        byte[] zero = new byte[16];
        byte[] result = aesCmac(zero, m);
        if (DEBUG) {
            Log.d(TAG, "\ts1 :" + MeshUtils.bytesToHexString(result));
        }
        return result;
    }

    /**
     *  Use default supported curve
     * @param curveName the predefined curve name, e.g. secp256r1
     * @return
     */

    static private KeyPair generateKeyPairNamedCurve(String curveName) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("ECDH", "SC");
            ECGenParameterSpec ecParamSpec = new ECGenParameterSpec(curveName);
            kpg.initialize(ecParamSpec);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return kpg.generateKeyPair();
    }

    /**
     *  Generate key pair based on FIPS P-256
     * @return
     */
    public static KeyPair generateKeyPair() {
        //ECParams ecp = ECParams.getParams("secp256r1");
        //KeyPair kp = generateKeyPairParams(ecp);
        KeyPair kp = generateKeyPairNamedCurve("secp256r1");
        return kp;
    }

    /**
     *
     * @param u the private key for calculating shared secret value
     * @param peerPk the public key in byte array format
     * @return the shared secret value calculated by u and peerPk
     */
    public static byte[] p256(PrivateKey u, byte[] peerPk) {
        try {
            KeyFactory kf = KeyFactory.getInstance("ECDH", "SC");
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
            //ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(1, privateKeyBytes), spec);
            ECNamedCurveSpec params = new ECNamedCurveSpec("secp256r1",
                    spec.getCurve(), spec.getG(), spec.getN());
            java.security.spec.ECPoint w = new java.security.spec.ECPoint(
                    new BigInteger(1, Arrays.copyOfRange(peerPk, 0, 32)),
                    new BigInteger(1, Arrays.copyOfRange(peerPk, 32, 64)));
            PublicKey publicKey = kf.generatePublic(
                    new java.security.spec.ECPublicKeySpec(w, params));

            return p256(u, publicKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] p256(byte[] privateKeyBytes, byte[] peerPkX, byte[] peerPkY) {
        try {
            KeyFactory kf = KeyFactory.getInstance("ECDH", "SC");
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");

            ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(1, privateKeyBytes), spec);

            ECNamedCurveSpec params = new ECNamedCurveSpec("secp256r1",
                    spec.getCurve(), spec.getG(), spec.getN());
            java.security.spec.ECPoint w = new java.security.spec.ECPoint(
                    new BigInteger(1, peerPkX),
                    new BigInteger(1, peerPkY));
            PublicKey publicKey = kf.generatePublic(
                    new java.security.spec.ECPublicKeySpec(w, params));
            PrivateKey privateKey = kf.generatePrivate(ecPrivateKeySpec);

            return p256(privateKey, publicKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] p256(PrivateKey u, byte[] peerPkX, byte[] peerPkY) {
        try {
            KeyFactory kf = KeyFactory.getInstance("ECDH", "SC");
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
            //ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(1, privateKeyBytes), spec);
            //Log.d(TAG, "a=" + spec.getCurve().getA().toString());
            //Log.d(TAG, "b=" + spec.getCurve().getB().toString());
            //Log.d(TAG, "G=" + spec.getG().toString());
            //Log.d(TAG, "N=" + spec.getN().toString());
            ECNamedCurveSpec params = new ECNamedCurveSpec("secp256r1",
                    spec.getCurve(), spec.getG(), spec.getN());
            java.security.spec.ECPoint w = new java.security.spec.ECPoint(
                    new BigInteger(1, peerPkX),
                    new BigInteger(1, peerPkY));
            PublicKey publicKey = kf.generatePublic(
                    new java.security.spec.ECPublicKeySpec(w, params));

            return p256(u, publicKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] p256(PrivateKey u, PublicKey v) {
        ECPublicKey ecPubKey = (ECPublicKey) v;
        ECPrivateKey ecPrvKey = (ECPrivateKey) u;
        Log.d(TAG, "public key Wx: "
                + ecPubKey.getW().getAffineX().toString(16));
        Log.d(TAG, "public key Wy: "
                + ecPubKey.getW().getAffineY().toString(16));

        KeyAgreement keyAgreement = null;
        try {
            keyAgreement = KeyAgreement.getInstance("ECDH", "SC");
            keyAgreement.init(ecPrvKey);
            keyAgreement.doPhase(v, true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return keyAgreement.generateSecret();
    }

    public static void test() {
        Log.d(TAG, "CryptoTool self-testing start >>>");
        byte[] result = MeshCrypto.aesCipher(mTestKey, mTestText, true);
        Log.d(TAG, "AES RESULT: " + MeshUtils.bytesToHexString(result));

        result = new byte[mTestCcmText.length];
        byte[] tag = new byte[8];
        MeshCrypto.aesCcm(mTestCcmKey, mTestCcmNonce, tag, mTestCcmAdd, mTestCcmText, result, true);
        Log.d(TAG, "CCM RESULT: " + MeshUtils.bytesToHexString(result));
        Log.d(TAG, "CCM TAG: " + MeshUtils.bytesToHexString(tag));

        result = MeshCrypto.aesCmac(mTestCmacKey, mTestCmacText40);
        Log.d(TAG, "CMAC RESULT :" + MeshUtils.bytesToHexString(result));

        result = MeshCrypto.s1(mTestS1Text);
        Log.d(TAG, "S1 RESULT :" + MeshUtils.bytesToHexString(result));

        result = MeshCrypto.k1(mTestK1N, mTestK1Salt, mTestK1P);
        Log.d(TAG, "K1 RESULT :" + MeshUtils.bytesToHexString(result));

        result = MeshCrypto.k2(mTestK2N, mTestK2P_m);
        Log.d(TAG, "K2 RESULT (Master):" + MeshUtils.bytesToHexString(result));
        result = MeshCrypto.k2(mTestK2N, mTestK2P_f);
        Log.d(TAG, "K2 RESULT (Friendship):" + MeshUtils.bytesToHexString(result));

        result = MeshCrypto.k3(mTestK3N);
        Log.d(TAG, "K3 RESULT :" + MeshUtils.bytesToHexString(result));

        result = new byte[1];
        result[0] = MeshCrypto.k4(mTestK4N);
        Log.d(TAG, "K4 RESULT :" + MeshUtils.bytesToHexString(result));
        Log.d(TAG, "CryptoTool self-testing end >>>");

        KeyPair pair1 = MeshCrypto.generateKeyPair();
        //KeyPair pair1 = MeshKeyGenerator.generateKeyPair();
        Log.d(TAG, "pub1="+MeshUtils.bytesToHexString(pair1.getPublic().getEncoded()));
        Log.d(TAG, "prv1="+MeshUtils.bytesToHexString(pair1.getPrivate().getEncoded()));
        KeyPair pair2 = MeshCrypto.generateKeyPair();
        //KeyPair pair2 = MeshKeyGenerator.generateKeyPair();
        Log.d(TAG, "pub2="+MeshUtils.bytesToHexString(pair2.getPublic().getEncoded()));
        Log.d(TAG, "prv2="+MeshUtils.bytesToHexString(pair2.getPrivate().getEncoded()));
        result = MeshCrypto.p256(pair1.getPrivate(), pair2.getPublic());
        Log.d(TAG, "P256 RESULT1 :"+MeshUtils.bytesToHexString(result));
        result = MeshCrypto.p256(pair2.getPrivate(), pair1.getPublic());
        Log.d(TAG, "P256 RESULT2 :"+MeshUtils.bytesToHexString(result));
    }
}
