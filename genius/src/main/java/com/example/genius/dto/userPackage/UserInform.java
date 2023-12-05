package com.example.genius.dto.userPackage;

public class UserInform {
    public String email;
    public String nick_name;
    public String person_description;
    public Integer sex;

    public UserInform(String email, String nick_name, String person_description, Integer sex) {
        this.email = email;
        this.nick_name = nick_name;
        this.person_description = person_description;
        this.sex = sex;
    }
}
