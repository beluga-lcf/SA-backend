package com.example.genius.dto.searchResult;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SearchResult {
    public int count;
    public ArrayList<SearchItem> items;
}
