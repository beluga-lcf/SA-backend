package com.example.genius.service;

import com.example.genius.dto.searchResult.SearchItem;
import com.example.genius.dto.searchResult.SearchRequest;
import com.example.genius.dto.searchResult.SearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;

public interface SearchService {
    public String complexSearch(JsonNode requestBody) throws Exception;

    public JsonNode getHotFields() throws Exception;
}
