package com.example.genius.dto.searchResult;

import com.example.genius.dto.workDisplay.InnerAuthor;
import com.example.genius.dto.workDisplay.InnerLocation;
import com.example.genius.dto.workDisplay.InnerSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
public class SearchItem {
    public String workId; //论文id
    public String type; //论文类型
    public String title; //论文标题
    public ArrayList<InnerAuthor> authors; //论文作者
    public InnerSource source; // 论文来源(期刊或数据库)
    public String publicationDate; //论文出版日期
    public ArrayList<String> keywords; //论文关键词
    public InnerLocation location; //所在地址
    public String abstracts;


}
