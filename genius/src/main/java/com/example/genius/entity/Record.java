package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("customer.search_record")
@KeySequence(value = "search_record_id_seq",dbType = DbType.POSTGRE_SQL)
public class Record {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("record_name")
    private String recordName;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;
    @TableField("search_userid")
    private int SearchUserId;
    @TableField("record_id")
    private String RecordId;
    @TableField("pbscholar_id")
    private String pbscholarId;
    public Record(){

    }
}
