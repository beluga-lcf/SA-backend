package com.example.genius.controller;


import com.example.genius.util.ReverseAESUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.genius.util.ApiUtil.getHeaders;

@RestController
@RequestMapping("/api/patents")
public class PatentController {

    @GetMapping("/{patentid}")
    public ResponseEntity<String> getPatent(@PathVariable String patentid) {
        // 设置请求的URL
        String url = "https://pubscholar.cn/hky/open/resources/api/v2/patent/" + ReverseAESUtil.encrypt(patentid) + "?uid=664ab44bcbb54371b6fbc154241c9072&type=";

        // 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 创建HttpEntity
        HttpEntity<String> entity = new HttpEntity<>("body", getHeaders());

        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 返回响应
        return response;
    }

    @PostMapping("/patent-recommendations")
    public ResponseEntity<String> getPatentRecommendations(@RequestBody PatentRequest patentRequest) {
        String url = "https://pubscholar.cn/hky/open/resources/api/v1/patent/recommendations";

        // 使用RestTemplate发送POST请求
        RestTemplate restTemplate = new RestTemplate();
        String fixedUserId = "48bb3d184dd22adc05c0941194a2e718";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", patentRequest.title);
        requestBody.put("userId", fixedUserId);
        // 创建HttpEntity
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, getHeaders());

        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        // 返回响应
        return response;
    }

}
class PatentRequest {
    public String title;
    //userId是固定的, 不传参
//    private String userId;

}

