package com.example.genius.service.impl;

import com.example.genius.service.InstitutionService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Iterator;
import java.util.Map;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    public JsonNode getOpenalexIdFromInstitutionName(String institutionName) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/institutions";
        String params = "search=" + institutionName;
        String result = ApiUtil.get(url, params);
        JsonNode jsonNode = objectMapper.readTree(result);
        return  jsonNode.get("results").get(0).get("id");
    }
    public JsonNode searchInstitution(String institutionName) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/institutions";
        String params = "search=" + institutionName;
        String result = ApiUtil.get(url, params);
        JsonNode jsonNode = objectMapper.readTree(result);
        return  jsonNode.get("results").get(0);
    }

    public JsonNode getInstitutionHomePage(String institutionName) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/institutions";
        String params = "search=" + StringUtil.spaceReplace(institutionName);
        String result = ApiUtil.get(url, params);
        JsonNode jsonNode = objectMapper.readTree(result);
        JsonNode institution = jsonNode.get("results").get(0);
        String institutionId = institution.get("id").toString();
        // 作者
        String url2 = "https://api.openalex.org/authors";
        String params2 = "filter=" + "last_known_institution.id:" + institutionId;
        String result2 = ApiUtil.get(url2, params2);
        JsonNode allAuthors = objectMapper.readTree(result2).get("results");
        ArrayNode arrayNode = objectMapper.convertValue(allAuthors, ArrayNode.class);
        ArrayNode authorList = objectMapper.createArrayNode();
        for (int i = 0; i < Math.min(5, arrayNode.size()); i++) {
            authorList.add(arrayNode.get(i));
        }
        // 遍历数组，取前五个
        ObjectNode objectNode = objectMapper.convertValue(institution, ObjectNode.class);
        objectNode.set("authors", authorList);
        return  objectMapper.convertValue(objectNode, JsonNode.class);
    }
}
