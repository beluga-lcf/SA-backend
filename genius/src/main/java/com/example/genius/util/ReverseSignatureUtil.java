package com.example.genius.util;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReverseSignatureUtil {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foocrg227gng6m6fbo95inwakpingbti");

        String timestamp=createTimestamp();
        stringBuilder.append(timestamp);
        String nonce=createNonce();
        stringBuilder.append(nonce);

        System.out.println(nonce);
        System.out.println(timestamp);
//        System.out.println(stringBuilder.toString());
//        stringBuilder.append("1702995786128");
//        stringBuilder.append("F8GJIJ");
        System.out.println(createSHA1(stringBuilder.toString()));

    }
    public static String createSHA1(String hh) {
        try {
            // 创建一个MessageDigest实例用于SHA1加密
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            // 将输入字符串转换为字节数组并加密
            byte[] encodedHash = digest.digest(hh.getBytes());
            // 将加密后的字节转换为十六进制字符串
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            // 异常处理
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static String createNonce() {
        SecureRandom random = new SecureRandom();
        StringBuilder nonce = new StringBuilder();
        while (nonce.length() < 6) {
            nonce.append(Integer.toString(random.nextInt(36), 36).toUpperCase());
        }
        return nonce.substring(0, 6);
    }
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }
    public static String createSignature(String input) {
        // 将输入字符串转换为字节数组
        byte[] inputBytes = input.getBytes();
        int[] inputWords = bytesToWords(inputBytes);
        int inputLength = inputBytes.length;

        // 初始化哈希值
        int h0 = 0x67452301;
        int h1 = 0xEFCDAB89;
        int h2 = 0x98BADCFE;
        int h3 = 0x10325476;
        int h4 = 0xC3D2E1F0;

        // 处理输入字符串
        inputWords = processInput(inputWords, inputLength);

        // 主循环
        int[] buffer = new int[80];
        for (int i = 0; i < inputWords.length; i += 16) {
            // 初始化变量
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;

            for (int j = 0; j < 80; j++) {
                if (j < 16) buffer[j] = inputWords[i + j];
                else buffer[j] = rotateLeft(buffer[j - 3] ^ buffer[j - 8] ^ buffer[j - 14] ^ buffer[j - 16], 1);

                int temp = rotateLeft(a, 5) + e + buffer[j] + ft(j, b, c, d);
                e = d;
                d = c;
                c = rotateLeft(b, 30);
                b = a;
                a = temp;
            }

            // 更新哈希值
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
        }

        // 组装最终的哈希值
        byte[] finalHash = wordsToBytes(new int[]{h0, h1, h2, h3, h4});
        return bytesToHex(finalHash);
    }

    private static int[] processInput(int[] words, int length) {
        int bitsLength = length * 8;

        // 将长度扩展至符合512位块的要求
        int paddedLength = ((bitsLength + 64) / 512 + 1) * 512;
        int[] paddedWords = new int[paddedLength / 32];

        // 复制原始数据
        System.arraycopy(words, 0, paddedWords, 0, words.length);

        // 添加1位和0位填充
        int index = bitsLength / 32;
        paddedWords[index] |= 1 << (31 - (bitsLength % 32));
        paddedWords[paddedWords.length - 2] = bitsLength >>> 32;
        paddedWords[paddedWords.length - 1] = bitsLength & 0xFFFFFFFF;

        return paddedWords;
    }


    private static int rotateLeft(int n, int distance) {
        return (n << distance) | (n >>> (32 - distance));
    }

    private static int ft(int t, int b, int c, int d) {
        if (t < 20) return (b & c) | ((~b) & d);
        if (t < 40) return b ^ c ^ d;
        if (t < 60) return (b & c) | (b & d) | (c & d);
        return b ^ c ^ d;
    }
    public static int[] bytesToWords(byte[] bytes) {
        int[] words = new int[bytes.length / 4];
        int byteIndex = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = ((bytes[byteIndex++] & 0xFF) << 24) |
                    ((bytes[byteIndex++] & 0xFF) << 16) |
                    ((bytes[byteIndex++] & 0xFF) << 8) |
                    (bytes[byteIndex++] & 0xFF);
        }
        return words;
    }

    // Converts a word array to a byte array
    public static byte[] wordsToBytes(int[] words) {
        byte[] bytes = new byte[words.length * 4];
        int byteIndex = 0;
        for (int word : words) {
            bytes[byteIndex++] = (byte) (word >> 24);
            bytes[byteIndex++] = (byte) (word >> 16);
            bytes[byteIndex++] = (byte) (word >> 8);
            bytes[byteIndex++] = (byte) word;
        }
        return bytes;
    }

    // Converts a byte array to a hexadecimal string
//    public static String bytesToHex(byte[] bytes) {
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : bytes) {
//            String hex = Integer.toHexString(0xFF & b);
//            if (hex.length() == 1) {
//                hexString.append('0');
//            }
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }

    // Converts a hexadecimal string to a byte array
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return bytes;
    }
}
