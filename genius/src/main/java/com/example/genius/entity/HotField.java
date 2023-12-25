package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer.hotfield")
public class HotField {
    @TableField("name")
    private String name;
    @TableField("hotnum")
    private int hotNum;

    public HotField(String name, int hotNum) {
        this.name = name;
        this.hotNum = hotNum;
    }

    public HotField() {
<<<<<<< HEAD

=======
>>>>>>> c292418bb9f2d14060f4a9b69386c9823dc1a93b
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public void addHotNum() {
        this.hotNum++;
    }
}
