package com.example.genius.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.genius.dto.funder.DisFunder;
import com.example.genius.dto.funder.RelateWork;
import com.example.genius.dto.funder.ReturnFunder;
import com.example.genius.entity.Response;
import com.example.genius.service.impl.OpenAlexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@Slf4j
@RestController
@RequestMapping("/api/funder")
public class FunderController extends BaseController{
    @Autowired
    private OpenAlexService openAlexService;
    @GetMapping(value = "/displayFunder")
    public Response displayFunder(String workID){
        workID = workID.substring(20);
        System.out.println(workID);
        String jsons = openAlexService.getFunderByWork(workID);
        JSONObject jsonObject = JSONObject.parseObject(jsons);
        String resJson = jsonObject.getString("grants");
        JSONArray resArray = JSON.parseArray(resJson);
        ArrayList<ReturnFunder> funders = new ArrayList<>();
        for(int i = 0; i < resArray.size(); i++){
            JSONObject j = resArray.getJSONObject(i);
            funders.add(new ReturnFunder(j.getString("funder"),j.getString("funder_display_name")));
        }
        return getSuccessResponse(funders);
    }

    @GetMapping(value = "/funderMainPage")
    private Response funderMainPage(String funderID){
        String jsons = openAlexService.getFunderInfoByFunderID(funderID);
        JSONObject jsonObject = JSONObject.parseObject(jsons);
        DisFunder disFunder = new DisFunder();
        disFunder.setName(jsonObject.getString("display_name"));
        disFunder.setCountry(jsonObject.getString("country_code"));
        disFunder.setHomepage(jsonObject.getString("homepage_url"));
        disFunder.setDescription(jsonObject.getString("description"));
        jsons = openAlexService.getRelatedWork(funderID);
        jsonObject = JSONObject.parseObject(jsons);
        String resJson = jsonObject.getString("results");
        JSONArray resArray = JSON.parseArray(resJson);
        ArrayList<RelateWork> relateWorks = new ArrayList<>();
        for(int i = 0; i < resArray.size(); i++){
            JSONObject j = resArray.getJSONObject(i);
            relateWorks.add(new RelateWork(j.getString("id"),j.getString("display_name")));
        }
        disFunder.setRelateWorks(relateWorks);
        return getSuccessResponse(disFunder);
    }

}
