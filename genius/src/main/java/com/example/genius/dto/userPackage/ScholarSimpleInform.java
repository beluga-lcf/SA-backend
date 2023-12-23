package com.example.genius.dto.userPackage;

import com.example.genius.dto.mywork.MyWorkDis;

import java.util.ArrayList;

public class ScholarSimpleInform {
    // 姓名
    public String name;
    // 其他名字
    public String[] names;
    //    // 学术身份
//    public String identity;
    // 所属组织
    public String organization;
    // 研究领域
    public String[] interests;
    // 联系邮箱
    public String email;
    // 简介
    public String introduction;
    // 被引次数
    public String citationsNum;
    // 学术成果数量
    public String achievementsNum;
    // 有无被认领
    public boolean isClaimed;

    public ScholarSimpleInform(String name, String[] names, String organization, String[] interests, String citationsNum, String achievementsNum) {
        this.name = name;
        this.names = names;
        this.organization = organization;
        this.interests = interests;
        this.citationsNum = citationsNum;
        this.achievementsNum = achievementsNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setClaimed(boolean claimed) {
        isClaimed = claimed;
    }

    public String[] getNames() {
        return names;
    }
}
