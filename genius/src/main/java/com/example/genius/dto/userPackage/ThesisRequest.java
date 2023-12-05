package com.example.genius.dto.userPackage;

public class ThesisRequest {
    private String thesisId;
    private String thesisName;

    public ThesisRequest(String thesisId, String thesisName) {
        this.thesisId = thesisId;
        this.thesisName = thesisName;
    }

    public String getThesisId() {
        return thesisId;
    }

    public String getThesisName() {
        return thesisName;
    }
}
