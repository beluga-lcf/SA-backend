package com.example.genius.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.Disrecord.DisConcepts;
import com.example.genius.dto.Disrecord.Disrecord;
import com.example.genius.dto.mywork.ConceptDis;
import com.example.genius.entity.*;
import com.alibaba.fastjson2.JSONArray;
import com.example.genius.entity.Record;
import com.example.genius.enums.ErrorType;
import com.example.genius.mapper.RecordMapper;
import com.example.genius.service.HotFieldService;
import com.example.genius.service.HotSpotService;
import com.example.genius.service.RecordService;
import com.example.genius.service.impl.OpenAlexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    private HotFieldService hotFieldService;
    @Autowired
    private HotSpotService hotSpotService;
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
                if(record.getRecordId()!=null){
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
                disrecords.add(new Disrecord(record.getId(),record.getRecordId(),record.getRecordName(),record.getTime(),conceptDis,conceptDis.size(),record.getPbscholarId()));
            }
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
            if(record.getRecordName().contains(keyword)&&record.getRecordId()!=null){
                String result = openAlexService.getConceptByWorkID(record.getRecordId());
                ArrayList<ConceptDis> conceptDis = new ArrayList<>();
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("concepts");
                for(int i = 0; i<jsonArray.size(); i++){
                    conceptDis.add(new ConceptDis(jsonArray.getJSONObject(i).getString("display_name")));
                }
                disrecords.add(new Disrecord(record.getId(),record.getRecordId(),record.getRecordName(),record.getTime(),conceptDis,conceptDis.size(),record.getPbscholarId()));
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
    @RequestMapping(value = "/getConcept",method = RequestMethod.GET)
    public Response getConcept(@RequestHeader(value = "Authorization") String token){
        int user = getIdByJwt(token);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("search_userid", user);
        List<Record> records = recordService.list(queryWrapper);
        HashMap<String,Integer> map = new HashMap<>();
        for(Record record : records){
            if(record.getRecordId()!=null){
                String result = openAlexService.getConceptByWorkID(record.getRecordId());
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("concepts");
                for(int i = 0; i<jsonArray.size(); i++){
                    if(!map.containsKey(jsonArray.getJSONObject(i).getString("display_name"))){
                        map.put(jsonArray.getJSONObject(i).getString("display_name"),1);
                    }else {
                        map.put(jsonArray.getJSONObject(i).getString("display_name"),map.get(jsonArray.getJSONObject(i).getString("display_name"))+1);
                    }
                }
            }
        }
        ArrayList<DisConcepts> retConcepts = new ArrayList<>();
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        int count = 0;
        for(Map.Entry<String, Integer> mapping:list){
            retConcepts.add(new DisConcepts(mapping.getValue(),mapping.getKey()));
            count++;
            if(count==5){
                break;
            }
        }
        return getSuccessResponse(retConcepts);
    }
    int min5(int a){
        return Math.min(a, 5);
    }

    // TODO: 总热点领域
    @RequestMapping(value = "/hotfield",method = RequestMethod.GET)
    public Response getHotField() {
        System.out.println("进入热点领域");
        List<HotField> hotFields = hotFieldService.getTop10();
        if (hotFields == null) {
            // 查询错误
            return getErrorResponse(null, ErrorType.hotField_fail);
        }
        else {
            // 归一化热值*1000+1000即[1000, 2000]
            int max = 0;
            int min = 10000000;
            for (HotField hotField : hotFields) {
                if (max < hotField.getHotNum()) {
                    max = hotField.getHotNum();
                }
                if (min > hotField.getHotNum()) {
                    min = hotField.getHotNum();
                }
            }
            int mid = (max - min);
            mid = (mid == 0 ? 1 : mid);
            for (HotField hotField : hotFields) {
                hotField.setHotNum((int) ((hotField.getHotNum() - min + 0.0) / mid * 500.0 + 500));
            }
            return getSuccessResponse(hotFields);
        }
    }

    // TODO: 总热点成果
    @RequestMapping(value = "/hotspots",method = RequestMethod.GET)
    public Response getHotSpots() {
        List<HotSpot> hotSpots = hotSpotService.getTop10();
        if (hotSpots == null) {
            // 查询错误
            return getErrorResponse(null, ErrorType.hotSpot_fail);
        }
        else {
            // 归一化热值*1000+1000即[1000, 2000]
            int max = 0;
            int min = 10000000;
            for (HotSpot hotSpot : hotSpots) {
                if (max < hotSpot.getHotNum()) {
                    max = hotSpot.getHotNum();
                }
                if (min > hotSpot.getHotNum()) {
                    min = hotSpot.getHotNum();
                }
            }
            int mid = (max - min);
            mid = (mid == 0 ? 1 : mid);
            for (HotSpot hotSpot : hotSpots) {
                hotSpot.setHotNum((int) ((hotSpot.getHotNum() - min + 0.0) / mid * 500.0 + 500));
            }
            return getSuccessResponse(hotSpots);
        }
    }
//    // TODO: 月热点成果
//    @RequestMapping(value = "/hotspotsM",method = RequestMethod.GET)
//    public Response getHotSpotsM() {
//        // 触发器绑定记录表（只绑定insert动作）
//        return null;
//    }
}
