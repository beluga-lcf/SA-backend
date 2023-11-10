package com.example.genius.enums;

public enum ErrorType {
    already_registerd(1001, "用户已存在"),
    invalid_email(1002, "无效的邮箱");
    public Integer code;
    public String message;


    ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
