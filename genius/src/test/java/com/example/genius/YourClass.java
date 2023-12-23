package com.example.genius;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

public class YourClass {
    @Test
    public void getAllAuthorIds() {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = "{\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"authorships\": [\n" +
                "                {\n" +
                "                    \"author\": {\n" +
                "                        \"id\": \"https://openalex.org/A5063707907\",\n" +
                "                        \"display_name\": \"Karin Meyer\",\n" +
                "                        \"orcid\": \"https://orcid.org/0000-0003-2663-9059\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"authorships\": [\n" +
                "                {\n" +
                "                    \"author\": {\n" +
                "                        \"id\": \"https://openalex.org/A5086990171\",\n" +
                "                        \"display_name\": \"Andrew J. Studer\",\n" +
                "                        \"orcid\": \"https://orcid.org/0000-0002-0900-5655\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        String jsonResponse = str;

        Set<String> authorIdsSet = new HashSet<>();

        try {
            // 解析JSON字符串为JsonNode对象
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // 获取"results"数组节点
            JsonNode resultsNode = rootNode.get("results");

            // 遍历结果数组
            for (JsonNode resultNode : resultsNode) {
                JsonNode authorshipsNode = resultNode.get("authorships");

                for (JsonNode authorshipNode : authorshipsNode) {
                    JsonNode authorNode = authorshipNode.get("author");
                    String authorId = authorNode.get("id").asText();

                    // 将作者ID添加到Set中
                    authorIdsSet.add(authorId);
                }
            }
        } catch (Exception e) {
            // 处理异常
            System.out.println("Error!");
            e.printStackTrace();
        }
        System.out.println("Success");
        for (String authorId : authorIdsSet) {
            System.out.println(authorId);
        }
    }
}
