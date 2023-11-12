package com.example.genius.dto;

import com.example.genius.dto.work.AuthorOfWork;
import com.example.genius.dto.work.ConceptOfWork;
import com.example.genius.dto.work.LocationOfWork;
import com.example.genius.dto.work.SourceOfWork;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

//论文主页的展示内容
@Data
public class WorkDisplay {
    public String workId; //论文id
    public String type; //论文类型
    public String title; //论文标题
    public List<AuthorOfWork> authors; //论文作者
    public List<ConceptOfWork> keywords; //论文关键词
    public String abstracts; //论文摘要
    public Integer citationCount; //论文被引用次数
    public SourceOfWork source; // 论文来源(期刊或数据库)
    public String publicationDate; //论文出版日期
    public LocationOfWork location;

    @Builder
    public WorkDisplay(String workId, String type, String title, List<AuthorOfWork> authors, List<ConceptOfWork> keywords, String abstracts, Integer citationCount, SourceOfWork source, String publicationDate, LocationOfWork location) {
        this.workId = workId;
        this.type = type;
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.abstracts = abstracts;
        this.citationCount = citationCount;
        this.source = source;
        this.publicationDate = publicationDate;
        this.location = location;
    }


}