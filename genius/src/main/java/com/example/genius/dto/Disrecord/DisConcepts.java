package com.example.genius.dto.Disrecord;

import com.example.genius.dto.mywork.ConceptDis;
import lombok.Data;

import java.util.ArrayList;
@Data
public class DisConcepts {
    int value;
    String name;

    public DisConcepts(int i, String displayName) {
        value = i;
        name = displayName;
    }
}
