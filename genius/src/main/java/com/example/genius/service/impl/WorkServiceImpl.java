package com.example.genius.service.impl;

import com.example.generated.mapper.*;
import com.example.genius.dto.referenceWork.ReferenceWork;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.dto.workDisplay.AuthorOfWork;
import com.example.genius.dto.workDisplay.LocationOfWork;
import com.example.genius.dto.workDisplay.SourceOfWork;
import com.example.genius.service.WorkService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.sql.Ref;
import java.util.ArrayList;

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
    public WorkDisplay getWorkDisplayById(String workId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String id = StringUtil.removePrefix(workId);
        String url = "https://api.openalex.org/works/"+ id;
        String param = "";
        String result = ApiUtil.get(url,param);
        JsonNode jsonNode = objectMapper.readTree(result);
        // 作者
        JsonNode authorships = jsonNode.get("authorships");
        ArrayList<AuthorOfWork> authorList = new ArrayList<>();
        assert authorships.isArray();
        for(JsonNode node : authorships){
            node = node.get("author");
            AuthorOfWork authorOfWork = new AuthorOfWork();
            authorOfWork.setAuthorId(node.get("id").asText());
            authorOfWork.setAuthorName(node.get("display_name").asText());
            authorList.add(authorOfWork);
        }
        // 关键词
        JsonNode keywords = jsonNode.get("keywords");
        ArrayList<String> keywordList = new ArrayList<>();
        for (JsonNode node : keywords){
            String keyword = node.get("keyword").asText();
            keywordList.add(keyword);
        }
        // 来源
        JsonNode sourcNode = jsonNode.get("primary_location").get("source");
        SourceOfWork sourceOfWork = new SourceOfWork();
        sourceOfWork.setSoureId(sourcNode.get("id").asText());
        sourceOfWork.setSourceName(sourcNode.get("display_name").asText());
        // location
        JsonNode locationNode = jsonNode.get("primary_location");
        LocationOfWork location = new LocationOfWork();
        location.setAccessable(locationNode.get("is_oa").asBoolean());
        location.setPdf_url(locationNode.get("pdf_url").asText());
        // build
        WorkDisplay workDisplay = WorkDisplay.builder()
                .workId(jsonNode.get("id").asText())
                .type(jsonNode.get("title").asText())
                .title(jsonNode.get("title").asText())
                .authors(authorList)
                .keywords(keywordList)
                .citedByCount(jsonNode.get("cited_by_count").asInt())
                .source(sourceOfWork)
                .publicationDate(jsonNode.get("publication_date").asText())
                .location(location)
                .build();

        return workDisplay;
    }


    @Override
    public ArrayList<ReferenceWork> getReferenceByWorkId(String workId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String id = StringUtil.removePrefix(workId);
        String url = "https://api.openalex.org/works/"+ id;
        String param = "";
        String result = ApiUtil.get(url,param);
        JsonNode jsonNode = objectMapper.readTree(result);
        jsonNode = jsonNode.get("referenced_works");
        ArrayList<ReferenceWork> referenceWorks = new ArrayList<>();
        for(JsonNode node : jsonNode){
            ReferenceWork referenceWork = new ReferenceWork();
            String singleId = node.asText();
            String pureId = StringUtil.removePrefix(singleId);
            JsonNode workSelf = objectMapper.readTree(ApiUtil.get("https://api.openalex.org/works/"+ pureId, ""));
            referenceWork.setWorkId(workSelf.get("id").asText());
            referenceWork.setWorkName(workSelf.get("display_name").asText());
            referenceWork.setPublicationYear(workSelf.get("publication_year").asText());
            JsonNode sourceNode = workSelf.get("primary_location").get("source");
            JsonNode locationNode;
            if((locationNode = sourceNode.get("best_oa_location")) != null){
                referenceWork.setSourceName(locationNode.get("display_name").asText());
            } else if((locationNode = sourceNode.get("primary_location"))!=null){
                referenceWork.setSourceName(locationNode.get("display_name").asText());
            } else {
                referenceWork.setSourceName("null");
            }
            referenceWorks.add(referenceWork);
        }
        return referenceWorks;
    }
}
