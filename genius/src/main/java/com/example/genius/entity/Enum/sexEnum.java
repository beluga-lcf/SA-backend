package com.example.genius.entity.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum sexEnum {

    Unknown(0, "未知"),
    Male(1, "男"),
    Female(2, "女");

    @EnumValue //标定存入数据库的值
    private Integer sex;

    private String sexName;

    sexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}