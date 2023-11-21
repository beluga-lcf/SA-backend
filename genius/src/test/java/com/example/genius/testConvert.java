package com.example.genius;

import com.example.genius.dto.aggregation.Payload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testConvert {

    @Test
    public void test(){
        try {
            Payload payload = new Payload();
            // 使用Jackson库将对象转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String requestBody = objectMapper.writeValueAsString(payload);
            System.out.println("Request: " + requestBody);
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
