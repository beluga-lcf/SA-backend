package com.example.genius;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Calendar;
import java.util.HashMap;

public class Jwt {
    @Test
    void contextLoads(/*String email*/) {
        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        // 3600秒后令牌token失效
        instance.add(Calendar.SECOND,1);
        String token = JWT.create()
                .withHeader(map) 
                .withClaim("email", "2505293361@qq.com")
                .withClaim("user_id", 1)
                .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256("Wunderkinder"));//签名
        System.out.println(token);
    }

    @Test
    public String getIdByJwt(/*String token*/){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Wunderkinder")).build();
            DecodedJWT verify = jwtVerifier.verify("CpqK_Dg2yn4xHzMfGZpIBGAE");
            System.out.println(verify.getClaim("email"));
            System.out.println(verify.getClaim("user_id"));
            System.out.println("令牌过期时间：" + verify.getExpiresAt());
            return String.valueOf(verify.getClaim("user_id"));
        } catch (TokenExpiredException e) {
            // 处理令牌过期异常
            System.out.println("令牌已过期");
            // 可以选择刷新令牌或者要求用户重新登录
        } catch (JWTVerificationException e) {
            // 处理非法令牌异常
            System.out.println("非法令牌");
            // 可以记录异常并拒绝访问或要求用户重新登录
        }
        return null; // 或者返回一个特殊值，表示令牌无效
    }

    @Test
    @GetMapping("/greeting")
    public ResponseEntity<String> getToken(@RequestHeader(value = "Authorization", required = false) String language) {
        // code that uses the language variable
        return null;
    }


}
