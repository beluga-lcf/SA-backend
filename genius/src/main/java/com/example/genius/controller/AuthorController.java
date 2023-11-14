package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
@Slf4j
public class AuthorController extends BaseController{
    private AuthorService authorService;
    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService = authorService;
    }

    @RequestMapping("/getAuthorIdByWorkId")
    public Response getAuthorIdByWorkId(String workId){
        try {
            return getSuccessResponse(authorService.getAuthorIdByWorkId(workId));
        }
        catch (Exception e){
            log.error(e.getMessage());
            return getSimpleError();
        }
    }

}
