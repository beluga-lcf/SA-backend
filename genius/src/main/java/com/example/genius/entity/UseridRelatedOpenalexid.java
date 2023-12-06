package com.example.genius.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@TableName("customer.userid_related_openalexid")
public class UseridRelatedOpenalexid {
    @TableId
    private Integer UserId;
    @TableField("openalexid")
    private String Openalexid;
    @TableField("ischeck")
    private int ischeck;
}
