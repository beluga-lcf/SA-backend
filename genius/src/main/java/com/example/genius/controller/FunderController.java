package com.example.genius.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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
        ReturnFunder returnFunder = new ReturnFunder();
        JSONArray resArray = JSON.parseArray(resJson);
        ArrayList<ReturnFunder> funders = new ArrayList<>();
        for(int i = 0; i < resArray.size(); i++){
            JSONObject j = resArray.getJSONObject(i);
            returnFunder.setFunderID(j.getString("funder"));
            returnFunder.setFunderName(j.getString("funder_display_name"));
            funders.add(returnFunder);
        }
        return getSuccessResponse(funders);
    }

}
