package com.example.genius.dto.userPackage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInform {
    @JsonProperty("email")
    private String email;
    @JsonProperty("nick_name")
    private String nick_name;
    @JsonProperty("person_description")
    private String person_description;
    @JsonProperty("sex")
    private Integer sex;
    @JsonProperty("isAuthor")
    private boolean isAuthor;

    public UserInform(String email, String nick_name, String person_description, Integer sex, boolean isAuthor) {
        this.email = email;
        this.nick_name = nick_name;
        this.person_description = person_description;
        this.sex = sex;
        this.isAuthor = isAuthor;
    }

    public String getEmail() {
        return email;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getPerson_description() {
        return person_description;
    }

    public Integer getSex() {
        return sex;
    }

    public boolean getIsAuthor() {
        return isAuthor;
    }
}
