package com.example.genius.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.Disrecord.Disrecord;
import com.example.genius.dto.mywork.ConceptDis;
import com.example.genius.entity.Record;
import com.example.genius.entity.Response;
import com.alibaba.fastjson2.JSONArray;
import com.example.genius.entity.User;
import com.example.genius.enums.ErrorType;
import com.example.genius.mapper.RecordMapper;
import com.example.genius.service.RecordService;
import com.example.genius.service.impl.OpenAlexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    private OpenAlexService openAlexService;
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public Response search( @RequestHeader(value = "Authorization") String token){
        int id = getIdByJwt(token);
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
                String result = openAlexService.getConceptByWorkID(record.getRecordId());
                ArrayList<ConceptDis> conceptDis = new ArrayList<>();
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("concepts");
                for(int i = 0; i<jsonArray.size(); i++){
                    conceptDis.add(new ConceptDis(jsonArray.getJSONObject(i).getString("display_name")));
                }
                disrecords.add(new Disrecord(record.getRecordId(),record.getRecordName(),record.getTime(),conceptDis));
            }
            return getSuccessResponse(disrecords);
        }

    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Response delete(int id,@RequestHeader(value = "Authorization") String token){
        int user = getIdByJwt(token);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        boolean b = recordService.remove(queryWrapper);
        if(b){
            return getSuccessResponse("删除成功");
        }else{
            return getErrorResponse(null, ErrorType.delete_failed);
        }
    }
    @RequestMapping(value = "/filter",method = RequestMethod.GET)
    public Response filter(String keyword,@RequestHeader(value = "Authorization") String token){
        int user = getIdByJwt(token);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("search_userid", user);
        List<Record> records = recordService.list(queryWrapper);
        ArrayList<Disrecord> disrecords = new ArrayList<>();
        for(Record record : records){
            if(record.getRecordName().contains(keyword)){
                String result = openAlexService.getConceptByWorkID(record.getRecordId());
                ArrayList<ConceptDis> conceptDis = new ArrayList<>();
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("concepts");
                for(int i = 0; i<jsonArray.size(); i++){
                    conceptDis.add(new ConceptDis(jsonArray.getJSONObject(i).getString("display_name")));
                }
                disrecords.add(new Disrecord(record.getRecordId(),record.getRecordName(),record.getTime(),conceptDis));
            }
        }
        return getSuccessResponse(disrecords);
    }
    public void AddRecord1(String workId,String title,int UserId){
        Record record = new Record();
        record.setRecordId(workId);
        record.setRecordName(title);
        record.setSearchUserId(UserId);
        recordService.save(record);
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Response test(@RequestHeader(value = "Authorization") String token){
        int user = getIdByJwt(token);
        if(user>=0){
            AddRecord1("https://openalex.org/W2741809807","dsfaf",user);
        }
        return getSuccessResponse("成功");
    }



    // TODO: 总热点领域
    // TODO: 总热点成果
    // TODO: 月热点成果
}
