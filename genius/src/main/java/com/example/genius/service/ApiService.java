package com.example.genius.service;

import com.example.genius.dto.payload.*;
import com.fasterxml.jackson.databind.JsonNode;
import springfox.documentation.spring.web.json.Json;

public interface ApiService {
    public String getArticles(ArticlesPayload payload, int type) throws Exception;
    public String getPatents(PatentsPayload payload, int type) throws Exception;
    public String getBooks(BooksPayload payload, int type) throws Exception;
    public String getBulletins(BulletinsPayload payload, int type) throws Exception;
    public String getReports(ReportsPayload payload, int type) throws Exception;
    public String getSciencedata(SciencedataPayload payload, int type) throws Exception;
    public String getItems(JsonNode payload, String type) throws Exception ;
    public JsonNode getAggregations(JsonNode payload, String type) throws Exception;
    public String complex(JsonNode payload, String type) throws Exception;

    public JsonNode test(JsonNode payload) throws Exception;

}
