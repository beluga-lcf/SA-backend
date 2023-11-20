package com.example.genius.service;

import com.example.genius.dto.referenceWork.ReferenceWork;
import com.example.genius.dto.workDisplay.WorkDisplay;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public interface WorkService {
    public WorkDisplay getWorkDisplayById(String workId) throws JsonProcessingException;
    public ArrayList<ReferenceWork> getReferenceByWorkId(String workId) throws Exception;
}
