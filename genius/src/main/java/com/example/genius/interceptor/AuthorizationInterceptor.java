package com.example.genius.interceptor;

import com.example.genius.controller.BaseController;
import com.example.genius.entity.Response;
import com.example.genius.enums.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            // 在这里进行 Authorization 头的分析
            int id = BaseController.getIdByJwt(authorizationHeader);
            if (id >= 0) {
                // 将 id 加入请求处理方法的参数中
                System.out.println("用户id" + id);
                request.setAttribute("id", id);
                return true; // 继续处理请求
            } else {
                // id 不符合条件，直接返回错误响应
                sendErrorResponse(response, id);
                return false; // 不再处理请求
            }
        } else {
            // 如果没有 Authorization 头，也直接返回错误响应
            sendErrorResponse(response, -2);
            return false; // 不再处理请求
        }
    }

    private int analyzeAuthorization(String authorizationHeader) {
        // 进行 Authorization 头的分析操作，返回一个 int 类型的 id
        // 这里需要根据实际的逻辑进行处理，将 Authorization 解析为 id
        // 示例代码：假设直接将字符串解析为整数
        try {
            return Integer.parseInt(authorizationHeader);
        } catch (NumberFormatException e) {
            return -1; // 解析失败，返回不合法的 id
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int errorType) throws IOException {
        // 构建自定义的错误信息对象
        Response errorResponse;
        switch (errorType) {
            case -1: {
                errorResponse = BaseController.getErrorResponse(null, ErrorType.login_timeout);
                break;
            }
            case -2: {
                errorResponse = BaseController.getErrorResponse(null, ErrorType.jwt_illegal);
                break;
            }
            default: {
                errorResponse = BaseController.getSimpleError();
            }
        }

        // 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        // 设置响应头和内容类型
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        // 发送 JSON 响应给前端
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}