package com.example.genius.controller;

import com.example.generated.entity.Authors;
import com.example.generated.entity.Concepts;
import com.example.generated.entity.Works;
import com.example.generated.service.IAuthorsService;
import com.example.generated.service.IConceptsService;
import com.example.generated.service.IWorksService;
import com.example.genius.dto.searchResult.SearchRequest;
import com.example.genius.dto.searchResult.SearchResult;
import com.example.genius.entity.Response;
import com.example.genius.service.IWorkssService;
import com.example.genius.service.SearchService;
import com.example.genius.service.WorkService;
import com.example.genius.service.impl.OpenAlexService;
import com.example.genius.service.impl.WorksServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Random;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
@Slf4j
@RestController
@RequestMapping("/api/works")
public class WorksController extends BaseController{
    @Autowired
    private OpenAlexService openAlexService;
    private WorkService workService;
    private SearchService searchService;
    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }
    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/type-counts")
    public String getTypeCounts() {
        return openAlexService.getType();
    }
    @GetMapping("/filter-options")
    public String getFilterOptions(@RequestParam String type) {
        String response=openAlexService.getConcept();

        // 获取学科数据
        String subjects = JsonParser.buildSubjectsJson(response);
//        System.out.println(subjects);
        response=openAlexService.getYear();
        // 获取发表年度数据
        String years = JsonParser.buildYearsJson(response);

        // 获取作者数据
        response=openAlexService.getAuthor();
        String authors = JsonParser.buildAuthorJson(response);
        JSONArray array=new JSONArray();
        array.put(new JSONObject(subjects));
        array.put(new JSONObject(years));
        array.put(new JSONObject(authors));
        return array.toString();
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

    /*
    type: article, book-chapter, dissertation, book, dataset, paratext,
          other, reference-entry, report, peer-review, standard, editorial, erratum, grant, letter
     */
    @GetMapping("/filter")
    public Response<Object> filterSearch(String type, String concept){
        try {
            SearchResult result = searchService.FilterSearch(concept, type);
            return getSuccessResponse(result);
        }
        catch (Exception e) {
            log.error("here is an error!");
            return getSimpleError();
        }
    }

    @GetMapping("/search")
    public Response<Object> search(@RequestBody SearchRequest searchRequest){//可选，语言
        try {
            SearchResult result = searchService.ComplexSearch(searchRequest);
            return getSuccessResponse(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("here is an error!");
            return getSimpleError();
        }
    }

}

class JsonParser {

    public static String buildSubjectsJson(String jsonString) {
        // 将字符串解析为一个JSONObject
        JSONObject obj = new JSONObject(jsonString);
        // 获取results的JSONArray
        JSONArray results = obj.getJSONArray("results");

        // 创建新的JSON对象
        JSONObject newJson = new JSONObject();
        newJson.put("id", 1);
        newJson.put("title", "学科");

        // 创建select的JSONArray
        JSONArray select = new JSONArray();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String displayName = result.getString("display_name");
            int worksCount = result.getInt("works_count");

            // 创建新的select项
            JSONObject selectItem = new JSONObject();
            selectItem.put("id", i + 1);
            selectItem.put("value", displayName);
//            Random random = new Random();
//            //little trick (
//            int randomInt = random.nextInt(10000000);
//            boolean randomBoolean = random.nextBoolean();
//            if (randomBoolean)worksCount+=randomInt;
//            else worksCount-=randomInt;
//            if(worksCount<0)worksCount=-worksCount;
            selectItem.put("num", worksCount);

            // 将新的select项添加到select的JSONArray中
            select.put(selectItem);
        }

        // 将select的JSONArray添加到新的JSON对象中
        newJson.put("select", select);

        // 返回新的JSON字符串
        return newJson.toString();
    }

    public static String buildYearsJson(String jsonString) {
        // 将字符串解析为一个JSONObject
        JSONObject obj = new JSONObject(jsonString);
        // 获取results的JSONArray
        JSONArray results = obj.getJSONArray("group_by");

        // 创建新的JSON对象
        JSONObject newJson = new JSONObject();
        newJson.put("id", 2);
        newJson.put("title", "年份");

        // 创建select的JSONArray
        JSONArray select = new JSONArray();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String displayName = result.getString("key_display_name");
            int worksCount = result.getInt("count");

            // 创建新的select项
            JSONObject selectItem = new JSONObject();
            selectItem.put("id", i + 1);
            selectItem.put("value", displayName);
//            Random random = new Random();
//            //little trick (
//            int randomInt = random.nextInt(1000000);
//            boolean randomBoolean = random.nextBoolean();
//            if (randomBoolean)worksCount+=randomInt;
//            else worksCount-=randomInt;
//            if(worksCount<0)worksCount=-worksCount;
            selectItem.put("num", worksCount);

            // 将新的select项添加到select的JSONArray中
            select.put(selectItem);
        }

        // 将select的JSONArray添加到新的JSON对象中
        newJson.put("select", select);

        // 返回新的JSON字符串
        return newJson.toString();
    }

    public static String buildAuthorJson(String jsonString) {
        // 将字符串解析为一个JSONObject
        JSONObject obj = new JSONObject(jsonString);
        // 获取results的JSONArray
        JSONArray results = obj.getJSONArray("results");

        // 创建新的JSON对象
        JSONObject newJson = new JSONObject();
        newJson.put("id", 3);
        newJson.put("title", "作者");

        // 创建select的JSONArray
        JSONArray select = new JSONArray();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String displayName = result.getString("display_name");
            int worksCount = result.getInt("works_count");

            // 创建新的select项
            JSONObject selectItem = new JSONObject();
            selectItem.put("id", i + 1);
            selectItem.put("value", displayName);
//            Random random = new Random();
            //little trick (
//            int randomInt = random.nextInt(1000);
//            boolean randomBoolean = random.nextBoolean();
//            if (randomBoolean)worksCount+=randomInt;
//            else worksCount-=randomInt;
//            if(worksCount<0)worksCount=-worksCount;
            selectItem.put("num", worksCount);

            // 将新的select项添加到select的JSONArray中
            select.put(selectItem);
        }

        // 将select的JSONArray添加到新的JSON对象中
        newJson.put("select", select);

        // 返回新的JSON字符串
        return newJson.toString();
    }
}

