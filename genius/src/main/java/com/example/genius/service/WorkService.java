package com.example.genius.service;

import com.example.genius.dto.WorkDisplay;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WorkService {
    WorkDisplay getWorkDisplayById(String workId) throws JsonProcessingException;
}
