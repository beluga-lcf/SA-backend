package com.example.genius.util;

import com.example.genius.dto.searchResult.LanguageEnum;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

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
    public static String removePrefix(String input, String prefix) {
        if (input.startsWith(prefix)) {
            return input.substring(prefix.length());
        }
        return input;
    }

    public static String removePrefix(String input){
        return input.substring("https://openalex.org/".length());
    }

    public static String Or(ArrayList<String> types) {
        // 使用StringJoiner连接每个字符串
        StringJoiner joiner = new StringJoiner("|");

        // 遍历ArrayList，将每个字符串加入StringJoiner
        for (String type : types) {
            joiner.add(type);
        }

        // 返回连接后的字符串
        return joiner.toString();
    }

    public static String spaceReplace(String input){
        return input.replace(" ", "%20");
    }

}
