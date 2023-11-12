package com.example.genius;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@SpringBootTest
public class Generator {
    public static String url = "jdbc:postgresql://121.36.111.128:5432/postgres?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
    public static String username = "root";
    public static String password = "@Genius2023";

    public static ArrayList<String> tables = new ArrayList<>();
    public void addTableName(){
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'openalex';";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                tables.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void generate() {
        FastAutoGenerator
                .create(url, username, password)
                .globalConfig(builder -> {
                    builder
                            .author("chaofan") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://Workspace//Application//SA-backend//SA-backend//genius//src//main//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("generated") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://Workspace//Application//SA-backend//SA-backend//genius//src//main//resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀

                })

                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}