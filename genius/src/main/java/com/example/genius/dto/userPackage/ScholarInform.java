package com.example.genius.dto.userPackage;

import com.example.genius.dto.mywork.MyWorkDis;
import com.example.genius.entity.User;

public class ScholarInform {
    // 姓名
    public String name;
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
    // 学术成果
    public MyWorkDis myWorkDis;

    public ScholarInform(String name, String organization, String[] interests, String citationsNum, String achievementsNum, MyWorkDis myWorkDis) {
        this.name = name;
        this.organization = organization;
        this.interests = interests;
        this.citationsNum = citationsNum;
        this.achievementsNum = achievementsNum;
        this.myWorkDis = myWorkDis;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMyWorkDis(MyWorkDis myWorkDis) {
        this.myWorkDis = myWorkDis;
    }
}
