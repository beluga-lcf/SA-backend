package com.example.genius.service;

import com.example.genius.dto.searchResult.SearchItem;
import com.example.genius.dto.searchResult.SearchRequest;
import com.example.genius.dto.searchResult.SearchResult;

import java.util.ArrayList;

public interface SearchService {
    public SearchResult FilterSearch(String conceptName, String type) throws Exception;
    public SearchResult ComplexSearch(SearchRequest searchRequest) throws Exception;
}
