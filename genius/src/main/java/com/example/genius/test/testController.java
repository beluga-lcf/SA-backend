package com.example.genius.test;

import com.example.genius.util.ApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test")
public class testController {


    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public void testSend() throws MalformedURLException {
        String url = "https://api.openalex.org/authors/random";
        String param = "";
        String result = ApiUtil.get(url, param);
        log.info("返回的结果：", result);
    }

    @RequestMapping(value = "/send2",method = RequestMethod.GET)
    public class HttpClientExample {


    }



}
