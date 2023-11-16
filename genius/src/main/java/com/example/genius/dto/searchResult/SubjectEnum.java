package com.example.genius.dto.searchResult;

import lombok.Getter;

@Getter
public enum SubjectEnum { //HashMap会更高效

    AI("artificial intelligence", "https://openalex.org/C154945302"),
    Physics("physics", "https://openalex.org/C121332964"),
    Math("math", "https://openalex.org/C144237770");

    public final String conceptName;
    public final String conceptId;

    SubjectEnum(String conceptName, String conceptId) {
        this.conceptName = conceptName;
        this.conceptId = conceptId;
    }

    public static String getConceptId(String conceptName) {
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            if(subjectEnum.conceptName.equals(conceptName)){
                return subjectEnum.conceptId;
            }
        }
        return "";
    }

}
