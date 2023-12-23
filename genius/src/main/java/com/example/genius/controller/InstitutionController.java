package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.service.InstitutionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getInstitutionPatents")
    public Response<Object> getInstitutionsForMainPage(){
        try {
            return getSuccessResponse(institutionService.getInstitutionsForMainPage());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
