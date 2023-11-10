package com.example.genius.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.entity.Mail;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.enums.ErrorType;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.EmailService;
import com.example.genius.service.UserService;
import com.example.genius.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(String username, String password){
        User user = new User();
        user.setNickName(username);
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User check_user = userService.getOne(queryWrapper);
        if(check_user!=null){
            return getErrorResponse(null, ErrorType.already_registerd);
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
            return getSimpleError(); // Errortype to be done
        }
        if(checkuser.getPassword().equalsIgnoreCase(password)){
            return getSimpleError() ; // Errortype to be done
        }
        else {
            session.setAttribute("userId",checkuser.getUserId());
            log.info("一名用户已登录");
            return getSuccessResponse("登陆成功！");
        }
    }

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
    public Response sendVerifyCode(String email, String type){ //邮箱，类型
        /*
        to be done
         */
        return getSuccessResponse("验证码已发送！");
    }
}
