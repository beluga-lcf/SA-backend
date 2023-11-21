package com.example.genius.service.impl;

import com.example.genius.dto.searchResult.*;
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
import springfox.documentation.spring.web.json.Json;

import javax.xml.transform.Source;
import java.util.ArrayList;

import static com.example.genius.dto.searchResult.LanguageEnum.getLanguageCode;
import static com.example.genius.dto.searchResult.SubjectEnum.getConceptId;

@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public SearchResult FilterSearch(String conceptName, String type) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.openalex.org/works";
        String conceptId = getConceptId(conceptName);
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
            // 摘要
            String DOI = item.get("doi").asText();
            String abstractContent = ApiUtil.getAbstract(DOI);
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
                    .abstracts(abstractContent)
                    .build();
            searchResult.items.add(searchItem);
        }
        return  searchResult;

    }

    @Override
    public SearchResult ComplexSearch(SearchRequest searchRequest) throws Exception{
        String filterParam = "";
        String sortParam = "";
        String searchParam = "";
        String pageParam = "&page=1&per-page=10";
        boolean isFirstFilter = true;
        if(searchRequest.types != null){
            filterParam += "type:" + StringUtil.Or(searchRequest.types);
            isFirstFilter = false;
        }
        if(searchRequest.subjects != null){
            ArrayList<String> subjectIds = new ArrayList<>();
            for(String subject: searchRequest.subjects){
                for(SubjectEnum subjectEnum : SubjectEnum.values()){
                    if(subjectEnum.conceptName.equals(subject)){
                        subjectIds.add(subjectEnum.conceptId);
                    }
                }
            }
            // 判断是否是第一个筛选参数，如果是，不加逗号；如果不是，加逗号
            filterParam += isFirstFilter ? "concepts.id:" : ",concepts.id:";
            filterParam += StringUtil.Or(subjectIds);
            isFirstFilter = false;
        }
        if(searchRequest.publicationYears != null){
            // 判断是否是第一个筛选参数，如果是，不加逗号；如果不是，加逗号
            filterParam += isFirstFilter ? "publication_year:" : ",publication_year:";
            ArrayList<String> years = new ArrayList<>();
            for(Integer year: searchRequest.publicationYears){
                years.add(String.valueOf(year));
            }
            filterParam += StringUtil.Or(years);
            isFirstFilter = false;
        }
        if (searchRequest.languages != null){
            // 判断是否是第一个筛选参数，如果是，不加逗号；如果不是，加逗号
            filterParam += isFirstFilter ? "language:" : ",language:";
            ArrayList<String> languageCodes = new ArrayList<>();
            for(String languageName: searchRequest.languages){
                languageCodes.add(getLanguageCode(languageName));
            }
            filterParam += StringUtil.Or(languageCodes);
            isFirstFilter = false;
        }
        if(searchRequest.order_field != null){
            sortParam += searchRequest.order_field;
            if(searchRequest.order_direction != null){
                sortParam += ":"+searchRequest.order_direction;
            } else {
                sortParam += ":desc";
            }
        }
        if(searchRequest.searchContent != null){
            searchParam += searchRequest.searchContent;
        }
        if(searchRequest.pageNo != 0){
            pageParam = "&page=" + searchRequest.pageNo + "&per-page=" + searchRequest.pageSize;
        }
        String url = "https://api.openalex.org/works";
        // String param = filterParam + "&" + sortParam + "&" + searchParam + "&" + pageParam;
        String param = isFirstFilter ? "" : "filter=" + filterParam;
        param += sortParam.equals("") ? "" : "&sort=" + sortParam;
        param += searchParam.equals("") ? "" : "&search=" + searchParam;
        param += pageParam;
        // param: filter=concept:math|physics,publication_year:2023 $ search=AI & sort=
        System.out.println(url+"?"+param);
        String result = ApiUtil.get(url,param);
        ObjectMapper objectMapper = new ObjectMapper();
        SearchResult searchResult = new SearchResult();
        JsonNode jsonNode = objectMapper.readTree(result);
        JsonNode items = jsonNode.get("results");
        searchResult.count = jsonNode.get("meta").get("count").asInt();
        searchResult.items = new ArrayList<SearchItem>();
        int count = 0;
        int limitCount = 10;
        for (JsonNode item : items){
            if(count == limitCount){
                break;
            }
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
            // 来源 & location : primary_location > best_oa_location > locations
            InnerLocation location = new InnerLocation();
            InnerSource source = new InnerSource();
            JsonNode locationNode = item;
            JsonNode sourcNode = item;
            JsonNode primaryNode = item.get("primary_location");
            JsonNode bestNode = item.get("best_oa_location");
            JsonNode locationsNode = item.get("locations");
            if(primaryNode!=null && primaryNode.get("is_oa")!=null && primaryNode.get("is_oa").asBoolean()){
                locationNode = primaryNode;
                sourcNode = locationNode.get("source");
                source.setSoureId(sourcNode.get("id").asText());
                source.setSourceName(sourcNode.get("display_name").asText());
                location.setAccessable(true);
                location.setPdf_url(locationNode.get("pdf_url").asText());
            } else if(bestNode!=null && bestNode.get("is_oa")!=null && bestNode.get("is_oa").asBoolean()){
                locationNode = bestNode;
                sourcNode = locationNode.get("source");
                source.setSoureId(sourcNode.get("id").asText());
                source.setSourceName(sourcNode.get("display_name").asText());
                location.setAccessable(true);
                location.setPdf_url(locationNode.get("pdf_url").asText());
            } else if(locationsNode!=null && locationsNode.get(0).get("is_oa")!=null && locationsNode.get(0).get("is_oa").asBoolean()){
                locationNode = locationsNode.get(0);
                sourcNode = locationNode.get("source");
                source.setSoureId(sourcNode.get("id").asText());
                source.setSourceName(sourcNode.get("display_name").asText());
                location.setAccessable(true);
                location.setPdf_url(locationNode.get("pdf_url").asText());
            } else {
                source.setSoureId("null");
                source.setSourceName("null");
                location.setAccessable(false);
                location.setPdf_url("null");
            }
            // 出版时间
            String publicationDate = item.get("publication_date").asText();
            // 摘要
            String DOI = item.get("doi").asText();
            System.out.println(DOI);
            String abstractContent = ApiUtil.getAbstract(DOI);
            System.out.println(DOI);
            // SearchItem
            SearchItem searchItem = SearchItem.builder()
                    .workId(id)
                    .title(title)
                    .type(typeName)
                    .authors(authorList)
                    .keywords(keywordList)
                    .source(source)
                    .location(location)
                    .publicationDate(publicationDate)
                    .abstracts(abstractContent)
                    .build();
            searchResult.items.add(searchItem);
            count++;
        }
        return searchResult;
    }
}
