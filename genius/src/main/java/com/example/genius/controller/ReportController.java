package com.example.genius.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.genius.dto.Report.CommentRep;
import com.example.genius.dto.Report.ReportRate;
import com.example.genius.dto.Report.WorkRepRet;
import com.example.genius.dto.userPackage.LoginInfo;
import com.example.genius.entity.*;
import com.example.genius.entity.comment.Comment;
import com.example.genius.entity.comment.CommentReport;
import com.example.genius.enums.ErrorType;
import com.example.genius.service.*;
import com.example.genius.service.impl.UseridRelatedOpenalexidServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private AdminService adminService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentReportService commentReportService;
    @Autowired
    private UseridRelatedOpenalexService uroService;
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
            return getErrorResponse(null,ErrorType.illegal_work);
        }
        if(isAgree<1||isAgree>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        a.setIscheck(isAgree);
        workReportService.saveOrUpdate(a);
        return getSuccessResponse("审批成功！");
    }
    @RequestMapping(value = "/searchWorkById",method = RequestMethod.GET)
    public Response searchWorkById(int id){
        QueryWrapper<WorkReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<WorkReport> list = workReportService.list(queryWrapper);
        ArrayList<WorkRepRet> list1 = new ArrayList<WorkRepRet>();
        for(WorkReport u : list){
            list1.add(new WorkRepRet(u.getId(),u.getReporterName(),u.getReporter_id(),u.getReporteeWork(),u.getDescription(),u.getTime(), u.getIscheck()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/filterWork",method = RequestMethod.GET)
    public Response searchFilterWork(String substring){
        List<WorkReport> list = workReportService.list();
        ArrayList<WorkRepRet> list1 = new ArrayList<WorkRepRet>();
        for(WorkReport u : list){
            if(u.getReporterName().contains(substring)){
                list1.add(new WorkRepRet(u.getId(),u.getReporterName(),u.getReporter_id(),u.getReporteeWork(),u.getDescription(),u.getTime(),u.getIscheck()));
            }
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/getWorkBystatus",method = RequestMethod.GET)
    public Response getWorkByStatus(int status){
        if(status<1||status>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        QueryWrapper<WorkReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ischeck", status);
        List<WorkReport> list = workReportService.list(queryWrapper);
        ArrayList<WorkRepRet> list1 = new ArrayList<WorkRepRet>();
        for(WorkReport u : list){
            list1.add(new WorkRepRet(u.getId(),u.getReporterName(),u.getReporter_id(),u.getReporteeWork(),u.getDescription(),u.getTime(),u.getIscheck()));
        }
        return getSuccessResponse(list1);
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

    @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
    private Response adminLogin(String userName, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        Admin checkAdmin = adminService.getOne(queryWrapper);
        if (checkAdmin == null) {
            return getErrorResponse(null, ErrorType.no_such_admin);
        }
        if (!checkAdmin.getPassword().equalsIgnoreCase(password)) {
            return getErrorResponse(null, ErrorType.wrong_pwd);
        } else {
            String token = contextLoads(checkAdmin.getUserName());
            session.setAttribute("username", checkAdmin.getUserName());
            log.info("管理员" +checkAdmin.getUserName() + "已登录");
            LoginInfo loginInfo = new LoginInfo(checkAdmin.getUserName(), token);
            return getSuccessResponse(loginInfo);
        }
    }
    @RequestMapping(value = "/ReportComment",method = RequestMethod.GET)
    private Response reportComment(int commentID,String description,String reason,@RequestHeader(value = "Authorization") String token){
        int id = getIdByJwt(token);
        if(id<0){
            return getErrorResponse(ErrorType.login_timeout);
        }
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",commentID);
        Comment comment = commentService.getOne(queryWrapper);
        if (comment==null){
            return getErrorResponse(ErrorType.illegal_comment);
        }
        WorkReport workReport = new WorkReport();
        workReport.setReporter_id(id);
        String name;
        if((name = getUserNameByUserId(id))==null){
            return getErrorResponse(ErrorType.log_off_not_found);
        }
        CommentReport commentReport = new CommentReport();
        commentReport.setDescription(description);
        commentReport.setReporteeComment(comment.getContent());
        commentReport.setReporteeCommentId(commentID);
        commentReport.setReason(reason);
        commentReport.setIscheck(1);
        commentReport.setReporterName(name);
        commentReport.setReporter_id(id);
        commentReportService.save(commentReport);
        return getSuccessResponse("举报成功！");
    }
    @RequestMapping(value = "/displayComment",method = RequestMethod.GET)
    private Response displayComment(){
        List<CommentReport> list = commentReportService.list();
        ArrayList<CommentRep> list1 = new ArrayList<CommentRep>();
        for(CommentReport u : list){
            list1.add(new CommentRep(u.getId(),u.getReporterName(),u.getReporteeComment(),u.getReporteeCommentId(),u.getReporter_id(),u.getIscheck(),u.getTime(),u.getDescription(),u.getReason()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/commentReportHandle",method = RequestMethod.GET)
    private Response commentReportHandle(int id, int isAgree){
        QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        CommentReport a = commentReportService.getOne(queryWrapper);
        if(a == null){
            return getErrorResponse(null,ErrorType.illegal_comment);
        }
        if(isAgree<1||isAgree>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        a.setIscheck(isAgree);
        commentReportService.saveOrUpdate(a);
        return getSuccessResponse("审批成功！");
    }
    @RequestMapping(value = "/searchCommentById",method = RequestMethod.GET)
    private Response searchCommentById(int id){
        QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<CommentReport> list = commentReportService.list(queryWrapper);
        ArrayList<CommentRep> list1 = new ArrayList<CommentRep>();
        for(CommentReport u : list){
            list1.add(new CommentRep(u.getId(),u.getReporterName(),u.getReporteeComment(),u.getReporteeCommentId(),u.getReporter_id(),u.getIscheck(),u.getTime(),u.getDescription(),u.getReason()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/filterComment",method = RequestMethod.GET)
    public Response searchFilterComment(String substring){
        List<CommentReport> list = commentReportService.list();
        ArrayList<CommentRep> list1 = new ArrayList<CommentRep>();
        for(CommentReport u : list){
            if(u.getReporterName().contains(substring)||u.getReporteeComment().contains(substring)||u.getReason().contains(substring)){
                list1.add(new CommentRep(u.getId(),u.getReporterName(),u.getReporteeComment(),u.getReporteeCommentId(),u.getReporter_id(),u.getIscheck(),u.getTime(),u.getDescription(),u.getReason()));
            }
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/getCommentBystatus",method = RequestMethod.GET)
    public Response getCommentByStatus(int status){
        if(status<1||status>4){
            return getErrorResponse(null,ErrorType.invalid_check);
        }
        QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ischeck", status);
        List<CommentReport> list = commentReportService.list(queryWrapper);
        ArrayList<CommentRep> list1 = new ArrayList<CommentRep>();
        for(CommentReport u : list){
            list1.add(new CommentRep(u.getId(),u.getReporterName(),u.getReporteeComment(),u.getReporteeCommentId(),u.getReporter_id(),u.getIscheck(),u.getTime(),u.getDescription(),u.getReason()));
        }
        return getSuccessResponse(list1);
    }
    @RequestMapping(value = "/getSuccessRate", method = RequestMethod.GET)
    public Response getSuccessRate(){
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ischeck", 3);
        QueryWrapper<UseridRelatedOpenalexid> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("ischeck", 4);
        QueryWrapper<WorkReport> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("ischeck", 3);
        QueryWrapper<WorkReport> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("ischeck", 4);
        QueryWrapper<CommentReport> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("ischeck", 3);
        QueryWrapper<CommentReport> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.eq("ischeck", 4);
        int TotalRelate = uroService.list().size();
        int SucRelate = uroService.list(queryWrapper1).size()+uroService.list(queryWrapper4).size();
        int TotalWorkReport = workReportService.list().size();
        int SucWorkReport = workReportService.list(queryWrapper2).size()+workReportService.list(queryWrapper5).size();
        int TotalCommentReport = commentReportService.list().size();
        int SucCommentReport = commentReportService.list(queryWrapper3).size()+commentReportService.list(queryWrapper6).size();
        ReportRate reportRate = new ReportRate(TotalRelate, SucRelate, TotalWorkReport,SucWorkReport,TotalCommentReport,SucCommentReport);
        return getSuccessResponse(reportRate);
    }

}
