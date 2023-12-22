package com.example.genius.dto.userPackage;

import com.example.genius.dto.mywork.MyWorkDis;
import com.example.genius.entity.User;

import java.util.ArrayList;

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
    public ArrayList<MyWorkDis> myWorkDisArrayList;

    public ScholarInform(String name, String organization, String[] interests, String citationsNum, String achievementsNum, ArrayList<MyWorkDis> myWorkDisArrayList) {
        this.name = name;
        this.organization = organization;
        this.interests = interests;
        this.citationsNum = citationsNum;
        this.achievementsNum = achievementsNum;
        this.myWorkDisArrayList = myWorkDisArrayList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMyWorkDis(ArrayList<MyWorkDis> myWorkDisArrayList) {
        this.myWorkDisArrayList = myWorkDisArrayList;
    }
}
