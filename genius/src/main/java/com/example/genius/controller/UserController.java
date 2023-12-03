package com.example.genius.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.mywork.ConceptDis;
import com.example.genius.dto.mywork.MyWorkDis;
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
import com.example.genius.util.RedisUtils;
import com.example.genius.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisUtils redisUtils;

    public boolean checkVrCode(String email,String code){
        if(redisUtils.get(email) != null){
            if(code.equals(redisUtils.get(email))){
                return true;
            }
        }
        return false;

    }

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

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.GET)
    public Response sendVerifyCode(String email, String type){ //邮箱，类型
        if(redisUtils.get(email) != null){
            return getErrorResponse(null,ErrorType.already_send_email);
        }
        Random random = new Random();
        if(type.equals("register")){
            int vrcode = random.nextInt(9000)+1000;
            redisUtils.set(email,String.valueOf(vrcode), 60L, TimeUnit.SECONDS);
            emailService.sendRegisterVerifyMail(email,String.valueOf(vrcode));
        }
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
    public Response getWorks() {//依据用户ID获取学术成果
        System.out.println("12");
        //int userid = (int)session.getAttribute("userId");
        int userid = 1;
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);
        UseridRelatedOpenalexid a = uroService.getOne(queryWrapper);
        if (a == null) {
            return getSimpleError(); // Errortype to be done
        }
        String openalexId = a.getOpenalexid();
        System.out.println(openalexId);
        String jsons = openAlexService.getWorksByUser(openalexId);
        JSONObject json = JSONObject.parseObject(jsons);
        String resJson = json.getString("results");
        JSONArray resArray = JSON.parseArray(resJson);
//        JSONArray jsonArray = new JSONArray();
        MyWorkDis myWorkDis = new MyWorkDis();
        for (int i = 0; i < resArray.size(); i++) {
            JSONObject j = resArray.getJSONObject(i);
            myWorkDis.setId(j.getString("id"));
            myWorkDis.setTitle(j.getString("title"));
            myWorkDis.setDate(j.getString("publication_date"));
            JSONArray j2 = j.getJSONArray("concepts");
            for (int k = 0; k < j2.size(); k++) {
                myWorkDis.getConceptDis().add(new ConceptDis(j2.getJSONObject(k).getString("display_name")));
            }
//            JSONObject o1 = new JSONObject();
//            o1.put("id", j.getString("id"));
//            o1.put("title", j.getString("title"));
//            o1.put("date", j.getString("publication_date"));
//            JSONArray j3 = new JSONArray();
//            JSONArray j2 = j.getJSONArray("concepts");
//            for(int k = 0; k < j2.size(); k++) {
//                JSONObject k2 = new JSONObject();
//                k2.put("name",j2.getJSONObject(k).getString("display_name"));
//                j3.add(k2);
//            }
//            o1.put("concepts",j3);
//            jsonArray.add(o1);
//        }
        }
        return getSuccessResponse(myWorkDis);
    }
}
