package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.userId2PSThesisId")
public class UserId2PSThesisId {
    @TableField("id")
    private int id;
    @TableField("userid")
    private int userId;
    @TableField("psthesisid")
    private String pSThesisId;
    @TableField("tname")
    private String pSThesisName;

    public UserId2PSThesisId() {}

    public UserId2PSThesisId(int id, int userId, String pSThesisId, String pSThesisName) {
        this.id = id;
        this.userId = userId;
        this.pSThesisId = pSThesisId;
        this.pSThesisName = pSThesisName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setpSThesisId(String pSThesisId) {
        this.pSThesisId = pSThesisId;
    }

    public void setpSThesisName(String pSThesisName) {
        this.pSThesisName = pSThesisName;
    }
}
