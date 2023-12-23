package com.example.genius.dto.Disrecord;

import com.example.genius.dto.mywork.ConceptDis;
import lombok.Data;

import java.util.ArrayList;
@Data
public class DisConcepts {
    int count;
    String conceptName;

    public DisConcepts(int i, String displayName) {
        count = i;
        conceptName = displayName;
    }
}
