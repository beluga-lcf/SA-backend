package com.example.genius.service.impl;

import com.example.genius.dto.payload.ArticlesPayload;
import com.example.genius.dto.searchResult.*;
import com.example.genius.dto.workDisplay.InnerAuthor;
import com.example.genius.dto.workDisplay.InnerLocation;
import com.example.genius.dto.workDisplay.InnerSource;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.service.ApiService;
import com.example.genius.service.SearchService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import javax.xml.transform.Source;
import java.util.ArrayList;

import static com.example.genius.dto.searchResult.LanguageEnum.getLanguageCode;
import static com.example.genius.dto.searchResult.SubjectEnum.getConceptId;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ApiService apiService;

    @Override
    public String complexSearch(JsonNode requestBody) throws Exception {
        return null;
    }

    @Override
    public JsonNode getHotFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticlesPayload articlesPayload = new ArticlesPayload();
        System.out.println(articlesPayload);
        JsonNode payload = objectMapper.convertValue(articlesPayload, JsonNode.class);
        JsonNode resultNode= apiService.getAggregations(payload, "articles");
        ArrayNode arrayNode = objectMapper.convertValue(resultNode, ArrayNode.class);
        return arrayNode.get(1).get("content");
    }
}
