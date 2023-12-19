package com.example.genius.dto.funder;

import lombok.Data;

@Data
public class RelateWork {
    String name;
    String workID;

    public RelateWork(String id, String displayName) {
        this.workID = id;
        this.name = displayName;
    }
}
