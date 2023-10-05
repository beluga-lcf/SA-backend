package com.example.genius.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 学者信息
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@ApiModel(value = "Scholar对象", description = "学者信息")
public class Scholar implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学者id")
    private Long scholarId;

    @ApiModelProperty("学者姓名")
    private String name;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("学位")
    private String degree;

    @ApiModelProperty("描述")
    private String description;

    public Long getScholarId() {
        return scholarId;
    }

    public void setScholarId(Long scholarId) {
        this.scholarId = scholarId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Scholar{" +
            "scholarId=" + scholarId +
            ", name=" + name +
            ", email=" + email +
            ", degree=" + degree +
            ", description=" + description +
        "}";
    }
}
