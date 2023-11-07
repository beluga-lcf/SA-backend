package com.example.genius.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.genius.entity.User;
import com.example.genius.entity.VO.Response;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@Controller
@RequestMapping("/genius/user")
public class UserController extends BaseController{
    @Autowired
    IUserService userService;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/signUp")
    public Response signUp(String[] args) {
        User curUser = userService.getCurrentUser(args);
        if (curUser.getUserId() > 0) {
            return getSuccessResponse(null);
        }
        else {
            return getErrorResponse(curUser.getNickName());
        }
    }
}
