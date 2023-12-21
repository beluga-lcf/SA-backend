package com.example.genius.util;

import com.example.genius.dto.searchResult.LanguageEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        //将url编码转换为正常格式
        String encodedString = "(title%3A(haha~%20))%20AND%20(keyword%3A%22nihao~%20%22)";
        String decodedString = URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
        System.out.println(decodedString);
    }

}
