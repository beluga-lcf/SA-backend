package com.example.genius.dto.userPackage;

public class RePatentResult {
    private int code;
    private String message;

    public RePatentResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
