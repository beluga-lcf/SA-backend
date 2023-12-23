package com.example.genius.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.userPackage.ScholarSimpleInform;
import com.example.genius.entity.Response;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.service.AuthorService;
import com.example.genius.service.UseridRelatedOpenalexService;
import com.example.genius.service.impl.OpenAlexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/author")
@Slf4j
public class AuthorController extends BaseController{
    private AuthorService authorService;
    private OpenAlexService openAlexService;
    private UseridRelatedOpenalexService uroService;
    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService = authorService;
    }
    @Autowired
    public void setOpenAlexService(OpenAlexService openAlexService){
        this.openAlexService = openAlexService;
    }
    @Autowired
    public void setUroService(UseridRelatedOpenalexService uroService){
        this.uroService = uroService;
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

    @GetMapping("/getAuthorId")
    public String getAuthorId(String workName, String authorName){
        // 复用
        List<ScholarSimpleInform> authors = new ArrayList<>();
        List<String> scholarIds = openAlexService.getAuthoriIdByWorkname(workName);
        for (String scholarId : scholarIds) {
            // 获得一个author
            ScholarSimpleInform scholarSInform = openAlexService.getAuthorSimpleSingle(scholarId);
            if (scholarSInform == null) {
                // 未知错误导致未查到
            }
            else {
                // 如果name可接受，加入users
                // 获取全部名字,包括name和names
                String[] names = scholarSInform.getNames();
                boolean acceptable = false;
                for (String name : names) {
                    authorName = authorName.replace(",","");
                    System.out.println(authorName);
                    String[] name_1 = name.split(" ");
                    String[] name_2 = authorName.split(" ");
                    int matches = 0;
                    for (String str1 : name_1) {
                        for (String str2 : name_2) {
                            if (str1.contains(str2) || str2.contains(str1)) {
                                matches++;
                            }
                        }
                    }
                    acceptable = (matches - 2.0 / 3 * name_1.length >= 0) && (matches - 2.0 / 3 * name_2.length >= 0);
                }
                if (acceptable) {
                    return scholarId;
                }
            }
        }
        return null;
    }
}
