package com.example.genius.service.impl;

import com.example.genius.dto.searchResult.SearchItem;
import com.example.genius.dto.searchResult.SearchResult;
import com.example.genius.dto.workDisplay.InnerAuthor;
import com.example.genius.dto.workDisplay.InnerLocation;
import com.example.genius.dto.workDisplay.InnerSource;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.service.SearchService;
import com.example.genius.util.ApiUtil;
import com.example.genius.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public SearchResult FilterSearch(String conceptId, String type) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/works";
        String param = "filter=" + "concept.id:" + conceptId + ",type:" + type;
        String result = ApiUtil.get(url,param);
        JsonNode jsonNode = objectMapper.readTree(result);
        SearchResult searchResult = new SearchResult();
        JsonNode items = jsonNode.get("results");
        searchResult.count = jsonNode.get("meta").get("count").asInt();
        searchResult.items = new ArrayList<SearchItem>();
        for (JsonNode item : items){
            // id
            String id = item.get("id").asText();
            //type
            String typeName = item.get("type").asText();
            // 标题
            String title = item.get("title").asText();
            // 作者
            JsonNode authorships = item.get("authorships");
            ArrayList<InnerAuthor> authorList = new ArrayList<>();
            for(JsonNode node : authorships){
                node = node.get("author");
                InnerAuthor innerAuthor = new InnerAuthor();
                innerAuthor.setAuthorId(node.get("id").asText());
                innerAuthor.setAuthorName(node.get("display_name").asText());
                authorList.add(innerAuthor);
            }
            // 关键词
            JsonNode keywords = item.get("keywords");
            ArrayList<String> keywordList = new ArrayList<>();
            for (JsonNode node : keywords){
                String keyword = node.get("keyword").asText();
                keywordList.add(keyword);
            }
            // 来源
            JsonNode sourcNode = item.get("primary_location").get("source");
            InnerSource innerSource = new InnerSource();
            innerSource.setSoureId(sourcNode.get("id").asText());
            innerSource.setSourceName(sourcNode.get("display_name").asText());
            // location
            InnerLocation location = new InnerLocation();
            JsonNode locationNode = item;
            if( (locationNode = item.get("primary_location")).get("is_oa").asBoolean()){
                location.setAccessable(true);
                location.setPdf_url(locationNode.get("pdf_url").asText());
            } else if((locationNode = item.get("open_access")).get("is_oa").asBoolean()){
                location.setAccessable(true);
                location.setPdf_url(locationNode.get("oa_url").asText());
            } else {
                location.setAccessable(false);
                location.setPdf_url(null);
            }

            // 出版时间
            String publicationDate = item.get("publication_date").asText();
            // SearchItem
            SearchItem searchItem = SearchItem.builder()
                    .workId(id)
                    .title(title)
                    .type(typeName)
                    .authors(authorList)
                    .keywords(keywordList)
                    .source(innerSource)
                    .location(location)
                    .publicationDate(publicationDate)
                    .abstracts("temporary null")
                    .build();
            searchResult.items.add(searchItem);
        }
        return  searchResult;

    }
}
