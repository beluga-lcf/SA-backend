package com.example.genius.util;

import com.example.genius.dto.searchResult.LanguageEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

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

    public static String encodeStrategy(String input){
        String output = input
                .replace(":", "%3A") // 左括号
                .replace(" ", "%20")
                .replace("\"","%22")
                .replace("[","%5B")
                .replace("]","%5D"); // 右括号
        return output;
    }

    public static String convert_strategy_for_advanced(String input){
        //(title:(1)) AND (keyword:"2") NOT (author:(3~ )) AND (year:[2020 TO 2021]),这里~后有一个空格注意一下
        // title=1 AND keyword=2 NOT author=3~ AND year=2020,2021
         StringBuilder result = new StringBuilder();
        // 利用正则表达式分割字符串
        String[] clauses = input.split(" ");
        // 解析每个子字符串并输出
        for (int i = 0; i < clauses.length; i++) {
            String clause = clauses[i];
            if(i%2==0){
                String[] parts = clause.split("=", 2);
                String key = parts[0];
                String value = parts[1];
                if(key.equals("keyword")){
                    value = "\"" + value + "\"";
                }
                else if(key.equals("year")){
                    String[] years = value.split(",");
                    value = "[" + years[0] + " TO " + years[1] + "]";
                }
                else{
                    value = "("+value+")";
                }
                result.append("(").append(key).append(":").append(value).append(")");
            }
            else{
                result.append(clause);
            }
            if(i!=clauses.length-1){
                result.append(" ");
            }
        }
        return result.toString();
    }
    public static String convert_strategy_for_professional(String input){
        // title:大数据 AND keyword:经济 NOT author:张*
        // TI=大数据 AND KY=经济 NOT AU=张*
//        TS=主题 , TI=题名 , KY=关键词 , AU=作者 , AF=机构 , AB=摘要 , SO=期刊 , PY=出版年
        HashMap map = new HashMap<String, String>();
        map.put("TI", "title");
        map.put("KY", "keyword");
        map.put("AU", "author");
        map.put("PY", "year");
        map.put("TS", "subject");
        map.put("AF", "institution");
        map.put("AB", "abstract");
        map.put("SO", "journal");
        StringBuilder result = new StringBuilder();
        // 利用正则表达式分割字符串
        String[] clauses = input.split(" ");
        // 解析每个子字符串并输出
        for (int i = 0; i < clauses.length; i++) {
            String clause = clauses[i];
            if(i%2==0){
                String[] parts = clause.split("=", 2);
                String key = parts[0];
                String value = parts[1];
                result.append(map.get(key)).append(":").append(value);
            }
            else{
                result.append(clause);
            }
            if(i!=clauses.length-1){
                result.append(" ");
            }
        }
        return result.toString();
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        //将url编码转换为正常格式
        String str1 = "title=1 AND keyword=2 NOT author=3~ AND year=2020,2021";
        String out1 = convert_strategy_for_advanced(str1);
        String str2 = "TI=大数据 AND KY=经济 NOT AU=张*";
        String out2 = convert_strategy_for_professional(str2);
        String str3 = "%20title%3A%E5%A4%A7%E6%95%B0%E6%8D%AE%20AND%20keyword%3A%E7%BB%8F%E6%B5%8E%20NOT%20author%3A%E5%BC%A0*%20";
        String out3 = URLDecoder.decode(str3, "UTF-8");
        System.out.println("advanced: "+out1);
        System.out.println("professional: "+out2);
        System.out.println("decode: "+out3);
    }


}
