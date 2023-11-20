package com.example.genius.controller;

import com.example.genius.entity.Response;
import com.example.genius.enums.ErrorType;

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

    protected <T> Response<T> getErrorResponse(T t, ErrorType errorType) {
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode(errorType.getCode());
        response.setInfo(errorType.getMessage());
        response.setData(t);
        return response;
    }

    protected <T> Response getErrorResponse(T code, T message) {
        Response<T> response = new Response<>();
        response.setStatus("error");
        response.setCode((Integer) code);
        response.setInfo(message.toString());
        response.setData(null);
        return response;
    }

}
