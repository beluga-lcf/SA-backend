package com.example.genius;


import com.example.genius.dto.mywork.MyWorkDis;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.impl.OpenAlexService;
import com.example.genius.service.impl.UseridRelatedOpenalexidServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class JSONTest {
    @Autowired
    private OpenAlexService openAlexService;

    @Test
    public void getArrayTest() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("String1");
        jsonArray.put("String2");
        jsonArray.put("String3");
        String[] stringArray = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringArray[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Test Normal Case:");
        for (String str : stringArray) {
            System.out.println(str);
        }
    }

//    @Test
//    public void getStringTest() throws JSONException {
//        List<MyWorkDis> myWorkDisS = openAlexService.getWorks("https://openalex.org/A5040654425");
//        for (MyWorkDis myWorkDis : myWorkDisS) {
//            System.out.println(myWorkDis.toString());
//        }
//    }
}
