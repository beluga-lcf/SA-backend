package com.example.genius.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface InstitutionService {
    public JsonNode getInstitutionHomePage(String institutionName) throws Exception;
}
