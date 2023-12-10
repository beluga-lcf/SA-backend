package com.example.genius.dto.funder;

import lombok.Data;

@Data
public class ReturnFunder {
    String funderID;
    String funderName;

    public ReturnFunder(String funder, String funderDisplayName) {
        this.funderID = funder;
        this.funderName = funderDisplayName;
    }
}
