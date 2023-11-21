package com.example.genius.dto.searchResult;

import java.util.ArrayList;

public class SearchRequest {
    // filter
    public ArrayList<String> types;
    public ArrayList<String> subjects; // AI, Physics, Math
    public ArrayList<Integer> publicationYears;
    public ArrayList<String> languages;
    // sort
    public String order_direction; // asc or desc, 默认desc
    public String order_field;
    // search
    public String searchContent;
    // page
    public int pageNo;
    public int pageSize;

}
