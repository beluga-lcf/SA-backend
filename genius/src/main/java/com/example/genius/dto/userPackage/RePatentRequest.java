package com.example.genius.dto.userPackage;

public class RePatentRequest {
    private String patentId;
    private String patentName;
    private String[] author;

    public RePatentRequest(String patentId, String patentName, String[] author) {
        this.patentId = patentId;
        this.patentName = patentName;
        this.author = author;
    }

    public String getPatentId() {
        return patentId;
    }

    public String getPatentName() {
        return patentName;
    }

    public String[] getAuthor() {
        return author;
    }
}
