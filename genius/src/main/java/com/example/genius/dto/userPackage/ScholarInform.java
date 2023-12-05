package com.example.genius.dto.userPackage;

import com.example.genius.entity.User;

public class ScholarInform {
    public String name;
    public String identity;
    public String organization;
    public String Interests;
    public String email;
    public String introduction;
    public String achievements;

    public ScholarInform(OpenalexInform openalexInform) {

        this.openalexInform = openalexInform;
    }
}
