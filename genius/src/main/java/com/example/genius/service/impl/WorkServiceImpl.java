package com.example.genius.service.impl;

import com.example.generated.mapper.*;
import com.example.genius.dto.referenceWork.ReferenceWork;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.dto.workDisplay.InnerAuthor;
import com.example.genius.dto.workDisplay.InnerLocation;
import com.example.genius.dto.workDisplay.InnerSource;
import com.example.genius.service.WorkService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public WorkDisplay getWorkDisplayById(String workId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String id = StringUtil.removePrefix(workId);
        String url = "https://api.openalex.org/works/"+ id;
        String param = "";
        String result = ApiUtil.get(url,param);
        JsonNode jsonNode = objectMapper.readTree(result);
        // 作者
        JsonNode authorships = jsonNode.get("authorships");
        ArrayList<InnerAuthor> authorList = new ArrayList<>();
        assert authorships.isArray();
        for(JsonNode node : authorships){
            node = node.get("author");
            InnerAuthor innerAuthor = new InnerAuthor();
            innerAuthor.setAuthorId(node.get("id").asText());
            innerAuthor.setAuthorName(node.get("display_name").asText());
            authorList.add(innerAuthor);
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
        InnerSource innerSource = new InnerSource();
        innerSource.setSoureId(sourcNode.get("id").asText());
        innerSource.setSourceName(sourcNode.get("display_name").asText());
        // location
        JsonNode locationNode = jsonNode.get("primary_location");
        InnerLocation location = new InnerLocation();
        location.setAccessable(locationNode.get("is_oa").asBoolean());
        location.setPdf_url(locationNode.get("pdf_url").asText());
        // abstract
        String DOI = jsonNode.get("doi").asText();
        String abstractContent = ApiUtil.getAbstract(DOI);
        // build
        WorkDisplay workDisplay = WorkDisplay.builder()
                .workId(jsonNode.get("id").asText())
                .type(jsonNode.get("title").asText())
                .title(jsonNode.get("title").asText())
                .authors(authorList)
                .keywords(keywordList)
                .citedByCount(jsonNode.get("cited_by_count").asInt())
                .source(innerSource)
                .publicationDate(jsonNode.get("publication_date").asText())
                .location(location)
                .abstractContent(abstractContent)
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
        String DOI = jsonNode.get("doi").asText();
        String cross_url = "https://api.crossref.org/works/" + DOI;
        String cross_result = ApiUtil.get(cross_url, "");
        JsonNode crossAllNode = objectMapper.readTree(cross_result);
        JsonNode CrossNode = crossAllNode.get("message").get("reference");
        JsonNode OpenNode = jsonNode.get("referenced_works");
        JsonNode RefNode = CrossNode == null ? OpenNode : CrossNode;
        // 为了性能，只展示6个
        ArrayList<ReferenceWork> referenceWorks = new ArrayList<>();
        int count = 0;
        for(JsonNode node : RefNode) {
            if(count == 6) break;
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
            count++;
        }
        return referenceWorks;
    }
}
