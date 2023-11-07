package com.example.genius.util;

import java.util.Random;

public class StringUtil {
    public static String generateRandomCode() {
        int codeLength = 5;
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            // 生成随机大写字母 A-Z 对应 ASCII 码为 65-90
            char randomChar = (char) (random.nextInt(26) + 'A');
            code.append(randomChar);
        }

        return code.toString();
    }
}
