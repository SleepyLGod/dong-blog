package com.lhd.mylblog.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {

    /**
     * 获得Md5加密
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String strToMd5(String str) {
        String md5Str = null;
        if (str != null && str.length() != 0) {
            try {
                // 创建一个MessageDigest实例:
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 调用update输入数据:
                md.update(str.getBytes());
                byte result[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < result.length; ++offset) {
                    i = result[offset];
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(i));
                }
                //32位
                md5Str = buf.toString();
                return md5Str;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获得AES加密(CBC模式)
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String strToAES(String str) {
        String aesStr = null;
        if (str != null && str.length() != 0) {
            try {
                // 256位密钥 = 32 bytes Key:
                byte[] key = "1234567890abcdef1234567890abcdef".getBytes(StandardCharsets.UTF_8);
                // 加密:
                byte[] data = str.getBytes(StandardCharsets.UTF_8);
                byte[] encrypted = encrypt(key, data);
                System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
                aesStr = Base64.getEncoder().encodeToString(encrypted);
                // 解密:
                byte[] decrypted = decrypt(key, encrypted);
                System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
                String antiAesStr = new String(decrypted, StandardCharsets.UTF_8);
                return aesStr;
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    // 加密:
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回:
        return join(iv, data);
    }
    // 解密:
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密:
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }
    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }

}