package com.example.genius.controller;

import com.example.genius.dto.searchResult.SearchResult;
import com.example.genius.entity.Response;
import com.example.genius.service.SearchService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/search")
public class SearchController extends BaseController {
    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/filter")
    public Response<Object> filterSearch(String type, String conceptId){
        try {
            SearchResult result = searchService.FilterSearch(conceptId, type);
            return getSuccessResponse(result);
        }
        catch (Exception e) {
            log.error("here is an error!");
            return getSimpleError();
        }
    }
}
