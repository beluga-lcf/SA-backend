package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
@Slf4j
public class AuthorController extends BaseController{
    private AuthorService authorService;
    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/getAuthorIdByWorkId")
    public Response<Object> getAuthorIdByWorkId(String workId){
        try {
            return getSuccessResponse(authorService.getAuthorIdByWorkId(workId));
        }
        catch (Exception e){
            log.error(e.getMessage());
            return getSimpleError();
        }
    }

    @GetMapping("/getAuthorHomePage")
    public Response<Object> getAuthorHomePage(String authorId){
        try {
            // to be done
            return getSimpleSuccess();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return getSimpleError();
        }
    }

}
