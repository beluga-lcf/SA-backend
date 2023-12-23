package com.example.genius.dto.Report;

import lombok.Data;

@Data
public class ReportRate {
    int TotalRelate;
    int SucRelate;
    int TotalWorkReport;
    int SucWorkReport;
    int TotalCommentReport;
    int SucCommentReport;
    public ReportRate(int TotalRelate, int SucRelate, int TotalWorkReport, int SucWorkReport, int TotalCommentReport,int SucCommentReport){
        this.TotalRelate = TotalRelate;
        this.TotalWorkReport = TotalWorkReport;
        this.TotalCommentReport = TotalCommentReport;
        this.SucCommentReport = SucCommentReport;
        this.SucWorkReport = SucWorkReport;
        this.SucRelate = SucRelate;
    }
}
