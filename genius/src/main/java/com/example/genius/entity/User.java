package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    private String nickName;
    private String email;
    private String password;
    private Integer sex;
    private String personDescription;
    private LocalDateTime joinTime;
}