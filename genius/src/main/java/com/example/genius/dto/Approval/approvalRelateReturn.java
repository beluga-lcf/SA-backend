package com.example.genius.dto.Approval;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class approvalRelateReturn {
    private int id;
    private String openalexID;
    private int ischeck;
    private String approvePeople;
    private LocalDateTime time;
    private String text;
    public approvalRelateReturn(int id, String openalexID, String approvePeople, LocalDateTime time,int ischeck,String text) {
        this.id = id;
        this.openalexID = openalexID;
        this.ischeck = ischeck;
        this.approvePeople = approvePeople;
        this.time = time;
        this.text = text;
    }
}
