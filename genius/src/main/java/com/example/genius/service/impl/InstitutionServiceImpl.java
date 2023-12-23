package com.example.genius.service.impl;

import com.example.genius.service.InstitutionService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // 先剔除一部分没用的数据，避免干扰其前端同学
        ObjectNode objectNode = objectMapper.convertValue(institution, ObjectNode.class);
        objectNode.remove("lineage");
        objectNode.remove("international");
        // 作者
        String url2 = "https://api.openalex.org/authors";
        String params2 = "filter=" + "last_known_institution.id:" + institutionId;
        String result2 = ApiUtil.get(url2, params2);
        JsonNode allAuthors = objectMapper.readTree(result2).get("results");
        ArrayNode arrayNode = objectMapper.convertValue(allAuthors, ArrayNode.class);
        ArrayNode authorList = objectMapper.createArrayNode();
        for (int i = 0; i < Math.min(5, arrayNode.size()); i++) {
            JsonNode newAuthor = simplifyAuthor(arrayNode.get(i));
            authorList.add(newAuthor);
        }
        // 遍历数组，取前五个
        objectNode.set("authors", authorList);
        return  objectMapper.convertValue(objectNode, JsonNode.class);
    }

    // 简化机构中的作者信息
    public JsonNode simplifyAuthor(JsonNode oriNode){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.set("id",oriNode.get("id"));
        objectNode.set("display_name",oriNode.get("display_name"));
        objectNode.set("works_count",oriNode.get("works_count"));
        objectNode.set("cited_by_count",oriNode.get("cited_by_count"));
        return objectMapper.convertValue(objectNode,JsonNode.class);
    }

    public JsonNode getInstitutionWorks(String institutionId) throws JsonProcessingException {
        String url = "https://api.openalex.org/works";
        String params = "filter=" + "institutions.id:" + institutionId;
        String result = ApiUtil.get(url, params);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode allWorks = objectMapper.readTree(result).get("results");
        ArrayNode arrayNode = objectMapper.convertValue(allWorks,ArrayNode.class);
        ArrayNode workList = objectMapper.createArrayNode();
        for (int i = 0; i < Math.min(5, arrayNode.size()); i++) {
            JsonNode newWork = arrayNode.get(i);
            workList.add(newWork);
        }
        return objectMapper.convertValue(workList,JsonNode.class);
    }

    public JsonNode simplifyWork(JsonNode oriNode) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode newWork = objectMapper.createObjectNode();
        newWork.set("id",oriNode.get("id"));
        newWork.set("title",oriNode.get("title"));
        // 日期
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy(MM)");
        Date date = inputFormat.parse(oriNode.get("publication_date").asText());
        String outputDateString = outputFormat.format(date);
        newWork.put("date",outputDateString);
        // 作者
        ArrayNode oriAuthors = objectMapper.convertValue(oriNode.get("authorships"),ArrayNode.class);
        ArrayList<String> newAuthors = new ArrayList<>();
        for (int i = 0; i < Math.min(5, oriAuthors.size()); i++){
            JsonNode author = oriAuthors.get(i);
//            newAuthors.add(author.)
        }
        return null;

    }
}


