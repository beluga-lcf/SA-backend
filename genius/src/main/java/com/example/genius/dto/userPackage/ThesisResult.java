package com.example.genius.dto.userPackage;

import com.example.genius.entity.UserId2PSThesisId;

import java.util.List;

public class ThesisResult {
    private int code;
    private String message;
    private List<UserId2PSThesisId> thesisList;

    public ThesisResult(int code, String message, List<UserId2PSThesisId> thesisList) {
        this.code = code;
        this.message = message;
        this.thesisList = thesisList;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<UserId2PSThesisId> getThesisList() {
        return thesisList;
    }
}
