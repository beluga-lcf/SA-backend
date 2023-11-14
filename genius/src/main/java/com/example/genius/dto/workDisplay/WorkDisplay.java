package com.example.genius.dto.workDisplay;

import com.example.genius.dto.workDisplay.AuthorOfWork;
import com.example.genius.dto.workDisplay.LocationOfWork;
import com.example.genius.dto.workDisplay.SourceOfWork;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

//论文主页的展示内容
@Data
@Builder
@AllArgsConstructor
public class WorkDisplay {
    public String workId; //论文id
    public String type; //论文类型
    public String title; //论文标题
    public ArrayList<AuthorOfWork> authors; //论文作者
    public ArrayList<String> keywords; //论文关键词
    public Integer citedByCount; //论文被引用次数
    public SourceOfWork source; // 论文来源(期刊或数据库)
    public String publicationDate; //论文出版日期
    public LocationOfWork location;

}