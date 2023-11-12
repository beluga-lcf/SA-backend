package com.example.generated.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.example.generated.entity.Concepts;
import com.example.generated.entity.Works;
import com.example.generated.mapper.ConceptsMapper;
import com.example.generated.mapper.WorksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> {
    @Autowired
    ConceptsMapper conceptsMapper;

    public List<Map<String, Object>> getTypeCounts() {
        QueryWrapper<Works> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("type", "COUNT(*) as count").groupBy("type");
        return this.listMaps(queryWrapper).stream()
                .map(map -> Map.of(
                        "key", map.get("type"),
                        "key_display_name", map.get("type"),
                        "count", map.get("count")))
                .collect(Collectors.toList());
    }
    public Object getWorkDetails(String type) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 根据type检索不同的数据
        if ("学科".equals(type)) {
            result.add(getConcepts());
        } else if ("发表年度".equals(type)) {
            result.add(getPublicationYears());
        } else if ("作者".equals(type)) {
            result.add(getAuthors());
        }

        return result;
    }

    //TODO: bugs
    private Map<String, Object> getConcepts() {
        List<Map<String, Object>> conceptsList = conceptsMapper.selectList(new QueryWrapper<Concepts>()
                        .select("id", "display_name"))
                .stream()
                .map(concept -> Map.of(
                        "id", concept.getId(),
                        "value", concept.getDisplayName()))
                .collect(Collectors.toList());
        return Map.of("id", 1, "title", "学科", "select", conceptsList);
    }


    private Map<String, Object> getPublicationYears() {
        List<Map<String, Object>> years = IntStream.rangeClosed(2018, Year.now().getValue())
                .mapToObj(year -> Map.of("id", year, "value", year + "年"))
                .collect(Collectors.toList());
        return Map.of("id", 2, "title", "发表年度", "select", years);
    }


    private Map<String, Object> getAuthors() {
        // 从authors表获取数据
        return Map.of("id", 3, "title", "作者", "select", /* 调用Authors表的查询逻辑 */);
    }
}

