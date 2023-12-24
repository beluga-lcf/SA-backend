package com.example.genius.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface InstitutionService {
    public JsonNode getInstitutionHomePage(String institutionName) throws Exception;
    public JsonNode getInstitutionHomePage2(String institutionName) throws Exception;
    public JsonNode getInstitutionWorks(String institutionId) throws Exception;

    public JsonNode getInstitutionsForMainPage() throws Exception;
    public String getInstitionIdFromName(String institutionName) throws Exception;

}
