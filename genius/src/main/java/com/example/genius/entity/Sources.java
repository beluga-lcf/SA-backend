package com.example.genius.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author chaofan
 * @since 2023-10-13
 */
@ApiModel(value = "Sources对象", description = "")
public class Sources implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String issnL;

    private String issn;

    private String displayName;

    private String publisher;

    private Integer worksCount;

    private Integer citedByCount;

    private Boolean isOa;

    private Boolean isInDoaj;

    private String homepageUrl;

    private String worksApiUrl;

    private LocalDateTime updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIssnL() {
        return issnL;
    }

    public void setIssnL(String issnL) {
        this.issnL = issnL;
    }
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public Integer getWorksCount() {
        return worksCount;
    }

    public void setWorksCount(Integer worksCount) {
        this.worksCount = worksCount;
    }
    public Integer getCitedByCount() {
        return citedByCount;
    }

    public void setCitedByCount(Integer citedByCount) {
        this.citedByCount = citedByCount;
    }
    public Boolean getIsOa() {
        return isOa;
    }

    public void setIsOa(Boolean isOa) {
        this.isOa = isOa;
    }
    public Boolean getIsInDoaj() {
        return isInDoaj;
    }

    public void setIsInDoaj(Boolean isInDoaj) {
        this.isInDoaj = isInDoaj;
    }
    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }
    public String getWorksApiUrl() {
        return worksApiUrl;
    }

    public void setWorksApiUrl(String worksApiUrl) {
        this.worksApiUrl = worksApiUrl;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Sources{" +
            "id=" + id +
            ", issnL=" + issnL +
            ", issn=" + issn +
            ", displayName=" + displayName +
            ", publisher=" + publisher +
            ", worksCount=" + worksCount +
            ", citedByCount=" + citedByCount +
            ", isOa=" + isOa +
            ", isInDoaj=" + isInDoaj +
            ", homepageUrl=" + homepageUrl +
            ", worksApiUrl=" + worksApiUrl +
            ", updatedDate=" + updatedDate +
        "}";
    }
}
