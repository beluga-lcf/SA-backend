package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("customer.search_record")
public class Record {
    @TableId
    private int id;
    @TableField("record_text")
    private String recordText;
    @TableField("search_time")
    private Date SearchTime;
    @TableField("search_userid")
    private int SearchUserId;
}
