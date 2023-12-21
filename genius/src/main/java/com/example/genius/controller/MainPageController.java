package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.service.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/homepage")
public class MainPageController extends BaseController {
    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }
    @GetMapping("/getHotFields")
    public JsonNode getHotFields(){
        try {
            return searchService.getHotFields();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
