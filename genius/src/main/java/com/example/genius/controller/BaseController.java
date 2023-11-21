package com.example.genius.controller;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.genius.entity.Response;
import com.example.genius.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class BaseController {
    protected <T> Response<T> getSimpleSuccess(){
        Response<T> response = new Response<>();
        response.setStatus("success");
        response.setCode(200);
        response.setInfo("请求成功");
        response.setData(null);
        return response;
    }

    protected <T> Response<T> getSimpleError(){
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode(500);
        response.setInfo("请求失败");
        response.setData(null);
        return response;
    }
    protected <T> Response<T> getSuccessResponse(T t) {
        Response<T> response = new Response<>();
        response.setStatus("success");
        response.setCode(200);
        response.setInfo("请求成功");
        response.setData(t);
        return response;
    }

    protected <T> Response<T> getErrorResponse(T t) {
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode(201);
        response.setInfo("请求错误");
        response.setData(t);
        return response;
    }
    protected <T> Response<T> getErrorResponse(T t, ErrorType errorType) {
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode(errorType.getCode());
        response.setInfo(errorType.getMessage());
        response.setData(t);
        return response;
    }

    /*
    接收token
    成功返回user_id
    令牌过期返回-1
    令牌非法返回-2
    以封装响应事件
     */
    public int getIdByJwt(String token){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Wunderkinder")).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            log.info(String.valueOf(verify.getClaim("email")));
            log.info(String.valueOf(verify.getClaim("user_id")));
            log.info("令牌过期时间：" + verify.getExpiresAt());
            return Integer.parseInt(String.valueOf(verify.getClaim("user_id")));
        } catch (TokenExpiredException e) {
            // 处理令牌过期异常
            log.info("令牌已过期");
            JSONObject jsonObject = new JSONObject();
            int user_id = getIdByJwt(token);
            jsonObject.put("code", 204);
            jsonObject.put("message", "登录过期，请重新登陆");
            String json = jsonObject.toJSONString();
            getErrorResponse(json);
            return -1;
        } catch (JWTVerificationException e) {
            // 处理非法令牌异常
            log.info("非法令牌");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 203);
            jsonObject.put("message", "请先登录");
            String json = jsonObject.toJSONString();
            getErrorResponse(json);
            return -2;
        }
    }

}