package com.example.genius;

import com.example.genius.util.ApiUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testConvert {

    @Test
    public void test(){
        try {
            String institutionName = "Carolinas Medical Center";
            String url = "https://api.openalex.org/institutions?search=Carolinas%Medical%Center";
            String result = ApiUtil.get(url, "");
            System.out.println(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        String originalJson = "{\"page\":1,\"size\":10,\"order_field\":\"date\",\"order_direction\":\"desc\",\"user_id\":\"3b9547dd87904c44923d675711729962\",\"aggregations\":{\"type\":\"\",\"subject\":\"\",\"year\":\"\",\"source\":\"\",\"collection\":\"\",\"lang\":\"\",\"funding\":\"\",\"institution\":\"\",\"license\":\"\"}}";
        // 去除转义字符
        String requestBody = originalJson.replace("\\", "");
        System.out.println(requestBody);
    }

    @Test
    public void test3(){
        String ori_str = "(title%3A(1))%20OR%20(author%3A(2))%20AND%20(keyword%3A%221%22)%20OR%20(author%3A(3))%20OR%20(institution%3A(4))%20AND%20(author%3A(1))";
        String str1 = ori_str.replace("%3A",":");
        String str2 = str1.replace("%20"," ");
        String str3 = str2.replace("%22","\"");
        System.out.println(str3);
    }





}
