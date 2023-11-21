package com.example.genius.dto.searchResult;

import lombok.Getter;

@Getter
public enum LanguageEnum {
    English("English", "en"),
    Chinese("Chinese", "zh-cn");
    public final String name;
    public final String code;
    LanguageEnum(String name, String code){
        this.name = name;
        this.code = code;
    }
    public static String getLanguageCode(String languageName) {
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.name.equals(languageName)) {
                return languageEnum.code;
            }
        }
        return "";
    }
}
