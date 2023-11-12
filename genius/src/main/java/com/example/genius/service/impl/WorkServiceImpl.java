package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.generated.entity.*;
import com.example.generated.mapper.*;
import com.example.genius.dto.WorkDisplay;
import com.example.genius.dto.work.AuthorOfWork;
import com.example.genius.dto.work.ConceptOfWork;
import com.example.genius.dto.work.LocationOfWork;
import com.example.genius.dto.work.SourceOfWork;
import com.example.genius.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorksMapper worksMapper;
    @Autowired
    private WorksAuthorshipsMapper worksAuthorshipsMapper;
    @Autowired
    private AuthorsMapper authorsMapper;
    @Autowired
    private WorksConceptsMapper worksConceptsMapper;
    @Autowired
    private ConceptsMapper conceptsMapper;
    @Autowired
    private WorksBestOaLocationsMapper worksBestOaLocationsMapper;
    @Autowired
    private SourcesMapper sourcesMapper;
    @Autowired
    private WorksLocationsMapper worksLocationsMapper;
    @Autowired
    private WorksPrimaryLocationsMapper worksPrimaryLocationsMapper;


    @Override
    public WorkDisplay getWorkDisplayById(String workId) {
        Works workInDatabase = worksMapper.selectOne(new QueryWrapper<Works>().eq("id", workId));
        // 作者
        List<WorksAuthorships> worksAuthorshipsList = worksAuthorshipsMapper.selectList(new QueryWrapper<WorksAuthorships>().eq("work_id", workId));
        List<AuthorOfWork> authors = new ArrayList<>();
        for(WorksAuthorships worksAuthorships : worksAuthorshipsList) {
            Authors author= authorsMapper.selectOne(new QueryWrapper<Authors>().eq("id", worksAuthorships.getAuthorId()));
            AuthorOfWork authorOfWork = new AuthorOfWork(true,author.getId(), author.getDisplayName());
            authors.add(authorOfWork);
        }
        // 关键词
        List<WorksConcepts> worksConceptsList = worksConceptsMapper.selectList(new QueryWrapper<WorksConcepts>().eq("work_id", workId));
        List<ConceptOfWork> concepts = new ArrayList<>();
        for(WorksConcepts worksConcepts : worksConceptsList) {
            Concepts concept = conceptsMapper.selectOne(new QueryWrapper<Concepts>().eq("id", worksConcepts.getConceptId()));
            ConceptOfWork conceptOfWork = new ConceptOfWork(true,concept.getId(), concept.getDisplayName());
            concepts.add(conceptOfWork);
        }
        // 内容 & 来源
        // 优先级：best_oa_locations >= primary_locations >= locations
        SourceOfWork sourceOfWork = new SourceOfWork(false, null, null);
        LocationOfWork locationOfWork = new LocationOfWork(false, null);
        WorksBestOaLocations worksBestOaLocations = worksBestOaLocationsMapper.selectOne(new QueryWrapper<WorksBestOaLocations>().eq("work_id", workId));
        if(worksBestOaLocations != null) {
            Sources sources = sourcesMapper.selectOne(new QueryWrapper<Sources>().eq("id", worksBestOaLocations.getSourceId()));
            sourceOfWork = new SourceOfWork(true, sources.getId(), sources.getDisplayName());
            locationOfWork = new LocationOfWork(true, worksBestOaLocations.getPdfUrl());
        }
        else {
            WorksPrimaryLocations worksPrimaryLocations = worksPrimaryLocationsMapper.selectOne(new QueryWrapper<WorksPrimaryLocations>().eq("work_id", workId));
            if(worksPrimaryLocations != null) {
                Sources sources = sourcesMapper.selectOne(new QueryWrapper<Sources>().eq("id", worksPrimaryLocations.getSourceId()));
                sourceOfWork = new SourceOfWork(true, sources.getId(), sources.getDisplayName());
                locationOfWork = new LocationOfWork(true, worksPrimaryLocations.getPdfUrl());
            }
            else {
                WorksLocations worksLocations = worksLocationsMapper.selectOne(new QueryWrapper<WorksLocations>().eq("work_id", workId));
                if(worksLocations != null) {
                    Sources sources = sourcesMapper.selectOne(new QueryWrapper<Sources>().eq("id", worksLocations.getSourceId()));
                    sourceOfWork = new SourceOfWork(true, sources.getId(), sources.getDisplayName());
                    locationOfWork = new LocationOfWork(true, worksLocations.getPdfUrl());
                }
            }
        }
        WorkDisplay workDisplay = WorkDisplay.builder()
                .workId(workInDatabase.getId())
                .type(workInDatabase.getType())
                .title(workInDatabase.getTitle())
                .authors(authors)
                .keywords(concepts)
                .citationCount(workInDatabase.getCitedByCount())
                .publicationDate(workInDatabase.getPublicationDate())
                .source(sourceOfWork)
                .location(locationOfWork)
                .build();
        return workDisplay;
    }
}
