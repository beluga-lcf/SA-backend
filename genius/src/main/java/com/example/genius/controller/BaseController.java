package com.example.genius.controller;

import com.example.genius.entity.Response;

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
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode(500);
        response.setInfo("请求失败");
        response.setData(t);
        return response;
    }
}
