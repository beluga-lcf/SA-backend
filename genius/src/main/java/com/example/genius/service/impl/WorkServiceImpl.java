package com.example.genius.service.impl;

import com.example.generated.mapper.*;
import com.example.genius.config.Properties;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.StringJoiner;

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
    public LocationOfWork location; //论文下载地址
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
        innerSource.setSourceId(sourcNode.get("id").asText());
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
                .type(jsonNode.get("type").asText())
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
    public ArrayList<ReferenceWork> getReferenceByWorkId(String workId){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String entity_id = StringUtil.removePrefix(workId);
            String open_url = "https://api.openalex.org/works/" + entity_id;
            String result = ApiUtil.get(open_url, "");
            JsonNode jsonNode = objectMapper.readTree(result);
            String DOI = jsonNode.get("doi").asText();
            JsonNode RefNode = jsonNode.get("referenced_works");
            // 为了性能，只展示6个z
            ArrayList<ReferenceWork> referenceWorks = new ArrayList<>();
            int count = 0;
            for (JsonNode node : RefNode) {
                if (count == 6) break;
                ReferenceWork referenceWork = new ReferenceWork();
                String singleId = node.asText();
                System.out.println(singleId);
                String pureId = StringUtil.removePrefix(singleId);
                JsonNode workSelf = objectMapper.readTree(ApiUtil.get("https://api.openalex.org/works/" + pureId, ""));
                referenceWork.setWorkId(workSelf.get("id").asText());
                referenceWork.workName = (workSelf.get("display_name")!=null) ? workSelf.get("display_name").asText() : "null";
                referenceWork.publicationYear = (workSelf.get("publication_year")!=null) ? workSelf.get("publication_year").asText() : "null";
                if (workSelf.get("primary_location") != null && workSelf.get("primary_location").get("source")!=null) {
                    referenceWork.setSourceName(workSelf.get("primary_location").get("source").get("display_name").asText());
                } else if (workSelf.get("best_oa_location") != null && workSelf.get("best_oa_location").get("source")!=null) {
                    referenceWork.setSourceName(workSelf.get("best_oa_location").get("source").get("display_name").asText());
                } else{
                    referenceWork.setSourceName("null");
                }
                referenceWorks.add(referenceWork);
                count++;
            }
            return referenceWorks;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonNode getWorkHomePage(String workId) throws Exception {
        String baseUrl = ApiUtil.getScholarUrl("article")+"/"+workId;
        String param = "uid=3b9547dd87904c44923d675711729962&type=article";
        String path = baseUrl + "?" + param;
        if(true){
            String responseBody = ApiUtil.getConnection(path);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode originalNode = objectMapper.readValue(responseBody, ObjectNode.class);
            ObjectNode newNode = objectMapper.createObjectNode();
            //id
            JsonNode idNode = originalNode.get("id");
            newNode.set("workId", idNode);
            //type,article_type
            JsonNode cnTypeNode = originalNode.get("type");
            JsonNode articleTypeNode = originalNode.get("article_type");
            newNode.set("type", cnTypeNode);
            newNode.set("article_type", articleTypeNode);
            //title
            JsonNode titleNode = originalNode.get("title");
            newNode.set("title", titleNode);
            //authors
            JsonNode authorsNode = originalNode.get("author");
            newNode.set("authors", authorsNode);
            //keywords
            JsonNode keywordsNode = originalNode.get("keywords");
            newNode.set("keywords", keywordsNode);
            //date
            JsonNode dateNode = originalNode.get("date");
            newNode.set("publicationDate", dateNode);
            //institution
            JsonNode institutionNode = originalNode.get("institution");
            newNode.set("institutions", institutionNode);
            //relation_index
            JsonNode relationIndexNode = originalNode.get("relation_index");
            ArrayNode newRelationIndexNode = objectMapper.createArrayNode();
                // 遍历relation_index数组
            ArrayNode relationIndexArray = objectMapper.convertValue(relationIndexNode, ArrayNode.class);
            for (JsonNode relationNode : relationIndexArray) {
                ArrayNode relationArray = objectMapper.convertValue(relationNode, ArrayNode.class);
                // 遍历数组中的元素
                for (int i = 0; i < relationArray.size(); i++) {
                    // 将元素转换为整数，加1，然后设置回去
                    int currentValue = Integer.parseInt(relationArray.get(i).asText());
                    relationArray.set(i, currentValue + 1);
                }
                newRelationIndexNode.add(relationArray);
            }
            newNode.set("relation_index", newRelationIndexNode);
            //abstracts
            JsonNode abstractsNode = originalNode.get("abstracts");
            newNode.set("abstract", abstractsNode);
            //abstracts_abbreviation
            JsonNode abstractsAbbreviationNode = originalNode.get("abstracts_abbreviation");
            newNode.set("abstract_abbreviation", abstractsAbbreviationNode);
            //links
            JsonNode linksNode = originalNode.get("links");
            newNode.set("links", linksNode);
            //ref_articles
            if(originalNode.get("ref_articles") != null){
                JsonNode refArticles = originalNode.get("ref_articles");
                ArrayNode refArticlesNew = objectMapper.createArrayNode();
                int count = 0;
                for(JsonNode refArticleNode : refArticles){
                    if(count == 6) break;
                    ObjectNode newRefArticleNode = objectMapper.createObjectNode();
                    JsonNode title = refArticleNode.get("title");
                    String year = refArticleNode.get("year").asText().trim();
                    String volume = refArticleNode.get("volume").asText().trim();
                    String author = refArticleNode.get("author").get(0).asText().trim();
                    StringJoiner infoJoiner = new StringJoiner(" ");
                    infoJoiner.add(author).add(year).add(volume);
                    String infomation = infoJoiner.toString();
                    newRefArticleNode.set("title", title);
                    newRefArticleNode.put("info",infomation);
                    refArticlesNew.add(newRefArticleNode);
                    count++;
                }
                newNode.set("ref_articles", refArticlesNew);
            }
            //recommend_articles
            String recommendPath = "https://pubscholar.cn/hky/open/resources/api/v1/article/recommendations";
            ObjectNode payload = objectMapper.createObjectNode();
            payload.put("query", titleNode.asText());
            payload.put("userId", "3b9547dd87904c44923d675711729962");
            String requestBody = objectMapper.writeValueAsString(payload);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
            ResponseEntity<String> response = new RestTemplate().exchange(recommendPath, HttpMethod.POST, entity, String.class);
            // 处理响应
            if (response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
                ArrayNode recommends = objectMapper.readValue(responseBody, ArrayNode.class);
                ArrayNode newRecommends = objectMapper.createArrayNode();
                int count = 0;
                for (JsonNode recommend : recommends) {
                    if(count == 6) break;
                    ObjectNode newRecommend = objectMapper.createObjectNode();
                    newRecommend.set("title", recommend.get("title"));
                    newRecommend.set("id", recommend.get("id"));
                    String year = recommend.get("year").asText().trim();
                    String volume = recommend.get("volume").asText().trim();
                    String author = recommend.get("author").get(0).asText().trim();
                    StringJoiner infoJoiner = new StringJoiner(" ");
                    infoJoiner.add(author).add(year).add(volume);
                    String infomation = infoJoiner.toString();
                    newRecommend.put("info",infomation);
                    newRecommends.add(newRecommend);
                    count++;
                }
                newNode.set("recommendations", newRecommends);
            } else {
                System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            }
            return newNode;
        }
        return null;
    }
}
