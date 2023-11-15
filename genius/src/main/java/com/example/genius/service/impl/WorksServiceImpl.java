package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.generated.entity.Works;
import com.example.generated.mapper.ConceptsMapper;
import com.example.generated.mapper.WorksMapper;
import com.example.genius.service.IWorkssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements IWorkssService {
    @Autowired
    ConceptsMapper conceptsMapper;
    @Override
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
}

