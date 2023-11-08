package com.example.genius;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
        instance.add(Calendar.SECOND,3600);

        String token = JWT.create()
                .withHeader(map) // header可以不写，因为默认值就是它
//                .withClaim("email", email)  //payload
                .withClaim("email", "2505293361@qq.com")
                .withClaim("user_id", 1)
                .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256("Wunderkinder"));//签名

        System.out.println(token);
    }

    @Test
    public void test(){
        // 通过签名生成验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Wunderkinder")).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE2OTk0MzczMjYsImVtYWlsIjoiMjUwNTI5MzM2MUBxcS5jb20ifQ.-KdZv81UH27961UVYcmTaTd4cMXimIDcsjZO_spXhZ8");
        System.out.println(verify.getClaim("email"));
        System.out.println(verify.getClaim("user_id"));
        System.out.println("令牌过期时间："+verify.getExpiresAt());

    }

    @Test
    @GetMapping("/greeting")
    public ResponseEntity<String> getToken(@RequestHeader(value = "Authorization", required = false) String language) {
        // code that uses the language variable
        return new ResponseEntity<String>(greeting, HttpStatus.OK);
    }


}
