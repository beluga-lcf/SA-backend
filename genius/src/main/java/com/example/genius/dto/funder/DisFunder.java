package com.example.genius.dto.funder;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DisFunder {
    String name;
    String country;
    String homepage;
    String description;
    ArrayList<RelateWork> relateWorks = new ArrayList<RelateWork>();
}
