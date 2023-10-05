package com.example.genius.controller;

import com.example.genius.entity.VO.Response;

public class BaseController {

    protected <T> Response getSuccessResponse(T t) {
        Response<T> response = new Response<>();
        response.setStatus("success");
        response.setCode(200);
        response.setInfo("请求成功");
        response.setData(t);
        return response;
    }

    protected <T> Response getErrorResponse(T t) {
        return new Response("error",500,"服务器返回错误",t);
    }
}