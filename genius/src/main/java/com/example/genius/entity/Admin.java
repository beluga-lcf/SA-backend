package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.admin")
public class Admin {
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
}
