package com.example.genius.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@ApiModel(value = "User对象", description = "用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("0:女 1:男 2:未知")
    private Integer sex;

    @ApiModelProperty("个人描述")
    private String personDescription;

    @ApiModelProperty("注册时间")
    private LocalDateTime joinTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }
    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", nickName=" + nickName +
            ", email=" + email +
            ", password=" + password +
            ", sex=" + sex +
            ", personDescription=" + personDescription +
            ", joinTime=" + joinTime +
        "}";
    }
}
