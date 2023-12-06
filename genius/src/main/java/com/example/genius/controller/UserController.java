package com.example.genius.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.userPackage.*;
import com.example.genius.dto.mywork.ConceptDis;
import com.example.genius.dto.mywork.MyWorkDis;
import com.alibaba.fastjson2.JSON;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.enums.ErrorType;
import com.example.genius.enums.SelectCollectMod;
import com.example.genius.mapper.UseridRelatedOpenalexidMapper;
import com.example.genius.mapper.UserMapper;
import com.example.genius.service.*;
import com.example.genius.service.UseridRelatedOpenalexService;
import com.example.genius.service.impl.OpenAlexService;
import com.example.genius.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
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
    private UserId2PSTIdService userId2PSTIdService;
    @Autowired
    private UserId2PSPIdService userId2PSPIdService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private OpenAlexService openAlexService;
    @Autowired
    private RedisUtils redisUtils;

    public boolean checkVrCode(String email, String code) {
        if (redisUtils.get(email) != null) {
            if (code.equals(redisUtils.get(email))) {
                return true;
            }
        }
        return false;

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public Response register(String nick_name, String email, String password, String captcha) {
        User user = new User();
        user.setNickName(nick_name);
        user.setEmail(email);
        user.setPassword(password);
//        if (captcha != null) {
//            user.setPersonDescription(captcha);
//        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User check_user = userService.getOne(queryWrapper);
        if (check_user != null) {
            return getErrorResponse(null, ErrorType.already_registerd);
        } else if (!checkVrCode(email, captcha)) {
            log.info(captcha);
            log.info(redisUtils.get(email));
            return getErrorResponse(null, ErrorType.wrong_captcha);
        } else {
            userService.save(user);
            log.info("There is a new user! " + user.getNickName());
            return getSuccessResponse(null);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody JSONObject loginInform) {
        String email = loginInform.getString("email");
        String password = loginInform.getString("password");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User checkUser = userService.getOne(queryWrapper);
        if (checkUser == null) {
            return getErrorResponse(null, ErrorType.without_register);
        }
        if (!checkUser.getPassword().equalsIgnoreCase(password)) {
            return getErrorResponse(null, ErrorType.wrong_pwd);
        } else {
            String token = contextLoads(email, Math.toIntExact(checkUser.getUserId()));
            session.setAttribute("userId", checkUser.getUserId());
            log.info("一名用户已登录,id:" + checkUser.getUserId());
            Token atoken = new Token(token);
            return getSuccessResponse(atoken);
        }
    }

    private String contextLoads(String email, int id) {

        HashMap<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        // 3600秒后令牌token失效
        instance.add(Calendar.SECOND, 3600 * 5);

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
    public Response sendVerifyCode(String email) { //邮箱，类型
        if (redisUtils.get(email) != null) {
            return getErrorResponse(null, ErrorType.already_send_email);
        }
        Random random = new Random();
        int vrcode = random.nextInt(9000) + 1000;
        redisUtils.set(email, String.valueOf(vrcode), 60L, TimeUnit.SECONDS);
        emailService.sendRegisterVerifyMail(email, String.valueOf(vrcode));
        return getSuccessResponse("验证码已发送！");
    }

    @RequestMapping(value = "/relateOpenalex", method = RequestMethod.GET)
    public Response relateOpenalex(String openalexId) {// 依据openalexID与Userid进行连接
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
        List<UseridRelatedOpenalexid> userids = uroMapper.selectList(queryWrapper);
        if (!userids.isEmpty()) {
            return getErrorResponse(null, ErrorType.already_registerd);
        } else {
            uroService.save(a);
            log.info("There is a new expert!" + 1);
            return getSuccessResponse("学者认证成功！");
        }

    }

    /*
    修改个人信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updateUserInform(@RequestHeader(value = "Authorization") String token, @RequestBody JSONObject updateSelfInform) {
        String nick_name = updateSelfInform.getString("nick_name");
        Integer sex = updateSelfInform.getInteger("sex");
        String person_description = updateSelfInform.getString("person_description");
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
            String aemail = checkUser.getEmail();
            String anick_name = checkUser.getNickName();
            Integer asex = checkUser.getSex();
            String aperson_description = checkUser.getPersonDescription();
            QueryWrapper<UseridRelatedOpenalexid> queryWrapperUro = new QueryWrapper<>();
            queryWrapperUro.eq("user_id", userid);
            UseridRelatedOpenalexid searchUser = uroService.getOne(queryWrapperUro);
            boolean isAuthor;
            if (searchUser == null) {
                // 未查到对应的openalex id, 可能是openalex没收录或不是认证学者
                // 根据认证来源判断一定是没认证
                isAuthor = false;
            } else {
                isAuthor = true;
            }
            return getSuccessResponse(new UserInform(aemail, anick_name, aperson_description, asex, isAuthor));
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
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
            QueryWrapper<UseridRelatedOpenalexid> queryWrapperUro = new QueryWrapper<>();
            queryWrapperUro.eq("user_id", userid);
            UseridRelatedOpenalexid searchUser = uroService.getOne(queryWrapperUro);
            boolean isAuthor;
            if (searchUser == null) {
                // 未查到对应的openalex id, 可能是openalex没收录或不是认证学者
                // 根据认证来源判断一定是没认证
                isAuthor = false;
            } else {
                isAuthor = true;
            }
            return getSuccessResponse(new UserInform(checkUser.getEmail(), checkUser.getNickName(), checkUser.getPersonDescription(), checkUser.getSex(), isAuthor));
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    public Response changePassword(@RequestHeader(value = "Authorization") String token, @RequestBody JSONObject passwdInform) {
        String oldPw = passwdInform.getString("oldPw");
        String newPw = passwdInform.getString("newPw");
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
            } else {
                log.info("密码错误");
//                jsonObject.put("code", 206);
//                jsonObject.put("message", "密码错误");
//                String json = jsonObject.toJSONString();
                return getErrorResponse(null, ErrorType.wrong_pwd);
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/getScholarInfoSelf", method = RequestMethod.GET)
    public Response getScholarInfoSelf(@RequestHeader(value = "Authorization") String token) {
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
                return getErrorResponse(null, ErrorType.not_scholar);
            } else {
                ScholarInform scholarInform = openAlexService.getAuthorSingle(searchUser.getOpenalexid());
                User user = userService.getById(user_id);
                scholarInform.setEmail(user.getEmail());
                scholarInform.setIntroduction(user.getPersonDescription());
                return getSuccessResponse(scholarInform);
            }
        } else if (user_id == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (user_id == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/getScholarInfo", method = RequestMethod.GET)
    public Response getScholarInfo(int user_id) {
        // 查关联表
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id);
        UseridRelatedOpenalexid searchUser = uroService.getOne(queryWrapper);
        if (searchUser == null) {
            // 未查到对应的openalex id, 可能是openalex没收录或不是认证学者
            // 根据认证来源判断一定是没认证
            return getErrorResponse(null, ErrorType.not_scholar);
        } else {
            ScholarInform scholarInform = openAlexService.getAuthorSingle(searchUser.getOpenalexid());
            User user = userService.getById(user_id);
            scholarInform.setEmail(user.getEmail());
            scholarInform.setIntroduction(user.getPersonDescription());
            return getSuccessResponse(scholarInform);
        }
    }

    @RequestMapping(value = "/disregister", method = RequestMethod.POST)
    public Response disRegister(@RequestHeader(value = "Authorization") String token) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", userid);
            userService.remove(queryWrapper);
            getSuccessResponse(null);
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/collectThesis", method = RequestMethod.POST)
    public Response collectThesis(@RequestHeader(value = "Authorization") String token, @RequestBody ThesisRequest thesisRequest) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                ThesisResult result = userId2PSTIdService.collectT(userid, thesisRequest);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getThesisList());
                }
                else if (result.getCode() == 1011){
                    return getErrorResponse(null, ErrorType.collect_T_duplicate);
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                log.info("    请求失败");
                log.error(String.valueOf(e));
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/collectPatent", method = RequestMethod.POST)
    public Response collectPatent(@RequestHeader(value = "Authorization") String token, @RequestBody RePatentRequest patentRequest) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                RePatentResult result = userId2PSPIdService.collectP(userid, patentRequest);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getPatentList());
                }
                else if (result.getCode() == 1013) {
                    return getErrorResponse(null, ErrorType.collect_P_duplicate);
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/deleteThesis", method = RequestMethod.POST)
    public Response deleteThesis(@RequestHeader(value = "Authorization") String token, @RequestBody ThesisRequest thesisRequest) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                ThesisResult result = userId2PSTIdService.deleteT(userid, thesisRequest);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getThesisList());
                }
                else if (result.getCode() == 1012) {
                    return getErrorResponse(null, ErrorType.collect_T_not_found);
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/deletePatent", method = RequestMethod.POST)
    public Response deletePatent(@RequestHeader(value = "Authorization") String token, @RequestBody RePatentRequest rePatentRequest) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                RePatentResult result = userId2PSPIdService.deleteP(userid, rePatentRequest);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getPatentList());
                }
                else if (result.getCode() == 1012) {
                    return getErrorResponse(null, ErrorType.collect_P_not_found);
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/selectCT", method = RequestMethod.GET)
    public Response selectCT(@RequestHeader(value = "Authorization") String token, String selectTName, String mod) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            SelectCollectMod sMod;
            switch (mod) {
                case "full": {
                    sMod = SelectCollectMod.contain_full;
                    break;
                }
                case "fragment": {
                    sMod = SelectCollectMod.contain_fragment;
                    break;
                }
                default: {
                    sMod = null;
                }
            }
            try {
                ThesisResult result = userId2PSTIdService.selectT(userid, selectTName, sMod);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getThesisList());
                }
                else if (result.getCode() == 1015) {
                    return getErrorResponse(null, ErrorType.select_T_not_found);
                }
                else if (result.getCode() == 1017) {
                    return getErrorResponse(null, ErrorType.select_mod_unknown);
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    // TODO: 搜索收藏专利
    @RequestMapping(value = "/selectCP", method = RequestMethod.GET)
    public Response selectCP(@RequestHeader(value = "Authorization") String token, String selectPName, String mod) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            SelectCollectMod sMod;
            switch (mod) {
                case "full": {
                    sMod = SelectCollectMod.contain_full;
                    break;
                }
                case "fragment": {
                    sMod = SelectCollectMod.contain_fragment;
                    break;
                }
                default: {
                    sMod = null;
                }
            }
            try {
                RePatentResult result = userId2PSPIdService.selectP(userid, selectPName, sMod);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getPatentList());
                }
                else if (result.getCode() == 1016) {
                    return getErrorResponse(null, ErrorType.select_P_not_found);
                }
                else if (result.getCode() == 1017) {
                    return getErrorResponse(null, ErrorType.select_mod_unknown);
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/checkcollcetT", method = RequestMethod.GET)
    public Response checkCollectThesis(@RequestHeader(value = "Authorization") String token, String thesisId) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                ThesisResult result = userId2PSTIdService.checkT(userid, thesisId);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getThesisList());
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    @RequestMapping(value = "/checkcollectP", method = RequestMethod.GET)
    public Response checkCollectPatent(@RequestHeader(value = "Authorization") String token, String rePatentId) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
//                log.info("    " + userid);
//                log.info("    " + rePatentId);
                RePatentResult result = userId2PSPIdService.checkP(userid, rePatentId);
                if (result.getCode() == 200) {
                    return getSuccessResponse(result.getPatentList());
                }
                else {
                    return getSimpleError();
                }
            }
            catch (Exception e) {
                return getSimpleError();
            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }
}
