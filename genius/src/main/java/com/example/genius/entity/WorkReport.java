package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer.work_report")
@KeySequence(value = "customer.work_report_id_seq",dbType = DbType.POSTGRE_SQL)
public class WorkReport {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("reporter_name")
    private String reporterName;
    @TableField("reportee_work")
    private String reporteeWork;
    @TableField("reporter_id")
    private int reporterId;
    @TableField("ischeck")
    private int ischeck;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;
    @TableField("description")
    private String description;

    public WorkReport(){

    }
    public int getReporter_id(){
        return reporterId;
    }
}
