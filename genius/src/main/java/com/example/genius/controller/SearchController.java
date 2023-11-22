package com.example.genius.controller;

import com.example.genius.dto.payload.*;
import com.example.genius.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("/api/search")
public class SearchController extends BaseController {
    private ApiService apiService;
    @Autowired
    public void setApiService(ApiService apiService){
        this.apiService = apiService;
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
    public String getArticleAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"articles");
        }
        catch (Exception e){
            return "error";
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
    public String getPatentAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"patents");
        }
        catch (Exception e){
            e.printStackTrace();
            return "error !!!";
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
    public String getBookAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"books");
        }
        catch (Exception e){
            return "error";
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
    public String getBulletinAggregation(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"bulletin");
        }
        catch (Exception e){
            return "error";
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
    public String getPatentAggregation(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"reports");
        }
        catch (Exception e){
            return "error";
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
    public String getSciencedataAggregations(@RequestBody JsonNode requestBody){
        try {
            return apiService.getAggregations(requestBody,"sciencedata");
        }
        catch (Exception e){
            return "error";
        }
    }



}
