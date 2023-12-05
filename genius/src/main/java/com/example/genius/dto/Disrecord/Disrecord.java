package com.example.genius.dto.Disrecord;

import lombok.Data;

import java.util.Date;

@Data
public class Disrecord {
    String content;//搜索内容
    Date time;//搜索时间
    int searchID;//搜索者ID
    public Disrecord(String content, Date time, int searchID){
        this.content = content;
        this.time = time;
        this.searchID = searchID;
    }
}
