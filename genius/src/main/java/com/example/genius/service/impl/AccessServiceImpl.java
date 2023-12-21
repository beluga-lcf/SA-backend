package com.example.genius.service.impl;

import com.example.genius.service.AccessService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Service
public class AccessServiceImpl implements AccessService {

    public boolean isAccess_Institution(String institutionName) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/institutions";
        String params = "search=" + URLEncoder.encode(institutionName,"UTF-8");
        String result = ApiUtil.get(url, params);
        JsonNode resultJson = objectMapper.readTree(result);
        if(resultJson.get("meta").get("count").asInt() == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
