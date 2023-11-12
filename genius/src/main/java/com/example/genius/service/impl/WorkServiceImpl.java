package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.generated.entity.Authors;
import com.example.generated.entity.Works;
import com.example.generated.entity.WorksAuthorships;
import com.example.generated.mapper.AuthorsMapper;
import com.example.generated.mapper.WorksAuthorshipsMapper;
import com.example.generated.mapper.WorksMapper;
import com.example.genius.dto.AuthorOfWork;
import com.example.genius.dto.WorkDisplay;
import com.example.genius.service.WorkService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorksMapper worksMapper;
    @Autowired
    private WorksAuthorshipsMapper worksAuthorshipsMapper;
    @Autowired
    private AuthorsMapper authorsMapper;


    @Override
    public WorkDisplay getWorkDisplayById(String workId) {
        Works workInDatabase = worksMapper.selectById(workId);
        // 作者
        QueryWrapper<WorksAuthorships> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_id", workId);
        List<WorksAuthorships> worksAuthorshipsList = worksAuthorshipsMapper.selectList(queryWrapper);
        List<AuthorOfWork> authors = new ArrayList<>();
        for(WorksAuthorships worksAuthorships : worksAuthorshipsList) {
            Authors author= authorsMapper.selectById(worksAuthorships.getAuthorId());
            AuthorOfWork authorOfWork = new AuthorOfWork();
            authorOfWork.setAuthorId(worksAuthorships.getAuthorId());
            authorOfWork.setAuthorName(author.getDisplayName());
            authors.add(authorOfWork);
        }
        // 关键词

        WorkDisplay workDisplay = WorkDisplay.builder()
                .workId(workInDatabase.getId())
                .type(workInDatabase.getType())
                .title(workInDatabase.getTitle())
                .authors(authors)
//                .keywords(workInDatabase.getKeywords())
//                .abstracts(workInDatabase.getAbstracts())
//                .citationCount(workInDatabase.getCitationCount())
//                .publisher(workInDatabase.)
                .publicationDate(workInDatabase.getPublicationDate())
                .build();
        return workDisplay;
    }
}
