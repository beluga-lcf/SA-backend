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
@ApiModel(value = "Institutions对象", description = "")
public class Institutions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String ror;

    private String displayName;

    private String countryCode;

    private String type;

    private String homepageUrl;

    private String imageUrl;

    private String imageThumbnailUrl;

    private String displayNameAcroynyms;

    private String displayNameAlternatives;

    private Integer worksCount;

    private Integer citedByCount;

    private String worksApiUrl;

    private LocalDateTime updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRor() {
        return ror;
    }

    public void setRor(String ror) {
        this.ror = ror;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }
    public String getDisplayNameAcroynyms() {
        return displayNameAcroynyms;
    }

    public void setDisplayNameAcroynyms(String displayNameAcroynyms) {
        this.displayNameAcroynyms = displayNameAcroynyms;
    }
    public String getDisplayNameAlternatives() {
        return displayNameAlternatives;
    }

    public void setDisplayNameAlternatives(String displayNameAlternatives) {
        this.displayNameAlternatives = displayNameAlternatives;
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
        return "Institutions{" +
            "id=" + id +
            ", ror=" + ror +
            ", displayName=" + displayName +
            ", countryCode=" + countryCode +
            ", type=" + type +
            ", homepageUrl=" + homepageUrl +
            ", imageUrl=" + imageUrl +
            ", imageThumbnailUrl=" + imageThumbnailUrl +
            ", displayNameAcroynyms=" + displayNameAcroynyms +
            ", displayNameAlternatives=" + displayNameAlternatives +
            ", worksCount=" + worksCount +
            ", citedByCount=" + citedByCount +
            ", worksApiUrl=" + worksApiUrl +
            ", updatedDate=" + updatedDate +
        "}";
    }
}
