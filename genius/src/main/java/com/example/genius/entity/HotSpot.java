package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.hotspot")
public class HotSpot {
    @TableField("name")
    private String name;
    @TableField("hotnum")
    private int hotNum;
    @TableField("id")
    private String id;
    @TableField("author")
    private String author;

    public HotSpot(String name, int hotNum, String id, String author) {
        this.name = name;
        this.hotNum = hotNum;
        this.id = id;
        this.author = author;
    }

    public HotSpot() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addHotNum() {
        this.hotNum++;
    }

    public String getAuthor() {
        return author;
    }
}
