package com.example.genius.service.impl;

import com.example.genius.dto.workDisplay.AuthorOfWork;
import com.example.genius.service.AuthorService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorServiceImpl implements AuthorService {
    // 根据作品id获得作者们的id
    public ArrayList<String> getAuthorIdByWorkId(String workId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String id = StringUtil.removePrefix(workId);
        String url = "https://api.openalex.org/works/"+ id;
        String param = "";
        String result = ApiUtil.get(url,param);
        JsonNode jsonNode = objectMapper.readTree(result);
        // 作者
        JsonNode authorships = jsonNode.get("authorships");
        ArrayList<String> authors = new ArrayList<>();
        for(JsonNode node : authorships){
            node = node.get("author");
            String authorId = node.get("id").asText();
            authors.add(authorId);
        }
        return authors;
    }
}
