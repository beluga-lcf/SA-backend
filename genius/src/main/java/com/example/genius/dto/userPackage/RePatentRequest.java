package com.example.genius.dto.userPackage;

public class RePatentRequest {
    private String patentId;
    private String patentName;

    public RePatentRequest(String patentId, String patentName) {
        this.patentId = patentId;
        this.patentName = patentName;
    }

    public String getPatentId() {
        return patentId;
    }

    public String getPatentName() {
        return patentName;
    }
}
