package com.example.genius.dto.userPackage;

public class ThesisRequest {
    private String thesisId;
    private String thesisName;
    private String[] author;

    public ThesisRequest(String thesisId, String thesisName, String[] author) {
        this.thesisId = thesisId;
        this.thesisName = thesisName;
        this.author = author;
    }

    public String getThesisId() {
        return thesisId;
    }

    public String getThesisName() {
        return thesisName;
    }

    public String[] getAuthor() {
        return author;
    }
}
