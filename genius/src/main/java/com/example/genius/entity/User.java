package com.example.genius.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.genius.entity.Enum.SexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@Data
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

    @ApiModelProperty("0:未知 1:男 2:女")
    private SexEnum sex;

    @ApiModelProperty("个人描述")
    private String personDescription;

    @ApiModelProperty("注册时间")
    private LocalDateTime joinTime;

}
