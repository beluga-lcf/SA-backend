package com.example.genius.dto.funder;

import com.example.genius.dto.authorDisplay.AuthorDisplay;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RelateWork {
    String name;
    String workID;
    ArrayList<AuthorDisplay> authors = new ArrayList<AuthorDisplay>();

    public RelateWork(String id, String displayName,ArrayList<AuthorDisplay> authors) {
        this.workID = id;
        this.name = displayName;
        this.authors = authors;
    }
}
