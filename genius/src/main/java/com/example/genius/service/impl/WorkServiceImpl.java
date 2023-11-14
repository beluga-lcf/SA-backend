package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.generated.entity.*;
import com.example.generated.mapper.*;
import com.example.genius.dto.WorkDisplay;
import com.example.genius.dto.work.AuthorOfWork;
import com.example.genius.dto.work.LocationOfWork;
import com.example.genius.dto.work.SourceOfWork;
import com.example.genius.service.WorkService;
import com.example.genius.util.ApiUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorksMapper worksMapper;
    @Autowired
    private WorksAuthorshipsMapper worksAuthorshipsMapper;
    @Autowired
    private AuthorsMapper authorsMapper;
    @Autowired
    private WorksConceptsMapper worksConceptsMapper;
    @Autowired
    private ConceptsMapper conceptsMapper;
    @Autowired
    private WorksBestOaLocationsMapper worksBestOaLocationsMapper;
    @Autowired
    private SourcesMapper sourcesMapper;
    @Autowired
    private WorksLocationsMapper worksLocationsMapper;
    @Autowired
    private WorksPrimaryLocationsMapper worksPrimaryLocationsMapper;

    /*
    public String workId; //论文id
    public String type; //论文类型
    public String title; //论文标题
    public List<AuthorOfWork> authors; //论文作者
    public List<ConceptOfWork> keywords; //论文关键词
    public Integer citationCount; //论文被引用次数
    public SourceOfWork source; // 论文来源(期刊或数据库)
    public String publicationDate; //论文出版日期
    public LocationOfWork location;
     */
    @Override
    public WorkDisplay getWorkDisplayById(String workId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String url = "https://api.openalex.org/works/"+workId;
            String param = "";
            String result = ApiUtil.get(url,param);
            JsonNode jsonNode = objectMapper.readTree(result);
            // 作者
            JsonNode authorships = jsonNode.get("authorships");
            ArrayList<AuthorOfWork> authorList = new ArrayList<>();
            assert authorships.isArray();
            for(JsonNode node : authorships){
                node = node.get("author");
                AuthorOfWork authorOfWork = objectMapper.treeToValue(node, AuthorOfWork.class);
                authorList.add(authorOfWork);
            }
            // 关键词
            JsonNode keywords = jsonNode.get("keywords");
            ArrayList<String> keywordList = new ArrayList<>();
            assert keywords.isArray();
            for (JsonNode node : keywords){
                String keyword = node.get("keyword").asText();
                keywordList.add(keyword);
            }
            WorkDisplay workDisplay = WorkDisplay.builder()
                    .workId(jsonNode.get("id").asText())
                    .type(jsonNode.get("title").asText())
                    .title(jsonNode.get("title").asText())
                    .authors(authorList)
                    .keywords(keywordList)
                    .citedByCount(jsonNode.get("cited_by_count").asInt())
                    .source(objectMapper.treeToValue(jsonNode.get("source"), SourceOfWork.class))
                    .publicationDate(jsonNode.get("publication_date").asText())
                    .location(objectMapper.treeToValue(jsonNode.get("primary_location"), LocationOfWork.class))
                    .build();

            return workDisplay;
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
            log.error("there is an error in json-processing!");
            return null;
        }

    }

}
