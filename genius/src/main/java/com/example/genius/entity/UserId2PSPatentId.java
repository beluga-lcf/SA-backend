package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.userId2PSPatentId")
public class UserId2PSPatentId {
    @TableField("userid")
    private int userId;
    @TableField("pspatentid")
    private String PSPatentId;
    @TableField("pname")
    private String PatentName;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPSPatentId(String PSPatentId) {
        this.PSPatentId = PSPatentId;
    }

    public void setPatentName(String patentName) {
        PatentName = patentName;
    }
}
