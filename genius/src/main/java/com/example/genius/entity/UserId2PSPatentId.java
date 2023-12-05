package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.userId2PSPatentId")
public class UserId2PSPatentId {
    @TableField("id")
    private int id;
    @TableField("userid")
    private int userId;
    @TableField("pspatentid")
    private String PSPatentId;
    @TableField("pname")
    private String PatentName;

    public UserId2PSPatentId() {
    }

    public UserId2PSPatentId(int userId, String PSPatentId, String patentName) {
        this.userId = userId;
        this.PSPatentId = PSPatentId;
        PatentName = patentName;
    }

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
