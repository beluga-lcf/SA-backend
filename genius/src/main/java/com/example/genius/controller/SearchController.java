package com.example.genius.controller;

import com.example.genius.dto.payload.*;
import com.example.genius.dto.searchResult.SearchRequest;
import com.example.genius.service.ApiService;
import com.example.genius.service.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("/api/search")
public class SearchController extends BaseController {
    private ApiService apiService;
    private SearchService searchService;

    @Autowired
    public void setApiService(ApiService apiService){
        this.apiService = apiService;
    }
    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    @PostMapping("/complexSearch")
    public String complexSearch(@RequestBody JsonNode requestBody){
        try {
            return searchService.complexSearch(requestBody);
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/articles")
    public String getArticle(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody,"articles");
        }
        catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/articles/aggregations")
    public JsonNode getArticleAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"articles");
        }
        catch (Exception e){
            return null;
        }
    }

    @PostMapping("/patents")
    public String getPatents(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody,"patents");
        }
        catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/patents/aggregations")
    public JsonNode getPatentAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"patents");
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/books")
    public String getBooks(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody, "books");
        }
        catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/books/aggregations")
    public JsonNode getBookAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"books");
        }
        catch (Exception e){
            return null;
        }
    }
    @PostMapping("/bulletins")
    public String getBulletins(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody, "bulletins");
        }
        catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/bulletins/aggregations")
    public JsonNode getBulletinAggregation(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"bulletin");
        }
        catch (Exception e){
            return null;
        }
    }
    @PostMapping("/reports")
    public String getReports(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody, "reports");
        }
        catch (Exception e){
            e.printStackTrace();
            return "error !!!";
        }
    }

    @PostMapping("/reports/aggregations")
    public JsonNode getPatentAggregation(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"reports");
        }
        catch (Exception e){
            return null;
        }
    }
    @PostMapping("/sciencedata")
    public String getSciencedata(@RequestBody JsonNode requestBody){
        try {
            return apiService.getItems(requestBody, "sciencedata");
        }
        catch (Exception e){
            e.printStackTrace();
            return "error !!!";
        }
    }

    @PostMapping("/sciencedata/aggregations")
    public JsonNode getSciencedataAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"sciencedata");
        }
        catch (Exception e){
            return null;
        }
    }

    @PostMapping("/test")
    public JsonNode testHeaders(@RequestBody JsonNode requestBody){
        try {
            return apiService.test(requestBody);
        }
        catch (Exception e){
            return null;
        }
    }





}
