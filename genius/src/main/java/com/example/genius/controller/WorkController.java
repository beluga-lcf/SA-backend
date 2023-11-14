package com.example.genius.controller;

import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.entity.Response;
import com.example.genius.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/displayWorkHomePage")
    public Response displayWorkHomePage(String workId) {
        try {
            return getSuccessResponse(workService.getWorkDisplayById(workId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return getSimpleError();
        }
    }
    @GetMapping("/getReferenceById")
    public Response getReferenceById(String workId){
        try {
            return getSuccessResponse(workService.getReferenceByWorkId(workId));
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return getSimpleError();
        }
    }

}
