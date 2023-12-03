package com.example.genius.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

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
    public String getWorksByUser(String openalexUserID){
        String url = "https://api.openalex.org/works?select=id,title,publication_date,concepts&per_page=200&filter=authorships.author.id:"+openalexUserID;
        return restTemplate.getForObject(url, String.class);

    }
    public String getFunderByWork(String workID){
        String url = "https://api.openalex.org/works/"+workID+"?select=grants";
        return restTemplate.getForObject(url, String.class);
    }

}











