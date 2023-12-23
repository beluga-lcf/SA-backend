package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer.user")
public class User {
    @TableId(value = "userid", type = IdType.AUTO)
    private Long userId;
    private String nickName;
    @TableField("email")
    private String email;
    private String password;
    private Integer sex;
    private String personDescription;
    private LocalDateTime joinTime;
}