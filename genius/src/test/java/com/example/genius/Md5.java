package com.example.genius;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import org.junit.jupiter.api.Test;

public class Md5 {
    @Test
    public void testMd5() {
        String password = "qweqwe";
        System.out.println(SecureUtil.md5(password));
    }
}
