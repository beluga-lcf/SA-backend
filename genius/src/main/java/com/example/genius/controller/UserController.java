package com.example.genius.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    HttpSession session;

//    @Autowired
//    UserMapper userMapper;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(String email, String password, String captcha){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User check_user = userService.getOne(queryWrapper);
        if(check_user!=null){
            return getErrorResponse(405, "邮箱已被注册");
        }
//        else if (captcha != /*本地验证码*/) {
//            return getErrorResponse(401, "验证码错误");
//        }
        else {
            userService.save(user);
            log.info("There is a new user! " + user.getNickName());
            return getSuccessResponse(200, "注册成功", null);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(String email, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User checkUser = userService.getOne(queryWrapper);
        if(checkUser == null) {
            return getErrorResponse(404, "用户名不存在!");
        }
        if(checkUser.getPassword().equalsIgnoreCase(password)){
            return getErrorResponse(401, "密码错误!");
        }
        else {
            String token = contextLoads(email, Math.toIntExact(checkUser.getUserId()));
            session.setAttribute("userId",checkUser.getUserId());
            log.info("一名用户已登录");
            return getSuccessResponse(200, "登陆成功！", token);
        }
    }

    private String contextLoads(String email, int id) {

        HashMap<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        // 3600秒后令牌token失效
        instance.add(Calendar.SECOND,3600);

        String token = JWT.create()
                .withHeader(map) // header可以不写，因为默认值就是它
                .withClaim("email", email)  //payload
//                .withClaim("email", "2505293361@qq.com")
                .withClaim("user_id", id)
                .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256("Wunderkinder"));//签名
        return token;
    }
//    @RequestMapping(value = "/name", method = RequestMethod.GET)
//    public Response getNikName(String email) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("email", email);
//        User checkUser = userService.getOne(queryWrapper);
//        if (checkUser == null) {
//            return getErrorResponse(404, "用户名不存在!");
//        }
//        else {
//            log.info("查询昵称" + checkUser.getUserId());
//            return getSuccessResponse(200, "查询成功！", checkUser.getNickName());
//        }
//    }
}