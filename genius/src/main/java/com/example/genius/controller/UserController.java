package com.example.genius.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.userPackage.*;
import com.example.genius.dto.Approval.approvalRelateReturn;
import com.example.genius.dto.userPackage.OpenalexInform;
import com.example.genius.dto.userPackage.ScholarInform;
import com.example.genius.dto.userPackage.Token;
import com.example.genius.dto.userPackage.UserInform;
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
import com.example.genius.util.StringUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
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
            LoginInfo loginInfo = new LoginInfo(checkUser.getNickName(), token);
            return getSuccessResponse(loginInfo);
        }
    }

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.GET)
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
    public Response relateOpenalex(String openalexId,String text, @RequestHeader(value = "Authorization") String token){// 依据openalexID与Userid进行连接
        System.out.println("11");
        Integer userid = getIdByJwt(token);
        if(userid<=0){
            return getErrorResponse(null,ErrorType.not_login);
        }
        UseridRelatedOpenalexid a = new UseridRelatedOpenalexid();
        a.setText(text);
        a.setUserId(userid);
        a.setOpenalexid(openalexId);
        a.setIscheck(1);
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);
        List<UseridRelatedOpenalexid> userids =uroMapper.selectList(queryWrapper);
        if(!userids.isEmpty()){
            uroService.saveOrUpdate(a);
            return getSuccessResponse("学者认证申请已修改！");
        }
        else {
            uroService.save(a);
            log.info("There is a new expert!"+1);
            return getSuccessResponse("学者认证申请已发送！");
        }

    }
    @RequestMapping(value = "approvalRelate",method = RequestMethod.GET)
    public Response approvalRelate(){
        List<UseridRelatedOpenalexid> list = uroService.list();
        ArrayList<approvalRelateReturn> list1 = new ArrayList<approvalRelateReturn>();
        for(UseridRelatedOpenalexid u : list){
            String jsons = openAlexService.getAuthorNameByAuthorID(u.getOpenalexid());
            JSONObject json = JSONObject.parseObject(jsons);
            String resJson = json.getString("display_name");
            list1.add(new approvalRelateReturn(u.getUserId(), u.getOpenalexid(),resJson,u.getTime(),u.getIscheck(), u.getText()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/filterApproval", method = RequestMethod.GET)
    public Response FilterApproval(int status){
        if(status<1||status>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ischeck", status);
        List<UseridRelatedOpenalexid> list = uroService.list(queryWrapper);
        ArrayList<approvalRelateReturn> list1 = new ArrayList<approvalRelateReturn>();
        for(UseridRelatedOpenalexid u : list){
            String jsons =openAlexService.getAuthorNameByAuthorID(u.getOpenalexid());
            JSONObject json = JSONObject.parseObject(jsons);
            String resJson = json.getString("display_name");
            list1.add(new approvalRelateReturn(u.getUserId(), u.getOpenalexid(),resJson,u.getTime(),u.getIscheck(), u.getText())
            );
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/searchById",method = RequestMethod.GET)
    public Response searchApprovalById(int id){
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UseridRelatedOpenalexid> list = uroService.list(queryWrapper);
        ArrayList<approvalRelateReturn> list1 = new ArrayList<approvalRelateReturn>();
        for(UseridRelatedOpenalexid u : list){
            String jsons =openAlexService.getAuthorNameByAuthorID(u.getOpenalexid());
            JSONObject json = JSONObject.parseObject(jsons);
            String resJson = json.getString("display_name");
            list1.add(new approvalRelateReturn(u.getUserId(), u.getOpenalexid(),resJson,u.getTime(),u.getIscheck(),u.getText()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/searchApproval",method = RequestMethod.GET)
    public Response searchApproval(String substring){
        List<UseridRelatedOpenalexid> list = uroService.list();
        ArrayList<approvalRelateReturn> list1 = new ArrayList<approvalRelateReturn>();
        for(UseridRelatedOpenalexid u : list){
            String jsons =openAlexService.getAuthorNameByAuthorID(u.getOpenalexid());
            JSONObject json = JSONObject.parseObject(jsons);
            String resJson = json.getString("display_name");
            if(resJson.contains(substring)){
                list1.add(new approvalRelateReturn(u.getUserId(), u.getOpenalexid(),resJson,u.getTime(),u.getIscheck(),u.getText()));
            }
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/agreeRelate",method = RequestMethod.GET)
    public Response agreeRelate(int userid,int isAgree){

        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);
        UseridRelatedOpenalexid a = uroService.getOne(queryWrapper);
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid",userid);
        User u = userService.getOne(queryWrapper1);
        if(a == null){
            return getErrorResponse(null,ErrorType.no_relate);
        }
        if(isAgree<1||isAgree>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        a.setIscheck(isAgree);
        uroService.saveOrUpdate(a);
        if(isAgree == 3){
            String jsons = openAlexService.getAuthorNameByAuthorID(a.getOpenalexid());
            JSONObject json = JSONObject.parseObject(jsons);
            String resJson = json.getString("display_name");
            emailService.sendRelateEmail(u.getEmail(),u.getNickName(),resJson);
        }
        return getSuccessResponse("审批成功！");
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
        ArrayList<MyWorkDis> myWorkDisArrayList = new ArrayList<>();
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
            myWorkDisArrayList.add(myWorkDis);
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
        return getSuccessResponse(myWorkDisArrayList);
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
        ScholarInform scholarInform = new ScholarInform();
        if (searchUser == null) {
            // 未查到认领记录
            scholarInform.setIsClaimed(false);
            scholarInform.setEmail("暂无邮箱信息");
            scholarInform.setIntroduction("暂无学者简介");
        } else {
            openAlexService.getAuthorSingle(searchUser.getOpenalexid());
            scholarInform.setIsClaimed(true);
            User user = userService.getById(user_id);
            scholarInform.setEmail(user.getEmail());
            scholarInform.setIntroduction(user.getPersonDescription());
        }
        return getSuccessResponse(scholarInform);
    }

    @RequestMapping(value = "/getInfoByOpenID", method = RequestMethod.GET)
    public Response getInfoByOpenID(String openalexId){
        ScholarInform scholarInform = openAlexService.getAuthorSingle(openalexId);
        NewScholarInform newScholarInform = new NewScholarInform();
        newScholarInform.setClaimed(scholarInform.isClaimed);
        newScholarInform.setInterests(scholarInform.interests);
        newScholarInform.setAchievementsNum(scholarInform.achievementsNum);
        newScholarInform.setName(scholarInform.name);
        newScholarInform.setNames(scholarInform.names);
        newScholarInform.setOrganization(scholarInform.getOrganization());
        newScholarInform.setCitationsNum(scholarInform.citationsNum);
        newScholarInform.setEmail(scholarInform.getEmail());
        newScholarInform.setIntroduction(scholarInform.getIntroduction());
        newScholarInform.setMyWorkDisArrayList(scholarInform.myWorkDisArrayList);
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openalexid",openalexId);
        int isRelated = 0;
        List<UseridRelatedOpenalexid> arrayList = uroService.list(queryWrapper);
        for(UseridRelatedOpenalexid u : arrayList){
            if(u.getIscheck()==3){
                isRelated = u.getUserId();
                break;
            }
        }
        newScholarInform.setIsRelate(isRelated);
        return getSuccessResponse(newScholarInform);
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
                else if (result.getCode() == 1019) {
                    return getErrorResponse(null, ErrorType.select_T_not_found);
                }
                else if (result.getCode() == 1021) {
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
                else if (result.getCode() == 1020) {
                    return getErrorResponse(null, ErrorType.select_P_not_found);
                }
                else if (result.getCode() == 1021) {
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

    @RequestMapping(value = "/getCThesis", method = RequestMethod.GET)
    public Response getCThesis(@RequestHeader(value = "Authorization") String token) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                ThesisResult result = userId2PSTIdService.getT(userid);
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
    // TODO: 查询全部收藏专利
    @RequestMapping(value = "/getCPatent", method = RequestMethod.GET)
    public Response getCPatent(@RequestHeader(value = "Authorization") String token) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            try {
                RePatentResult result = userId2PSPIdService.getP(userid);
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

    @RequestMapping(value = "/logoff", method = RequestMethod.POST)
    public Response logOff(@RequestHeader(value = "Authorization") String token) {
        // jwt解出id
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            int result = userService.logOff(userid);
            if (result == 0) {
                return getSuccessResponse(null);
            }
            else if (result == -1) {
                // 删除用户不存在
                return getErrorResponse(null, ErrorType.log_off_not_found);
            }
            else if (result == -2) {
                // 删除失败
                return getErrorResponse(null, ErrorType.log_off_failed);
            }
            else {
                return getSimpleError();
            }
//
//            try {
//            }
//            catch (Exception e) {
//                System.out.println("数据库操作失败");
//                return getSimpleError();
//            }
        } else if (userid == -1) {
            return getErrorResponse(null, ErrorType.login_timeout);
        } else if (userid == -2) {
            return getErrorResponse(null, ErrorType.jwt_illegal);
        }
        return null;
    }

    // TODO: 论文名查询相关论文的作者id
    @RequestMapping(value = "/getauthorbypaper", method = RequestMethod.GET)
    public Response getAuthorByPaper(String userName, String paperName) {
        List<ScholarSimpleInform> authors = new ArrayList<>();
        System.out.println("建立List");
        List<String> scholarIds = openAlexService.getAuthoriIdByWorkname(paperName);
        for (String scholarId : scholarIds) {
            // 获得一个author
            ScholarSimpleInform scholarSInform = openAlexService.getAuthorSimpleSingle(scholarId, paperName);
            if (scholarSInform == null) {
                // 未知错误导致未查到
            }
            else {
                // 如果name可接受，加入users
                // 获取全部名字,包括name和names
                String[] names = scholarSInform.getNames();
                boolean acceptable = false;
                for (String name : names) {
                    String[] name_1 = name.split(" ");
                    String[] name_2 = userName.split(" ");
                    int matches = 0;
                    for (String str1 : name_1) {
                        for (String str2 : name_2) {
                            if (str1.contains(str2) || str2.contains(str1)) {
                                matches++;
                            }
                        }
                    }
                    acceptable = acceptable || (matches - 2.0 / 3 * name_1.length >= 0) && (matches - 2.0 / 3 * name_2.length >= 0);
                }
                if (acceptable) {
                    QueryWrapper<UseridRelatedOpenalexid> uroQuery = new QueryWrapper<>();
                    uroQuery.eq("openalexid", scholarId);
                    UseridRelatedOpenalexid uroUser = uroService.getOne(uroQuery);
                    if (uroUser == null) {
                        // 未认领
                        authors.add(scholarSInform);
                    }
                }
            }
        }
        // 处理authors，返回
        return getSuccessResponse(authors);
    }

    // TODO: 收藏论文和专利的个数
    @RequestMapping(value = "/getTsPs", method = RequestMethod.GET)
    public Response getTsPs(@RequestHeader(value = "Authorization") String token) {
        int userid = getIdByJwt(token);
        if (userid >= 0) {
            long numThesis = 0;
            long numPatent = 0;
            try {
                numThesis = userId2PSTIdService.getNum(userid);
                numPatent = userId2PSPIdService.getNum(userid);
                ThesePatentNum tpNum = new ThesePatentNum(numThesis, numPatent);
                System.out.println("成功" + tpNum.getNumPatent() + " " + tpNum.getNumThesis());
                return getSuccessResponse(tpNum);
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

    @RequestMapping(value = "/JwtTest", method = RequestMethod.GET)
    public Response jwtTest(HttpServletRequest request, int myInt) {
        int userid = (int) request.getAttribute("id");
        return getSuccessResponse(userid);
    }
}
