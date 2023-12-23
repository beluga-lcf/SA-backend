package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.hotspotm")
public class HotSpotM {
    @TableField("name")
    private String name;
    @TableField("hotnum")
    private int hotNum;
    @TableField("id")
    private String id;

    public void setName(String name) {
        this.name = name;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public void setId(String id) {
        this.id = id;
    }
}
