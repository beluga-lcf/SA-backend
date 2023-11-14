package com.example.genius.controller;

import com.example.generated.entity.Authors;
import com.example.generated.entity.Concepts;
import com.example.generated.entity.Works;
import com.example.generated.service.IAuthorsService;
import com.example.generated.service.IConceptsService;
import com.example.generated.service.IWorksService;
import com.example.genius.service.IWorkssService;
import com.example.genius.service.impl.WorksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/works")
public class WorksController {
    @Autowired
    private IWorkssService worksService;
    @Autowired
    private IWorksService workService;

    @Autowired
    private IConceptsService conceptService;

    @Autowired
    private IAuthorsService authorService;


    @GetMapping("/type-counts")
    public Map<String, Object> getTypeCounts() {
        List<Map<String, Object>> typeCounts = worksService.getTypeCounts();
        return Map.of("group_by", typeCounts);
    }

    @GetMapping("/filter-options")
    public ResponseEntity<List<Map<String, Object>>> getFilterOptions(@RequestParam String type) {
        List<Map<String, Object>> response = new ArrayList<>();

        // 获取学科数据
        List<Map<String, Object>> subjects = getSubjectsByType(type);
        response.add(constructFilterOption(1, "学科", subjects));

        // 获取发表年度数据
        response.add(constructFilterOption(2, "发表年度", getPublicationYears()));

        // 获取作者数据
        List<Map<String, Object>> authors = getAuthorsByType(type);
        response.add(constructFilterOption(3, "作者", authors));

        return ResponseEntity.ok().body(response);
    }

    private List<Map<String, Object>> getSubjectsByType(String type) {
        QueryWrapper<Works> workQuery = new QueryWrapper<>();
        workQuery.eq("type", type);
        List<Works> works = workService.list(workQuery);

        QueryWrapper<Concepts> conceptQuery = new QueryWrapper<>();
        conceptQuery.in("id", works.stream().map(Works::getId).collect(Collectors.toList()));
        return conceptService.list(conceptQuery).stream()
                .map(concept -> {
                    Map<String, Object> conceptMap = new HashMap<>();
                    conceptMap.put("id", concept.getId());
                    conceptMap.put("value", concept.getDisplayName());
                    return conceptMap;
                })
                .collect(Collectors.toList());

    }

    private List<Map<String, Object>> getAuthorsByType(String type) {
        QueryWrapper<Works> workQuery = new QueryWrapper<>();
        workQuery.eq("type", type);
        List<Works> works = workService.list(workQuery);

        QueryWrapper<Authors> authorQuery = new QueryWrapper<>();
        authorQuery.in("id", works.stream().map(Works::getId).collect(Collectors.toList()));
        return authorService.list(authorQuery).stream()
                .map(author -> {
                    Map<String, Object> authorMap = new HashMap<>();
                    authorMap.put("id", author.getId());
                    authorMap.put("value", author.getDisplayName());
                    return authorMap;
                })
                .collect(Collectors.toList());

    }

    private Map<String, Object> constructFilterOption(int id, String title, List<Map<String, Object>> select) {
        Map<String, Object> filterOption = new HashMap<>();
        filterOption.put("id", id);
        filterOption.put("title", title);
        filterOption.put("select", select);
        return filterOption;
    }

    private List<Map<String, Object>> getPublicationYears() {
        List<Map<String, Object>> years = new ArrayList<>();
        for (int year = 2023; year >= 2018; year--) {
            Map<String, Object> yearOption = new HashMap<>();
            yearOption.put("id", 2023 - year + 1);
            yearOption.put("value", year + "年");
            years.add(yearOption);
        }
        return years;
    }
}
