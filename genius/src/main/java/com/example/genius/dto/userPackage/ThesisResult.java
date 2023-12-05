package com.example.genius.dto.userPackage;

public class ThesisResult {
    private int code;
    private String message;

    public ThesisResult(int code, String message) {
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
