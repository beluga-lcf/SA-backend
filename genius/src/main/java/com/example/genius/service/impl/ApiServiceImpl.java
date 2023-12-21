package com.example.genius.service.impl;

import com.example.genius.config.Properties;
import com.example.genius.dto.payload.*;
import com.example.genius.service.ApiService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.ReverseSignatureUtil;
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
import java.util.Iterator;

import static com.example.genius.util.ReverseSignatureUtil.*;

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
        // 破译
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foocrg227gng6m6fbo95inwakpingbti");
        String timestamp=createTimestamp();
        stringBuilder.append(timestamp);
        String nonce=createNonce();
        stringBuilder.append(nonce);
        String signature=createSHA1(stringBuilder.toString());
        //
        headers.set("timestamp", timestamp);
        headers.set("nonce", nonce);
        headers.set("signature", signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(signature);
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
    public JsonNode getAggregations(JsonNode payload, String type) throws Exception{
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

    public String complex(JsonNode payload,String type) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        // 处理strategy参数
        JsonNode strategyNode = payload.get("strategy");
        ArrayNode strategys = objectMapper.convertValue(strategyNode,ArrayNode.class);
        StringBuilder result_strategy_builder = new StringBuilder();
        int size = strategys.size();
        for(JsonNode strategy: strategys){
            StringBuilder oneBuilder = new StringBuilder();
            boolean isFirst = false;
            if(strategy.equals(strategys.get(0))){
                isFirst = true;
            }
            JsonNode op = strategy.get("op");
            JsonNode tag = strategy.get("tag");
            JsonNode content = strategy.get("content");
            String item;
            if(content.isNumber()){
                item = package_one_strategy(strategy,2);//()
            }
            else if(tag.asText().equals("year")){
                item = package_one_strategy(strategy,3);//[ TO ]
            }
            else{
                item = package_one_strategy(strategy,1);//""
            }
            if(!isFirst){
                result_strategy_builder.append(" ").append(op.asText()).append(" ");
            }
            result_strategy_builder.append(item);
        }
        // 转换为新的payload
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.setAll(objectMapper.convertValue(payload,ObjectNode.class));
        objectNode.put("strategy",result_strategy_builder.toString());
        String requestBody = objectMapper.writeValueAsString(objectNode);
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
    public String package_one_strategy(JsonNode strategy,int type){// content的类型，1:string,2:int,3:year
        JsonNode op = strategy.get("op");
        JsonNode tag = strategy.get("tag");
        JsonNode content = strategy.get("content");
        JsonNode fuzzy = strategy.get("fuzzy");
        StringBuilder builder = new StringBuilder();
        if(type==2) {
            // (title:(1))
            builder.append("(").append(tag).append(":(").append(content.asInt());
            if(fuzzy.asInt()==1) builder.append("~");
            builder.append("))");
        }
        else if(type==1){
            // (keyword:"2")
            builder.append("(").append(tag).append(":").append(content.asText());
            if(fuzzy.asInt()==1) builder.append("~");
            builder.append(")");
        }
        else{ //type == 3, content:"2022,2023"
            // (year:[2020 TO 2023])
            String strs = content.asText();
            String[] years = strs.split(",");
            builder.append("(").append(tag).append(":[").append(years[0]).append(" TO ").append(years[1]).append("])");
        }
        return builder.toString();
    }
    public JsonNode test(JsonNode payload) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String requestBody = objectMapper.writeValueAsString(payload);
        if (Properties.isDebug) System.out.println("Request: " + requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getHeaders());
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
