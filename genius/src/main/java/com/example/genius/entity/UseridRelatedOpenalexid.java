package com.example.genius.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("customer.userid_related_openalexid")
public class UseridRelatedOpenalexid {
    @TableId
    private Integer UserId;
    @TableField("openalexid")
    private String Openalexid;
    @TableField("ischeck")
    private int ischeck;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;
}
