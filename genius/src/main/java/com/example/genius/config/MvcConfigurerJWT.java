package com.example.genius.config;

import com.example.genius.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurerJWT implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/api/user/JwtTest")
//                .addPathPatterns("/api/user/getTsPs")
        ; // 添加需要拦截的接口路径
    }
}
