package com.example.genius.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.userPackage.OpenalexInform;
import com.example.genius.dto.userPackage.Token;
import com.example.genius.dto.userPackage.UserInform;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public Response register(String nick_name, String email, String password, String captcha){
        User user = new User();
        user.setNickName(nick_name);
        user.setEmail(email);
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User check_user = userService.getOne(queryWrapper);
//        JSONObject jsonObject = new JSONObject();
        if(check_user!=null){
//            jsonObject.put("code", 201);
//            jsonObject.put("message", "邮箱已被注册");
//            String json = jsonObject.toJSONString();
            return getErrorResponse(null, ErrorType.already_registerd);
        }
//        else if (captcha != /*本地验证码*/) {
//            return getErrorResponse(401, "验证码错误");
//        }
        else {
            userService.save(user);
            log.info("There is a new user! " + user.getNickName());
//            jsonObject.put("code", 200);
//            jsonObject.put("message", "注册成功");
//            String json = jsonObject.toJSONString();
            return getSuccessResponse(null);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(String email, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User checkUser = userService.getOne(queryWrapper);
//        JSONObject jsonObject = new JSONObject();
        if(checkUser == null) {
//            jsonObject.put("code", 202);
//            jsonObject.put("token", 0);
//            jsonObject.put("message", "邮箱未注册");
//            String json = jsonObject.toJSONString();
            return getErrorResponse(null, ErrorType.without_register);
        }
        if(!checkUser.getPassword().equalsIgnoreCase(password)){
//            jsonObject.put("code", 203);
//            jsonObject.put("token", 0);
//            jsonObject.put("message", "用户名或密码错误");
//            String json = jsonObject.toJSONString();
            return getErrorResponse(null, ErrorType.wrong_pwd);
        }
        else {
            String token = contextLoads(email, Math.toIntExact(checkUser.getUserId()));
            session.setAttribute("userId",checkUser.getUserId());
//            jsonObject.put("code", 200);
//            jsonObject.put("token", token);
//            jsonObject.put("message", "登陆成功");
//            String json = jsonObject.toJSONString();
            log.info("一名用户已登录,id:" + checkUser.getUserId());
            Token atoken = new Token(token);
            return getSuccessResponse(atoken);
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
                .withClaim("userid", id)
                .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256("Wunderkinder"));//签名
        return token;
    }

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
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

    @RequestMapping(value = "/relateOpenalex", method = RequestMethod.GET)
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

    /*
    修改个人信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updateUserInform(@RequestHeader(value = "Authorization") String token, String nick_name, Integer sex, String person_description) {
        JSONObject jsonObject = new JSONObject();
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            // 获取用户
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", userid);
            User checkUser = userService.getOne(queryWrapper);
            // 更新信息 updateById
            if (nick_name != null) {
                checkUser.setNickName(nick_name);
            }
            if (sex != null && sex != 0) {
                checkUser.setSex(sex);
            }
            if (person_description != null) {
                checkUser.setPersonDescription(person_description);
            }
            userService.updateById(checkUser);
//            jsonObject.put("code", 200);
            String aemail = checkUser.getEmail();
//            jsonObject.put("nick_name", checkUser.getNickName());
            String anick_name = checkUser.getNickName();
//            jsonObject.put("sex", checkUser.getSex());
            Integer asex = checkUser.getSex();
//            jsonObject.put("person_description", checkUser.getPersonDescription());
            String aperson_description = checkUser.getPersonDescription();
//            jsonObject.put("message", "更新成功");
//            String json = jsonObject.toJSONString();
            return getSuccessResponse(new UserInform(aemail, anick_name, aperson_description, asex));
        }
        else if (userid == -1) {
            return getErrorResponse(ErrorType.login_timeout);
        }
        else if (userid == -2) {
            return getErrorResponse(ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/getinform", method = RequestMethod.GET)
    public Response getUserInform(@RequestHeader(value = "Authorization") String token) {
        JSONObject jsonObject = new JSONObject();
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            // 获取用户
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", userid);
            User checkUser = userService.getOne(queryWrapper);
            return getSuccessResponse(new UserInform(checkUser.getEmail(), checkUser.getNickName(), checkUser.getPersonDescription(), checkUser.getSex()));
        }
        else if (userid == -1) {
            return getErrorResponse(ErrorType.login_timeout);
        }
        else if (userid == -2) {
            return getErrorResponse(ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    public Response changePassword(@RequestHeader(value = "Authorization") String token, String oldPw, String newPw) {
//        JSONObject jsonObject = new JSONObject();
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", userid);
            User user = userService.getOne(queryWrapper);
            // 验证旧密码正确
            if (oldPw.equals(user.getPassword())) {
                user.setPassword(newPw);
                userService.updateById(user);
                log.info("已修改密码");
//                jsonObject.put("code", 200);
//                jsonObject.put("message", "修改密码成功");
//                String json = jsonObject.toJSONString();
                return getSuccessResponse(null);
            }
            else {
                log.info("密码错误");
//                jsonObject.put("code", 206);
//                jsonObject.put("message", "密码错误");
//                String json = jsonObject.toJSONString();
                return getErrorResponse(ErrorType.wrong_pwd);
            }
        }
        else if (userid == -1) {
            return getErrorResponse(ErrorType.login_timeout);
        }
        else if (userid == -2) {
            return getErrorResponse(ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/getScholarInfo", method = RequestMethod.GET)
    public Response getScholarInfo(@RequestHeader(value = "Authorization") String token) {
        JSONObject jsonObject = new JSONObject();
        // jwt解出id
        int user_id = getIdByJwt(token);
        if (user_id >= 0) {
            // 查关联表
            QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            UseridRelatedOpenalexid searchUser = uroService.getOne(queryWrapper);
            if (searchUser == null) {
                // 未查到对应的openalex id, 可能是openalex没收录或不是认证学者
                // 根据认证来源判断一定是没认证
                return getErrorResponse(ErrorType.not_scholar);
            }
            else {
                QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
                queryWrapperUser.eq("userid", user_id);
                User user = userService.getOne(queryWrapperUser);
                OpenalexInform openalexInform = new OpenalexInform();
                return getSuccessResponse(new ScholarInform(user, searchUser));
            }
        }
        else if (user_id == -1) {
            return getErrorResponse(ErrorType.login_timeout);
        }
        else if (user_id == -2) {
            return getErrorResponse(ErrorType.jwt_illegal);
        }
        return null;
    }
}
