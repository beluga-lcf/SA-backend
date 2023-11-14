package com.example.genius.controller;

import com.example.genius.dto.WorkDisplay;
import com.example.genius.entity.Response;
import com.example.genius.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/work")
public class WorkController extends BaseController{
    private WorkService workService;
    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }

    @RequestMapping("/displayWorkHomePage")
    public Response<WorkDisplay> displayWorkHomePage(String workId) {
        try {
            return getSuccessResponse(workService.getWorkDisplayById(workId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return getSimpleError();
        }
    }

}
