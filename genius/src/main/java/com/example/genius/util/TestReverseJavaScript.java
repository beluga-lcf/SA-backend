package com.example.genius.util;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import com.example.genius.util.ReverseSignatureUtil;

@Service
public class TestReverseJavaScript {

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public TestReverseJavaScript(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String sendRequest() {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setCacheControl(CacheControl.noCache());
        headers.setConnection("keep-alive");
        headers.set("Cookie", "XSRF-TOKEN=cda9f9b4-b1eb-4904-bdcd-2e0d788e27ec");
        headers.set("Origin", "https://pubscholar.cn");
        headers.set("Pragma", "no-cache");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 ... Safari/537.36");
        headers.set("X-XSRF-TOKEN", "cda9f9b4-b1eb-4904-bdcd-2e0d788e27ec");

        headers.set("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foocrg227gng6m6fbo95inwakpingbti");
        String timestamp=ReverseSignatureUtil.createTimestamp();
        stringBuilder.append(timestamp);
        String nonce=ReverseSignatureUtil.createNonce();
        stringBuilder.append(nonce);
        headers.set("nonce",nonce );
        headers.set("timestamp", timestamp);

        System.out.println(stringBuilder.toString());
        String s=ReverseSignatureUtil.createSignature(stringBuilder.toString());
        System.out.println(s);
        System.out.println("jdjdjdjd");
        System.out.println("jdjdjdjd");
        headers.set("signature", s);

        // 设置请求体
        String requestBody = "{\"topicId\":\"d42efacfe0e7a12cc86297581a705ef1\",\"keyword\":\"寇雯齐\",\"strategy\":\"{\"type\":0,\"strategy\":\"author\"}\",\"searchType\":\"article\"}";

        // 创建HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求
        ResponseEntity<String> response = restTemplate.postForEntity("https://pubscholar.cn/hky/open/resources/api/v1/articles", entity, String.class);

        // 返回响应体
        return response.getBody();
    }
    public static void main(String[] args) {
        System.out.println(sendRequest());
    }

}

