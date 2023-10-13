package com.example.genius.entity;

import java.io.Serializable;
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
@ApiModel(value = "Works对象", description = "")
public class Works implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String doi;

    private String title;

    private String displayName;

    private Integer publicationYear;

    private String publicationDate;

    private String type;

    private Integer citedByCount;

    private Boolean isRetracted;

    private Boolean isParatext;

    private String citedByApiUrl;

    private String abstractInvertedIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getCitedByCount() {
        return citedByCount;
    }

    public void setCitedByCount(Integer citedByCount) {
        this.citedByCount = citedByCount;
    }
    public Boolean getIsRetracted() {
        return isRetracted;
    }

    public void setIsRetracted(Boolean isRetracted) {
        this.isRetracted = isRetracted;
    }
    public Boolean getIsParatext() {
        return isParatext;
    }

    public void setIsParatext(Boolean isParatext) {
        this.isParatext = isParatext;
    }
    public String getCitedByApiUrl() {
        return citedByApiUrl;
    }

    public void setCitedByApiUrl(String citedByApiUrl) {
        this.citedByApiUrl = citedByApiUrl;
    }
    public String getAbstractInvertedIndex() {
        return abstractInvertedIndex;
    }

    public void setAbstractInvertedIndex(String abstractInvertedIndex) {
        this.abstractInvertedIndex = abstractInvertedIndex;
    }

    @Override
    public String toString() {
        return "Works{" +
            "id=" + id +
            ", doi=" + doi +
            ", title=" + title +
            ", displayName=" + displayName +
            ", publicationYear=" + publicationYear +
            ", publicationDate=" + publicationDate +
            ", type=" + type +
            ", citedByCount=" + citedByCount +
            ", isRetracted=" + isRetracted +
            ", isParatext=" + isParatext +
            ", citedByApiUrl=" + citedByApiUrl +
            ", abstractInvertedIndex=" + abstractInvertedIndex +
        "}";
    }
}
