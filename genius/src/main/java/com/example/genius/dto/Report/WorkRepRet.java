package com.example.genius.dto.Report;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkRepRet {
    private int ID;
    private String reporterName;
    private int reporterId;
    private String reporteeWork;
    private String description;
    private LocalDateTime time;
    private int ischeck;
    public WorkRepRet(int ID,String reporterName, int reporterId, String reporteeWork, String description,LocalDateTime time, int ischeck) {
        this.ID = ID;
        this.reporterName = reporterName;
        this.reporterId = reporterId;
        this.reporteeWork = reporteeWork;
        this.description = description;
        this.time = time;
        this.ischeck = ischeck;
    }
}
