package com.example.genius.dto.mywork;

import lombok.Data;

@Data
public class ConceptDis {
    String name;// 研究方向
    public ConceptDis(String name){
        this.name = name;
    }
}
