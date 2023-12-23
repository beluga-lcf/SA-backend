package com.example.genius.service.impl;

import com.example.genius.config.Properties;
import com.example.genius.dto.payload.*;
import com.example.genius.service.ApiService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.ReverseSignatureUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import javax.swing.tree.TreeNode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static com.example.genius.util.ReverseSignatureUtil.*;

@Service
public class ApiServiceImpl implements ApiService{

    public String getArticles(ArticlesPayload payload, int type) throws Exception {
        // 样例：{"page":1,"size":10,"order_field":"date","order_direction":"desc","user_id":"3b9547dd87904c44923d675711729962","aggregations":{"type":"","subject":"","year":"","source":"","collection":"","lang":"","funding":"","institution":"","license":""}}
        // 使用Jackson库将对象转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(payload);
        if(Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());

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
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
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
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
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
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
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
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
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
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
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
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//        String requestBody = objectMapper.writeValueAsString(payload);
//        if (Properties.isDebug) System.out.println("Request: " + requestBody);
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
//        // 使用RestTemplate发起POST请求
//        String apiUrl = ApiUtil.getScholarUrl(type);
//        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
//        String responseBody;
//        // 处理响应
//        if (response.getStatusCode().is2xxSuccessful()) {
//            responseBody = response.getBody();
//            if (Properties.isDebug) System.out.println("Response: " + responseBody);
//            return responseBody;
//        } else {
//            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
//            return "error";
//        }
        return complex(payload,type,false);
    }
    public JsonNode getAggregations(JsonNode payload, String type) throws Exception{

//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//        String requestBody = objectMapper.writeValueAsString(payload);
//        if (Properties.isDebug) System.out.println("Request: " + requestBody);
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
//        // 使用RestTemplate发起POST请求
//        String apiUrl = ApiUtil.getScholarUrl(type) + "/aggregations";
//        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = complex(payload,type,true);
        // 处理响应
        JsonNode originalNode = objectMapper.readTree(responseBody);
        ArrayNode newNode = objectMapper.createArrayNode();
        // 获取所有字段名（key）
        Iterator<String> fieldNames = originalNode.fieldNames();
        // 遍历所有字段名
        while (fieldNames.hasNext()) { //迭代器的初始位置是在集合的第一个元素之前,不会漏掉元素
            ObjectNode tagNode = objectMapper.createObjectNode();
            String key = fieldNames.next();
            tagNode.put("tag",key);
            tagNode.set("content",originalNode.get(key));
            newNode.add(tagNode);
        }
        if (Properties.isDebug) System.out.println("Response: " + responseBody);
        return newNode;


    }

    public String complex(JsonNode payload, String type, boolean isAggregations) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        // 处理strategy参数
        JsonNode strategyNode = payload.get("strategy");
        String type_of_strategy = strategyNode.get("type").asText();
        String content_of_strategy = strategyNode.get("content").asText();
        String converted = "";
        if(type_of_strategy.equals("advanced")){
            converted = StringUtil.convert_strategy_for_advanced(content_of_strategy);
        }
        else if(type_of_strategy.equals("professional")){
            converted = StringUtil.convert_strategy_for_professional(content_of_strategy);
        }
        else if(type_of_strategy.equals("simple")){
            converted = content_of_strategy;
        }
        // 若strategy content为空，则final strategy也为空，没事
        // 转换为新的payload
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.setAll(objectMapper.convertValue(payload,ObjectNode.class));
        String finalStrategy = StringUtil.encodeStrategy(converted);
        objectNode.put("strategy",finalStrategy);
        String requestBody = objectMapper.writeValueAsString(objectNode);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
        if(Properties.isDebug){
            System.out.println("original: "+content_of_strategy);
            System.out.println("Converted: " + converted);
            System.out.println("Final strategy: " + finalStrategy);
            System.out.println("Request: " + requestBody);
        }
        // 使用RestTemplate发起POST请求
        String apiUrl = ApiUtil.getScholarUrl(type);
        apiUrl+= isAggregations?"/aggregations":"";
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
    public JsonNode test(JsonNode payload) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String requestBody = objectMapper.writeValueAsString(payload);
        if (Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
        // 使用RestTemplate发起POST请求
        String apiUrl = ApiUtil.getScholarUrl("articles") + "/aggregations";
        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String responseBody;
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();
            JsonNode originalNode = objectMapper.readTree(responseBody);
            ArrayNode newNode = objectMapper.createArrayNode();
            // 获取所有字段名（key）
            Iterator<String> fieldNames = originalNode.fieldNames();
            // 遍历所有字段名
            while (fieldNames.hasNext()) {
                ObjectNode tagNode = objectMapper.createObjectNode();
                String key = fieldNames.next();
                tagNode.put("tag",key);
                tagNode.set("content",originalNode.get(key));
                newNode.add(tagNode);
            }
            if (Properties.isDebug) System.out.println("Response: " + responseBody);
            return newNode;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return null;
        }
    }

}
