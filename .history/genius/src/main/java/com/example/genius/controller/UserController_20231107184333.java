package com.example.genius.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")

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
    }    @Autowired
    HttpSession session;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(String username, String password){
        User user = new User();
        user.setNickName(username);
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User check_user = userService.getOne(queryWrapper);
        if(check_user!=null){
            return getErrorResponse("用户名已存在!");
        }
        else {
            userService.save(user);
            log.info("There is a new user!"+username);
            return getSuccessResponse("注册成功!");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(String username, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User checkuser = userService.getOne(queryWrapper);
        if(checkuser == null) {
            return getErrorResponse("用户名不存在!");
        }
        if(checkuser.getPassword().equalsIgnoreCase(password)){
            return getErrorResponse("密码错误!");
        }
        else {
            session.setAttribute("userId",checkuser.getUserId());
            log.info("一名用户已登录");
            return getSuccessResponse("登陆成功！");
        }
    }
}
