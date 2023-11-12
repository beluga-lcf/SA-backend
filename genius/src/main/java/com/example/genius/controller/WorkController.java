package com.example.genius.controller;

import com.example.genius.dto.WorkDisplay;
import com.example.genius.entity.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
public class WorkController {

    @RequestMapping("/displayWorkHomePage")
    public Response<WorkDisplay> displayWorkHomePage(String workId) {
        // to be done
        return null;
    }

}
