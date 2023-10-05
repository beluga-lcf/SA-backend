package com.example.genius;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class Generator {
    @Test
    public void generate() {
        FastAutoGenerator
                .create("jdbc:mysql://127.0.0.1:3306/sa-backend?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true", "root", "Lvchaofan")
                .globalConfig(builder -> {
                    builder
                            .author("chaofan") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://Workspace//Application//SA-backend//SA-backend//genius//src//main//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("genius") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://Workspace//Application//SA-backend//SA-backend//genius//src//main//resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user", "paper", "scholar") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })

                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}