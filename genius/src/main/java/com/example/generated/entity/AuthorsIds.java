package com.example.generated.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author chaofan
 * @since 2023-11-11
 */
@TableName("authors_ids")
@ApiModel(value = "AuthorsIds对象", description = "")
public class AuthorsIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String authorId;

    private String openalex;

    private String orcid;

    private String scopus;

    private String twitter;

    private String wikipedia;

    private Long mag;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getOpenalex() {
        return openalex;
    }

    public void setOpenalex(String openalex) {
        this.openalex = openalex;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getScopus() {
        return scopus;
    }

    public void setScopus(String scopus) {
        this.scopus = scopus;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public Long getMag() {
        return mag;
    }

    public void setMag(Long mag) {
        this.mag = mag;
    }

    @Override
    public String toString() {
        return "AuthorsIds{" +
            "authorId = " + authorId +
            ", openalex = " + openalex +
            ", orcid = " + orcid +
            ", scopus = " + scopus +
            ", twitter = " + twitter +
            ", wikipedia = " + wikipedia +
            ", mag = " + mag +
        "}";
    }
}
