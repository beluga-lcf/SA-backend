package com.example.genius.test;

import com.example.genius.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test")
public class testController {


    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public void testSend() throws MalformedURLException {
        String url = "https://api.openalex.org/authors/random";
        String param = "";
        String result = ApiUtil.get(url, param);
        log.info("返回的结果：", result);
    }

    @RequestMapping(value = "/send2",method = RequestMethod.GET)

    public class HttpClientExample {
        public void main(String[] args) {
            try {
                // 设置请求URL
                URL url = new URL("https://pubscholar.cn/hky/open/resources/api/v1/article/c6f51ede40a548e08e9cc5c5f57fba31?uid=3b9547dd87904c44923d675711729962&type=article");

                // 打开连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // 设置请求方法
                connection.setRequestMethod("GET");

                // 设置请求头
                connection.setRequestProperty("Accept", "application/json, text/plain, */*");
                connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                connection.setRequestProperty("Connection", "keep-alive");
                connection.setRequestProperty("Cookie", "XSRF-TOKEN=0023e620-b52e-4ede-98f4-455d5363fddd");
                connection.setRequestProperty("Sec-Fetch-Dest", "empty");
                connection.setRequestProperty("Sec-Fetch-Mode", "cors");
                connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
                connection.setRequestProperty("X-XSRF-TOKEN", "0023e620-b52e-4ede-98f4-455d5363fddd");
                connection.setRequestProperty("sec-ch-ua", "\"Microsoft Edge\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
                connection.setRequestProperty("sec-ch-ua-mobile", "?0");
                connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");

                // 获取响应代码
                int responseCode = connection.getResponseCode();

                // 读取响应内容
                BufferedReader reader;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }

                // 打印响应内容
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 关闭连接
                connection.disconnect();

                // 打印响应结果
                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Body: " + response.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
