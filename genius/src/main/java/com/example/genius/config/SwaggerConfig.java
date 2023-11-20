package com.example.genius.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    //apiInfo类,介绍作者信息，随便写就行
    private ApiInfo apiInfo(){
        springfox.documentation.service.Contact contact = new Contact("lcf","https://www.baidu.com/","123@qq.com");
        return new ApiInfo(
                "接口文档示例",
                "林稍一抹轻如画，知是淮流转处山",
                "v1.0",
                "https://scs.buaa.edu.cn/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    @Bean
    public Docket user() {
        String regex="(?i).*user.*";//只扫描文件路径含有account的文件
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //生成接口文档时，忽略以下三种参数类型，一般调试接口用不到
                .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class, HttpServletResponse.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(regex))
                .build()
                .groupName("user");
    }

    @Bean
    public Docket email() {
        String regex="(?i).*email.*";//只扫描文件路径含有account的文件
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //生成接口文档时，忽略以下三种参数类型，一般调试接口用不到
                .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class, HttpServletResponse.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(regex))
                .build()
                .groupName("email");
    }

}

