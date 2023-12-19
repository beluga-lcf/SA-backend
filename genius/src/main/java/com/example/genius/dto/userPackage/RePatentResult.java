package com.example.genius.dto.userPackage;

import com.example.genius.entity.UserId2PSPatentId;

import java.util.List;

public class RePatentResult {
    private int code;
    private String message;
    private List<UserId2PSPatentId> patentList;

    public RePatentResult(int code, String message, List<UserId2PSPatentId> patentList) {
        this.code = code;
        this.message = message;
        this.patentList = patentList;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<UserId2PSPatentId> getPatentList() {
        return patentList;
    }
}
