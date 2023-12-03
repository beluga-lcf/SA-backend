package com.example.genius.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.Disrecord.Disrecord;
import com.example.genius.entity.Record;
import com.example.genius.entity.Response;
import com.alibaba.fastjson2.JSONArray;
import com.example.genius.entity.User;
import com.example.genius.mapper.RecordMapper;
import com.example.genius.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/record")
public class RecordController extends BaseController{
    @Autowired
    private HttpSession session;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordMapper recordMapper;
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Response search(int id){
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("search_userid", id);
        List<Record> records = recordService.list(queryWrapper);
        ArrayList<Disrecord> disrecords = new ArrayList<>();
        if(records.isEmpty()){
            return getSuccessResponse(disrecords);
        }else{
            for(Record record : records){
//                JSONObject j = new JSONObject();
//                j.put("content",record.getRecordText());
//                j.put("time",record.getSearchTime());
//                j.put("id",record.getId());
//                array.add(j);
                disrecords.add(new Disrecord(record.getRecordText(),record.getSearchTime(),record.getId()));
            }
            return getSuccessResponse(disrecords);
        }

    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Response delete(int id){
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        boolean b = recordService.remove(queryWrapper);
        if(b){
            return getSuccessResponse("删除成功");
        }else{
            return getSuccessResponse("删除失败");
        }
    }
}
