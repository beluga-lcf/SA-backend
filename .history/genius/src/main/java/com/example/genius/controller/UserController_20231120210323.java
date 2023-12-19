package com.example.genius.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.entity.Mail;
import com.alibaba.fastjson2.JSON;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.enums.ErrorType;
import com.example.genius.mapper.UseridRelatedOpenalexidMapper;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.EmailService;
import com.example.genius.service.UseridRelatedOpenalexService;
import com.example.genius.service.UserService;
import com.example.genius.service.UseridRelatedOpenalexService;
import com.example.genius.service.impl.OpenAlexService;
import com.example.genius.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private UseridRelatedOpenalexService uroService;
    @Autowired
    private UseridRelatedOpenalexidMapper uroMapper;

    @Autowired
    private EmailService emailService;
    @Autowired
    private OpenAlexService openAlexService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(String email, String password, String captcha){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User check_user = userService.getOne(queryWrapper);
        if(check_user!=null){
            return getErrorResponse(null, ErrorType.already_registerd);
        }
//        else if (captcha != /*本地验证码*/) {
//            return getErrorResponse(401, "验证码错误");
//        }
        else {
            userService.save(user);
            log.info("There is a new user! " + user.getNickName());
            return getSuccessResponse(null);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(String email, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User checkUser = userService.getOne(queryWrapper);
        if(checkUser == null) {
            return getErrorResponse(null, ErrorType.invalid_email);
        }
        if(checkUser.getPassword().equalsIgnoreCase(password)){
            return getErrorResponse(null, ErrorType.wrong_pwd);
        }
        else {
            String token = contextLoads(email, Math.toIntExact(checkUser.getUserId()));
            session.setAttribute("userId",checkUser.getUserId());
            log.info("一名用户已登录");
            return getSuccessResponse(token);
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

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
    public Response sendVerifyCode(String email, String type){ //邮箱，类型
        /*
        to be done
         */
        return getSuccessResponse("验证码已发送！");
    }

    @RequestMapping(value = "/relateOpenalex",method = RequestMethod.GET)
    public Response relateOpenalex(String openalexId){// 依据openalexID与Userid进行连接
        System.out.println("11");
//        Integer userid = (Integer) session.getAttribute("userId");
        UseridRelatedOpenalexid a = new UseridRelatedOpenalexid();
//        if(userid == null){
//            return getErrorResponse(null,ErrorType.not_login);
//        }
        a.setUserId(1);
        a.setOpenalexid(openalexId);
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", 1);
        List<UseridRelatedOpenalexid> userids =uroMapper.selectList(queryWrapper);
        if(!userids.isEmpty()){
            return getErrorResponse(null, ErrorType.already_registerd);
        }
        else {
            uroService.save(a);
            log.info("There is a new expert!"+1);
            return getSuccessResponse("学者认证成功！");
        }

    }

    @GetMapping(value = "/getWorks")
    public Response getWorks(){//依据用户ID获取学术成果
        System.out.println("12");
        //Long userid = (Long) session.getAttribute("userId");
        int userid = 1;
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        UseridRelatedOpenalexid a = uroService.getOne(queryWrapper);
        if(a == null) {
            return getSimpleError(); // Errortype to be done
        }
        String openalexId = a.getOpenalexid();
        System.out.println(openalexId);
        String jsons = openAlexService.getWorksByUser(openalexId);
        JSONObject json = JSONObject.parseObject(jsons);
        String resJson = json.getString("results");
        JSONArray resArray = JSON.parseArray(resJson);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resArray.size(); i++) {
            JSONObject j = resArray.getJSONObject(i);
            JSONObject o1 = new JSONObject();
            o1.put("id", j.getString("id"));
            o1.put("title", j.getString("title"));
            o1.put("date", j.getString("publication_date"));
            JSONArray j3 = new JSONArray();
            JSONArray j2 = j.getJSONArray("concepts");
            for(int k = 0; k < j2.size(); k++) {
                JSONObject k2 = new JSONObject();
                k2.put("name",j2.getJSONObject(k).getString("display_name"));
                j3.add(k2);
            }
            o1.put("concepts",j3);
            jsonArray.add(o1);
        }
        String output = jsonArray.toJSONString();
        return getSuccessResponse(output);
    }


}
