package com.example.genius.service;

import com.example.genius.dto.referenceWork.ReferenceWork;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public interface WorkService {
    public JsonNode getWorkHomePage(String workId) throws Exception;
}
