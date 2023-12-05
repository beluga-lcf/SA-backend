package com.example.genius.service.impl;

import com.example.genius.controller.BaseController;
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
        return new ScholarInform(
                name, // name
//                // identity
                organization, // organization
                interests, // interests
                citationsNum, // citationsNum
                achievementsNum // achievements
        );
    }
    public String getWorksByUser(String openalexUserID){
        String url = "https://api.openalex.org/works?select=id,title,publication_date,concepts&per_page=200&filter=authorships.author.id:"+openalexUserID;
        return restTemplate.getForObject(url, String.class);

    }
    public String getFunderByWork(String workID){
        String url = "https://api.openalex.org/works/"+workID+"?select=grants";
        return restTemplate.getForObject(url, String.class);
    }

}











