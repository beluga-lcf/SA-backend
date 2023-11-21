package com.example.genius.controller;

import com.example.genius.dto.payload.*;
import com.example.genius.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController extends BaseController {
    private ApiService apiService;
    @Autowired
    public void setApiService(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/article")
    public String getArticle(@RequestBody ArticlesPayload payload){
        try {
            return apiService.getArticles(payload,1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/article/classifyTag")
    public String getArticleAggregation(@RequestBody ArticlesPayload payload){
        try {
            return apiService.getArticles(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/patent")
    public String getPatent(@RequestBody PatentsPayload payload){
        try {
            return apiService.getPatents(payload,1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/patent/classifyTag")
    public String getPatentAggregation(@RequestBody PatentsPayload payload){
        try {
            return apiService.getPatents(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/book")
    public String getPatent(@RequestBody BooksPayload payload){
        try {
            return apiService.getBooks(payload,1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/book/classifyTag")
    public String getPatentAggregation(@RequestBody BooksPayload payload){
        try {
            return apiService.getBooks(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }
    @GetMapping("/bulletin")
    public String getPatent(@RequestBody BulletinsPayload payload){
        try {
            return apiService.getBulletins(payload,1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/bulletin/classifyTag")
    public String getPatentAggregation(@RequestBody BulletinsPayload payload){
        try {
            return apiService.getBulletins(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }
    @GetMapping("/report")
    public String getPatent(@RequestBody ReportsPayload payload){
        try {
            return apiService.getReports(payload,1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/report/classifyTag")
    public String getPatentAggregation(@RequestBody ReportsPayload payload){
        try {
            return apiService.getReports(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }
    @GetMapping("/sciencedata")
    public String getPatent(@RequestBody SciencedataPayload payload){
        try {
            return apiService.getSciencedata(payload, 1);
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/sciencedata/classifyTag")
    public String getPatentAggregation(@RequestBody SciencedataPayload payload){
        try {
            return apiService.getSciencedata(payload,2);
        }
        catch (Exception e){
            return "error";
        }
    }


}
