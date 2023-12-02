package com.example.genius;

import com.example.genius.util.ApiUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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




}
