package com.example.genius.service;

import com.example.genius.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
public interface IUserService extends IService<User> {
    public User getCurrentUser(String[] args)
}
