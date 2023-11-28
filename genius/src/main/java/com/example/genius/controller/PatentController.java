package com.example.genius.controller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/patents")
public class PatentController {
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.setCacheControl(CacheControl.noCache());
        headers.setConnection("keep-alive");
        headers.set("Cookie", "XSRF-TOKEN=be214c6e-81ab-4185-aa9b-65a102798470");
        headers.setPragma("no-cache");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        headers.set("X-XSRF-TOKEN", "be214c6e-81ab-4185-aa9b-65a102798470");
        headers.set("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        return  headers;
    }
    @GetMapping("/{patentid}")
    public ResponseEntity<String> getPatent(@PathVariable String patentid) {
        // 设置请求的URL
        String url = "https://pubscholar.cn/hky/open/resources/api/v1/patent/" + patentid + "?uid=48bb3d184dd22adc05c0941194a2e718&type=";

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

