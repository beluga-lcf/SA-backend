package com.example.genius.dto.userPackage;

public class LoginInfo {
    private String nickName;
    private String token;

    public LoginInfo(String nickName, String token) {
        this.nickName = nickName;
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public String getToken() {
        return token;
    }
}
