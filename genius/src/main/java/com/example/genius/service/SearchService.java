package com.example.genius.service;

import com.example.genius.dto.searchResult.SearchItem;
import com.example.genius.dto.searchResult.SearchResult;

import java.util.ArrayList;

public interface SearchService {
    public SearchResult FilterSearch(String conceptId, String type) throws Exception;
}
