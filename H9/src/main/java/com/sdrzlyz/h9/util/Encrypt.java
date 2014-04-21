package com.sdrzlyz.h9.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sdrzlyz on 14-3-24.
 */
public class Encrypt {

    //加密密码与解密密钥
    private static final String psw4encrypt = "6F1263A47AA5F184AD0DAB04188FC6B8DE55FC6A4631BB4833CC4CD7";

//    private static String byte2hex1(byte[] bytes) {
//        String str = "";
//        String tempStr = "";
//        int len = bytes.length;
//
//        for (int i = 0; i < len; i++) {
//            tempStr = Integer.toHexString(bytes[i] & 0XFF);
//            if (tempStr.length() == 1) {
//                str = str + "0" + tempStr;
//            } else {
//                str = str + tempStr;
//            }
//        }
//        return str.toUpperCase();
//    }

//与上面的算法相同，但是暂时不使用下面的算法
//    private static String byte2hex2(byte[] bytes){
//       if (bytes == null){
//           return null;
//       }
//        int len = bytes.length;
//        String str = "";
//        for(int i=0;i<len;i++){
//            if ((bytes[i]&0XFF) <16){
//                str = str+"0"+Integer.toHexString(bytes[i]&0XFF);
//            }else {
//                str =str+Integer.toHexString(bytes[i]&0XFF);
//            }
//        }
//        return str.toUpperCase();
//    }

    /**
     * @param input
     * @return String
     */
    public static String encryptSHA(String input) {
        String outStr = "";
        MessageDigest alga = null;
        try {
            alga = MessageDigest.getInstance("SHA-1");
            byte[] digest = alga.digest(input.getBytes());
            outStr = parseByte2HexStr(digest);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.i("encryptSHA error", "NO PSW");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("啊？加密算法不存在!!");
        }
        return outStr;
    }


    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encryptPsw(String content) {
        try {
            //Test.LogD("开始加密");
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            secureRandom.setSeed(psw4encrypt.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] temp = cipher.doFinal(byteContent);
            return parseByte2HexStr(temp); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     *
     * @param password 待解密内容
     * @return
     */
    public static String decryptPsw(String password) {
        try {
            //Test.LogD("开始解密");
            byte[] content = parseHexStr2Byte(password);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            secureRandom.setSeed(psw4encrypt.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] temp = cipher.doFinal(content);
            return new String(temp); // 加密
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    //TODO 测试用，正式发布时要删掉
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("加密对象：huagai");
        System.out.println("应该输出：AE28692B3C6699CCB5EE271C5D02EE19C122BD0F");
        System.out.println("实际输出：" + encryptSHA("huagai"));

        //密码本地化加解密测试
        String content = "\u0073\u0064\u0072\u007a\u006c\u0079\u007a\u0040\u0031\u0032\u0036\u002e\u0063\u006f\u006d";
        System.out.println("加密前：" + content);
        String encryptResultStr = encryptPsw(content);
        System.out.println("加密后：" + encryptResultStr);
        String decryptResult = decryptPsw(encryptResultStr);
        System.out.println("解密后：" + decryptResult);
    }
}
