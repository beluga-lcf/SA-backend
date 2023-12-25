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
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    public JsonNode getAllFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode fields = objectMapper.createObjectNode();
        fields.put("论文", "95,888,519");
        fields.put("专利", "80,412,855");
        fields.put("领域快报", "8377");
        fields.put("动态快讯", "9379");
        fields.put("科学数据","520,320");
        fields.put("图书", "123,760");
        return fields;
    }
}
