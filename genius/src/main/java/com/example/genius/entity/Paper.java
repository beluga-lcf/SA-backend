package com.example.genius.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 论文
 * </p>
 *
 * @author chaofan
 * @since 2023-10-06
 */
@ApiModel(value = "Paper对象", description = "论文")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("论文ID")
    private Long paperId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("发布日期")
    private LocalDateTime publicationDate;

    @ApiModelProperty("关键词")
    private String keywords;

    @ApiModelProperty("引用信息")
    private String citations;

    @ApiModelProperty("逻辑删除")
    private Integer isDeleted;

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getCitations() {
        return citations;
    }

    public void setCitations(String citations) {
        this.citations = citations;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Paper{" +
            "paperId=" + paperId +
            ", title=" + title +
            ", authorId=" + authorId +
            ", publicationDate=" + publicationDate +
            ", keywords=" + keywords +
            ", citations=" + citations +
            ", isDeleted=" + isDeleted +
        "}";
    }
}
