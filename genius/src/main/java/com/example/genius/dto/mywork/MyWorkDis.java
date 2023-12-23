package com.example.genius.dto.mywork;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
public class MyWorkDis {
    String id; //作品ID
    String title; //作品标题
    String date; //发表时间
    ArrayList<ConceptDis> conceptDis = new ArrayList<ConceptDis>();// 作品方向

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ConceptDis c : conceptDis) {
            sb.append(c.name);
        }
        return sb.toString();
    }
}
