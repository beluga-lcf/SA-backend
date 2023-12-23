package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer.work_report")
public class WorkReport {
    private String reporter_name;
    private String reportee_work;
    private int reporter_id;
    private int ischeck;
    private LocalDateTime time;
    private String description;
}
