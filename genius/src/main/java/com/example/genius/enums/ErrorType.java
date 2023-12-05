package com.example.genius.enums;

public enum ErrorType {
    already_registerd(1001, "用户已存在"),
    invalid_email(1002, "无效的邮箱"),
    wrong_pwd(1003, "用户名或密码错误"),
    not_login(1004,"未登录"),
    without_register(1005, "邮箱未注册"),
    login_timeout(1006, "登录过期，请重新登录"),
    jwt_illegal(1007, "非法令牌"),
    not_scholar(1008, "该用户不是认证学者"),
    already_send_email(1009, "已发送邮件"),
    wrong_captcha(1010, "验证码错误"),
    collect_T_duplicate(1011, "重复收藏论文"),
    collect_T_not_found(1012, "未收藏论文"),
    collect_P_duplicate(1013, "重复收藏专利"),
    collect_P_not_found(1014, "未收藏专利");

    public Integer code;
    public String message;

    ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
