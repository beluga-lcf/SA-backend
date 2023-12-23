package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("customer.search_record")
public class Record {
    @TableField("record_name")
    private String recordName;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;
    @TableField("search_userid")
    private int SearchUserId;
    @TableField("record_id")
    private String RecordId;
}
