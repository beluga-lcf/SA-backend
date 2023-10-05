package com.example.genius.service.impl;

import com.example.genius.entity.User;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
