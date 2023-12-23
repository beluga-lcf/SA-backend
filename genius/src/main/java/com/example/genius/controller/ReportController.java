package com.example.genius.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.Approval.approvalRelateReturn;
import com.example.genius.dto.Report.WorkRepRet;
import com.example.genius.entity.Response;
import com.example.genius.entity.User;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.entity.WorkReport;
import com.example.genius.enums.ErrorType;
import com.example.genius.service.UserService;
import com.example.genius.service.WorkReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import com.example.genius.controller.UserController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/report")
public class ReportController extends BaseController{
    @Autowired
    private HttpSession session;
    @Autowired
    private WorkReportService workReportService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/workReport", method = RequestMethod.GET)
    public Response workReport(String openalexID, String comment,@RequestHeader(value = "Authorization") String token){
        int id = getIdByJwt(token);
        if(id<0){
            return getErrorResponse(ErrorType.login_timeout);
        }
        WorkReport workReport = new WorkReport();
        workReport.setReporter_id(id);
        String name;
        if((name = getUserNameByUserId(id))==null){
            return getErrorResponse(ErrorType.log_off_not_found);
        }
        workReport.setReporterName(name);
        workReport.setReporteeWork(openalexID);
        workReport.setIscheck(1);
        workReport.setDescription(comment);
        workReportService.save(workReport);
        return getSuccessResponse("举报成功！");
    }
    @RequestMapping(value = "/workDisplay",method = RequestMethod.GET)
    public Response displayWork(){
        List<WorkReport> list = workReportService.list();
        ArrayList<WorkRepRet> list1 = new ArrayList<WorkRepRet>();
        for(WorkReport u : list){
            list1.add(new WorkRepRet(u.getId(),u.getReporterName(),u.getReporter_id(),u.getReporteeWork(),u.getDescription(),u.getTime(), u.getIscheck()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/workReportHandle",method = RequestMethod.GET)
    public Response HandleWorkreport(int id,int isAgree){
        QueryWrapper<WorkReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        WorkReport a = workReportService.getOne(queryWrapper);
        if(a == null){
            return getErrorResponse(null,ErrorType.no_relate);
        }
        if(isAgree<1||isAgree>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        a.setIscheck(isAgree);
        workReportService.saveOrUpdate(a);
        return getSuccessResponse("审批成功！");
    }
    private String getUserNameByUserId(int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        User check_user = userService.getOne(queryWrapper);
        if(check_user==null){
            return null;
        }else {
            return check_user.getNickName();
        }
    }
}
