package com.example.genius.service.impl;

import com.example.genius.config.Properties;
import com.example.genius.dto.payload.*;
import com.example.genius.service.ApiService;
import com.example.genius.util.ApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiServiceImpl implements ApiService{
    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        headers.set("Cookie", "XSRF-TOKEN=e01d46ee-1f94-4e60-ad16-3eed506b263b");
        headers.set("Origin", "https://pubscholar.cn");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
        headers.set("X-XSRF-TOKEN", "e01d46ee-1f94-4e60-ad16-3eed506b263b");
        headers.set("sec-ch-ua", "\"Microsoft Edge\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        return headers;
    }

    public String getArticles(ArticlesPayload payload, int type) throws Exception {
        // 样例：{"page":1,"size":10,"order_field":"date","order_direction":"desc","user_id":"3b9547dd87904c44923d675711729962","aggregations":{"type":"","subject":"","year":"","source":"","collection":"","lang":"","funding":"","institution":"","license":""}}
        // 使用Jackson库将对象转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());

        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/articles";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/articles/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);

        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }
    public String getBooks(BooksPayload payload, int type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/books";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/books/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }
    public String getBulletins(BulletinsPayload payload, int type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/bulletins";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/bulletin/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }

    public String getPatents(PatentsPayload payload, int type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/patents";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/patents/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }

    public String getReports(ReportsPayload payload, int type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/reports";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/reports/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }

    public String getSciencedata(SciencedataPayload payload, int type) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = "";
        if(type == 1) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/sciencedata";
        else if(type == 2) apiUrl = "https://pubscholar.cn/hky/open/resources/api/v1/sciencedata/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if(Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }
    public String getItems(JsonNode payload, String type) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String requestBody = objectMapper.writeValueAsString(payload);
        if (Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = ApiUtil.getScholarUrl(type);
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if (Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }
    public String getAggregations(JsonNode payload, String type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String requestBody = objectMapper.writeValueAsString(payload);
        if (Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = ApiUtil.getScholarUrl(type) + "/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            if (Properties.isDebug) System.out.println("Response: " + responseBody);
            return responseBody;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "error";
        }
    }

}
