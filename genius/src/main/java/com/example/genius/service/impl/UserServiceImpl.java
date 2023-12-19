package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.dto.userPackage.RePatentResult;
import com.example.genius.entity.User;
import com.example.genius.entity.UserId2PSPatentId;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public int logOff(int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        User checker = userMapper.selectOne(queryWrapper);
        if (checker != null) {
            userMapper.delete(queryWrapper);
            checker = userMapper.selectOne(queryWrapper);
            if (checker != null) {
                return -2; // 删除失败
            }
            else {
                return 0; //成功
            }
        }
        else {
            return -1; // 删除用户不存在
        }
    }
}
