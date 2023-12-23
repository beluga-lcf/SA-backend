package com.example.genius.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static com.example.genius.util.ReverseSignatureUtil.*;

@Slf4j
public class ApiUtil {
    public static String get(String url){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取 URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            result = new String(result.getBytes(), "UTF-8");
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        log.info("返回的结果：", result);
        return result;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String get(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取 URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            result = new String(result.getBytes(), "UTF-8");
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        log.info("返回的结果：", result);
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String get(String url, String param, String token) {

        if (StringUtils.isEmpty(token)) {
            return get(url, param);
        }

        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("orther-token", token);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            result = new String(result.getBytes(), "UTF-8");
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /*
    url of pubscholar.cn
     */
    public static String getScholarUrl(String type) {
        return "https://pubscholar.cn/hky/open/resources/api/v1/" + type;
    }

    public static String getScholarUrl2(String type) {
        return "https://pubscholar.cn/hky/open/resources/api/v2/" + type;
    }
    public static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        headers.set("Cookie", "XSRF-TOKEN=c46101a8-6be3-43cb-b141-8329042e49ad");
        headers.set("Origin", "https://pubscholar.cn");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
        headers.set("X-XSRF-TOKEN", "c46101a8-6be3-43cb-b141-8329042e49ad");
        headers.set("sec-ch-ua", "\"Microsoft Edge\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        // 破译
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foocrg227gng6m6fbo95inwakpingbti");
        String timestamp=createTimestamp();
        stringBuilder.append(timestamp);
        String nonce=createNonce();
        stringBuilder.append(nonce);
        String signature=createSHA1(stringBuilder.toString());
        // set
        headers.set("timestamp", timestamp);
        headers.set("nonce", nonce);
        headers.set("signature", signature);
        return headers;
    }

    public static String getConnection(String path) throws Exception{
        // 打开连接
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置请求方法
        connection.setRequestMethod("GET");
        // 设置请求头
        connection.setRequestProperty("Accept", "application/json, text/plain, */*");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Cookie", "XSRF-TOKEN=c46101a8-6be3-43cb-b141-8329042e49ad");
        connection.setRequestProperty("Sec-Fetch-Dest", "empty");
        connection.setRequestProperty("Sec-Fetch-Mode", "cors");
        connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
        connection.setRequestProperty("X-XSRF-TOKEN", "c46101a8-6be3-43cb-b141-8329042e49ad");
        connection.setRequestProperty("sec-ch-ua", "\"Microsoft Edge\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
        connection.setRequestProperty("sec-ch-ua-mobile", "?0");
        connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        // 破译
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foocrg227gng6m6fbo95inwakpingbti");
        String timestamp=createTimestamp();
        stringBuilder.append(timestamp);
        String nonce=createNonce();
        stringBuilder.append(nonce);
        String signature=createSHA1(stringBuilder.toString());
        // set
        connection.setRequestProperty("timestamp", timestamp);
        connection.setRequestProperty("nonce", nonce);
        connection.setRequestProperty("signature", signature);
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
        return response.toString();
    }

}
