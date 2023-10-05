package com.example.genius.controller;

import com.example.genius.entity.Paper;
import com.example.genius.entity.User;
import com.example.genius.entity.VO.Response;
import com.example.genius.mapper.PaperMapper;
import com.example.genius.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 论文 前端控制器
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@RestController
@RequestMapping("/paper")
public class PaperController extends BaseController{
    @Autowired
    IPaperService paperService;
    @Autowired
    PaperMapper paperMapper;

    @RequestMapping("/getAllPapers")
    public Response getAllPapers(){
        List<Paper> list = paperMapper.selectList(null);
        System.out.println(list);
        return getSuccessResponse(list);
    }
}
