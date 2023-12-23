package com.example.genius.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.util.Base64;

public class ReverseAESUtil {
    public static String encrypt(String input) {
        try {
            byte[] keyBytes=convertWordsToBytes(new int[]{1901552978, 1952019785, 1867538771, 1682335560},16);
            byte[] ivBytes=convertWordsToBytes(new int[]{1248545914, 1482905186, 1315778617, 943142453},16);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(Base64.getEncoder().encodeToString(encrypted).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static byte[] convertWordsToBytes(int[] words, int sigBytes) {
        ByteBuffer buffer = ByteBuffer.allocate(sigBytes);
        for (int i = 0; i < sigBytes; i++) {
            int word = words[i / 4];
            int shift = (3 - (i % 4)) * 8;
            buffer.put(i, (byte) ((word >> shift) & 0xFF));
        }
        return buffer.array();
    }

    public static void main(String[] args) {
        String originalString = "c6f51ede40a548e08e9cc5c5f57fba31";//要加密的原字符串
        System.out.println(encrypt(originalString));
    }
}

