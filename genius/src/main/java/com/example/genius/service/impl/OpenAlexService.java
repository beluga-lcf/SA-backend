package com.example.genius.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.example.genius.controller.BaseController;
import com.example.genius.dto.mywork.ConceptDis;
import com.example.genius.dto.mywork.MyWorkDis;
import com.example.genius.dto.userPackage.ScholarInform;
import com.example.genius.entity.User;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;
import org.json.JSONObject;

@Service
public class OpenAlexService {

    private final RestTemplate restTemplate;

    public OpenAlexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getType() {
        String url = "https://api.openalex.org/works?group_by=type";
        return restTemplate.getForObject(url, String.class);
    }
    public String getConcept() {
        String url = "https://api.openalex.org/concepts";
        return restTemplate.getForObject(url, String.class);
    }
    public String getYear() {
        String url = "https://api.openalex.org/works?group_by=publication_year";
        return restTemplate.getForObject(url,String.class);
    }
    public String getAuthor(){
        String url="https://api.openalex.org/authors";
        return restTemplate.getForObject(url,String.class);
    }
    public ScholarInform getAuthorSingle(String authorId) {
        String url="https://api.openalex.org/authors/" + authorId.substring(authorId.lastIndexOf("/") + 1);
        String jsonString = restTemplate.getForObject(url,String.class);
        JSONObject anAuthor = new JSONObject(jsonString);
        String name;
        String organization;
        String[] interests;
        String citationsNum;
        String achievementsNum;
        try {
            name = String.valueOf(anAuthor.get("display_name"));
        }
        catch (JSONException e) {
            name = null;
        }
        try {
            organization = (String) new JSONObject(anAuthor.get("last_known_institution")).get("display_name");
        }
        catch (JSONException e) {
            organization = null;
        }
        interests = BaseController.readJsonArray(anAuthor.getJSONArray("x_concepts"), "display_name");
        try {
            citationsNum = String.valueOf(anAuthor.get("cited_by_count"));
        }
        catch (JSONException e) {
            citationsNum = null;
        }
        try {
            achievementsNum = String.valueOf(anAuthor.get("cited_by_count"));
        }
        catch (JSONException e) {
            achievementsNum = null;
        }
        MyWorkDis myWorkDis = getWorks(authorId);
        return new ScholarInform(
                name, // name
//                // identity
                organization, // organization
                interests, // interests
                citationsNum, // citationsNum
                achievementsNum, // achievements
                myWorkDis
        );
    }
    public MyWorkDis getWorks(String openalexId) {//依据用户ID获取学术成果
        System.out.println(openalexId);

        String jsons = getWorksByUser(openalexId);
        com.alibaba.fastjson2.JSONObject json = com.alibaba.fastjson2.JSONObject.parseObject(jsons);
        String resJson = json.getString("results");
        JSONArray resArray = JSON.parseArray(resJson);
//        JSONArray jsonArray = new JSONArray();
        MyWorkDis myWorkDis = new MyWorkDis();
        for (int i = 0; i < resArray.size(); i++) {
            com.alibaba.fastjson2.JSONObject j = resArray.getJSONObject(i);
            myWorkDis.setId(j.getString("id"));
            myWorkDis.setTitle(j.getString("title"));
            myWorkDis.setDate(j.getString("publication_date"));
            JSONArray j2 = j.getJSONArray("concepts");
            for (int k = 0; k < j2.size(); k++) {
                myWorkDis.getConceptDis().add(new ConceptDis(j2.getJSONObject(k).getString("display_name")));
            }
        }
        return myWorkDis;
    }
    public String getWorksByUser(String openalexUserID){
        String url = "https://api.openalex.org/works?select=id,title,publication_date,concepts&per_page=200&filter=authorships.author.id:"+openalexUserID;
        return restTemplate.getForObject(url, String.class);

    }
    public String getFunderByWork(String workID){
        String url = "https://api.openalex.org/works/"+workID+"?select=grants";
        return restTemplate.getForObject(url, String.class);
    }
    public String getConceptByUser(String userID){
        String url = "https://api.openalex.org/authors/"+userID+"?select=x_concepts";
        return restTemplate.getForObject(url, String.class);
    }
    public String getWorkidByWorkname(String username){//通过作品名字获取作品ID
        String url = "https://api.openalex.org/works?filter=title.search:"+username;
        return restTemplate.getForObject(url, String.class);
    }
    public String getAuthorIDByWorkID(String workid){//通过作品ID获取作品作者的ID,直接传入以HTTP为头的ID
        workid = workid.substring(20);
        String url = "https://api.openalex.org/works/"+workid+"?select=authorships";
        return restTemplate.getForObject(url, String.class);
    }
    public String getFunderInfoByFunderID(String funderID){
        funderID = funderID.substring(21);
        System.out.println(funderID);
        String url = "https://api.openalex.org/funders/"+funderID+"?select=display_name,country_code,homepage_url,description";
        return restTemplate.getForObject(url, String.class);
    }
    public String getRelatedWork(String funderID){
        funderID = funderID.substring(21);
        String url = "https://api.openalex.org/works?filter=grants.funder:"+funderID+"&per-page=10&select=id,display_name";
        return restTemplate.getForObject(url, String.class);
    }

    

}












