package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class VerifyCode {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String userId;
    private String code;
    private Integer type;
    private LocalDateTime createTime;
}