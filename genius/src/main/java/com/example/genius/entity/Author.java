package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("authors")
public class Author {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    private String orcid;
    // 展示名字
    private String displayName;
    // 其他可用的展示名字
    private String displayNameAlternatives;
    private Integer worksCount;
    // 被引用次数
    private Integer citedByCount;
    // 目前所在研究机构
    private String lastKnownInstitution;
    // 作品的 url
    private String worksApiUrl;
    private Timestamp updatedDate;
    // 研究领域
    private String xConcepts;

    @Data
    public static class Concepts {
        private double score;
        private int level;
        private String id;
        private String displayName;
        private String wikidata;
    }

}
