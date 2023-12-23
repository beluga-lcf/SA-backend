package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alibaba.fastjson2.JSONObject;
import com.example.generated.mapper.*;
import com.example.genius.config.Properties;
import com.example.genius.dto.referenceWork.ReferenceWork;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.dto.workDisplay.InnerAuthor;
import com.example.genius.dto.workDisplay.InnerLocation;
import com.example.genius.dto.workDisplay.InnerSource;
import com.example.genius.entity.HotField;
import com.example.genius.entity.HotSpot;
import com.example.genius.mapper.HotFieldMapper;
import com.example.genius.mapper.HotSpotMapper;
import com.example.genius.service.AccessService;
import com.example.genius.service.WorkService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.ReverseAESUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.UnknownFormatConversionException;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private AccessService accessService;
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
    @Autowired
    private OpenAlexService openAlexService;
    @Autowired
    private HotFieldMapper hotFieldMapper;
    @Autowired
    private HotSpotMapper hotSpotMapper;

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
    public JsonNode getWorkHomePage(String workId) throws Exception {
        String baseUrl = ApiUtil.getScholarUrl2("article")+"/"+ ReverseAESUtil.encrypt(workId);
        String param = "uid=664ab44bcbb54371b6fbc154241c9072&type=article";
        String path = baseUrl + "?" + param;
        if(Properties.isDebug) System.out.println(path);
        if(true){
            // 使用RestTemplate发起POST请求
            String apiUrl = path;
            HttpEntity<String> entity = new HttpEntity<>("haha", ApiUtil.getHeaders());
            ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            // 解析返回值
            System.out.println("responseBody: "+responseBody);
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
            //openalexid
            String workEntity = openAlexService.getWorkidByWorkname(titleNode.asText().trim());
            String openaelexId = objectMapper.readTree(workEntity).get("results").get(0).get("id").asText();
            newNode.put("openalexId", openaelexId);
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
            ArrayNode institionArray = objectMapper.convertValue(institutionNode, ArrayNode.class);
            ArrayNode newInstitutionArray = objectMapper.createArrayNode();
            for(JsonNode institution: institionArray){
                ObjectNode newInstitution = objectMapper.createObjectNode();
                String institutionName = institution.asText();
                boolean isAccess = accessService.isAccess_Institution(institutionName);
                newInstitution.put("name", institutionName);
                newInstitution.put("isAccess", isAccess);
                newInstitutionArray.add(newInstitution);
            }
            newNode.set("institutions", newInstitutionArray);
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
            ArrayNode linksArray = objectMapper.convertValue(linksNode, ArrayNode.class);
            JsonNode newLinks = null;
            for (JsonNode linkNode : linksArray) {
                if(linkNode.get("is_open_access").asBoolean()){
                    newLinks = linksNode;
                    break;
                }
            }
            newNode.set("links", newLinks);
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
            HttpEntity<String> entity2 = new HttpEntity<>(requestBody, ApiUtil.getHeaders());
            ResponseEntity<String> response2 = new RestTemplate().exchange(recommendPath, HttpMethod.POST, entity2, String.class);
            // 处理响应
            if (response.getStatusCode().is2xxSuccessful()) {
                responseBody = response2.getBody();
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
                    String author = "";
                    if(recommend.get("author").get(0)!=null) author = recommend.get("author").get(0).asText().trim();
                    StringJoiner infoJoiner = new StringJoiner(" ");
                    if(!author.isEmpty()) infoJoiner.add(author);
                    infoJoiner.add(year).add(volume);
                    String infomation = infoJoiner.toString();
                    newRecommend.put("info",infomation);
                    newRecommends.add(newRecommend);
                    count++;
                }
                newNode.set("recommendations", newRecommends);
            } else {
                System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            }
            // TODO:维护 hot表
            // hotField
            insertHotField(keywordsNode);
            // hotSpot
            insertHotSpot(idNode, titleNode);
            return newNode;
        }

        return null;
    }

    @Transactional
    private void insertHotField(JsonNode keywords) {
        // TODO: 拆解keywords
        ArrayList<String> keys = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array = objectMapper.convertValue(keywords, ArrayNode.class);
        for(JsonNode child:array){
            keys.add(child.asText());
        }
        // 检索，有则update，无则insert
        for (String key : keys) {
            QueryWrapper<HotField> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", key);
            HotField checker = hotFieldMapper.selectOne(queryWrapper);
            if (checker == null) {
                checker = new HotField(key, 1);
                hotFieldMapper.insert(checker);
            }
            else {
                checker.addHotNum();
                hotFieldMapper.update(checker, queryWrapper);
            }
        }

    }

    @Transactional
    private void insertHotSpot(JsonNode idNodeJ, JsonNode titleNodeJ) {
        if (idNodeJ == null || titleNodeJ == null) {
            return;
        }
        String idNode = idNodeJ.asText();
        String titleNode = titleNodeJ.asText();
        // 查询，有则update，无则insert
        QueryWrapper<HotSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", idNode);
        HotSpot checker = hotSpotMapper.selectOne(queryWrapper);
        if (checker == null) {
            // 未记录，增加一条1
            checker = new HotSpot(titleNode, 1, idNode);
            hotSpotMapper.insert(checker);
        }
        else {
            // 已记录，hotnum加1
            checker.addHotNum();
            hotSpotMapper.update(checker, queryWrapper);
        }
    }
}
