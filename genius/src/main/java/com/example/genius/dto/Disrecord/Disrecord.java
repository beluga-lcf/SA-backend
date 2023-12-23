package com.example.genius.dto.Disrecord;

import com.example.genius.dto.mywork.ConceptDis;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Data
public class Disrecord {
    String name;//成果名
    LocalDateTime time;//搜索时间
    int searchID;//搜索者ID
    String recordId ;//成果ID
    ArrayList<ConceptDis> conceptDis = new ArrayList<ConceptDis>();
    public Disrecord(String recordId, String content, LocalDateTime time,ArrayList<ConceptDis> conceptDis) {
        this.name = content;
        this.time = time;
        this.recordId = recordId;
        this.conceptDis = conceptDis;
    }
}
