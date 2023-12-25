package com.example.genius.dto.userPackage;

import com.example.genius.dto.mywork.MyWorkDis;
import lombok.Data;

import java.util.ArrayList;
@Data
public class NewScholarInform {
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
    private int isRelate;
}
