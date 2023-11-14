package com.example.genius.controller;

import com.example.genius.dto.workDisplay.WorkDisplay;
import com.example.genius.entity.Response;
import com.example.genius.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Response<Object> displayWorkHomePage(String workId) {
        try {
            return getSuccessResponse(workService.getWorkDisplayById(workId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return getSimpleError();
        }
    }
    @GetMapping("/getReferenceById}")
    public Response<Object> getReferenceById(String workId){
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
