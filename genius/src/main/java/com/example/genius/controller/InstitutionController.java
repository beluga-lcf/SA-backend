package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.service.InstitutionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/institution")
public class InstitutionController extends BaseController{
    private InstitutionService institutionService;
    @Autowired
    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/getInstitutionHomePage")
    public Response<Object> getInstitutionHomePage(String institutionId){

        try {
            return getSuccessResponse(institutionService.getInstitutionHomePage2(institutionId));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInstitutionWorks")
    public Response<Object> getInstitutionWorks(String institutionId){
        try {
            return getSuccessResponse(institutionService.getInstitutionWorks(institutionId));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInstitutionsForMainPage")
    public JsonNode getInstitutionsForMainPage(){
        try {
            return institutionService.getInstitutionsForMainPage2();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInstitionIdFromName")
    public String getInstitionIdFromName(String institutionName){
        try {
            return institutionService.getInstitionIdFromName(institutionName);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addSInstitutions")
    public Response<Object> addSInstitutions(@RequestBody JsonNode requestBody) throws Exception {
        institutionService.addSInstitions(requestBody);
        return getSimpleSuccess();
    }


}
