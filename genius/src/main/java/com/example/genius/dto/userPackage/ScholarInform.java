package com.example.genius.dto.userPackage;

import com.example.genius.dto.mywork.MyWorkDis;
import com.example.genius.entity.User;
import lombok.Data;

import java.util.ArrayList;
@Data
public class ScholarInform {
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
    // 学术成果
    public ArrayList<MyWorkDis> myWorkDisArrayList;
    // 有无被认领
    public boolean isClaimed;

    public ScholarInform(String name, String[] names, String organization, String[] interests, String citationsNum, String achievementsNum, ArrayList<MyWorkDis> myWorkDisArrayList) {
        this.name = name;
        this.names = names;
        this.organization = organization;
        this.interests = interests;
        this.citationsNum = citationsNum;
        this.achievementsNum = achievementsNum;
        this.myWorkDisArrayList = myWorkDisArrayList;
    }
    public ScholarInform(){

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

    public void setIsClaimed(boolean isClaimed) {
        this.isClaimed = isClaimed;
    }


}
