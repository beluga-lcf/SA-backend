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
@ApiModel(value = "Publishers对象", description = "")
public class Publishers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String displayName;

    private String alternateTitles;

    private String countryCodes;

    private Integer hierarchyLevel;

    private String parentPublisher;

    private Integer worksCount;

    private Integer citedByCount;

    private String sourcesApiUrl;

    private LocalDateTime updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getAlternateTitles() {
        return alternateTitles;
    }

    public void setAlternateTitles(String alternateTitles) {
        this.alternateTitles = alternateTitles;
    }
    public String getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(String countryCodes) {
        this.countryCodes = countryCodes;
    }
    public Integer getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }
    public String getParentPublisher() {
        return parentPublisher;
    }

    public void setParentPublisher(String parentPublisher) {
        this.parentPublisher = parentPublisher;
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
    public String getSourcesApiUrl() {
        return sourcesApiUrl;
    }

    public void setSourcesApiUrl(String sourcesApiUrl) {
        this.sourcesApiUrl = sourcesApiUrl;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Publishers{" +
            "id=" + id +
            ", displayName=" + displayName +
            ", alternateTitles=" + alternateTitles +
            ", countryCodes=" + countryCodes +
            ", hierarchyLevel=" + hierarchyLevel +
            ", parentPublisher=" + parentPublisher +
            ", worksCount=" + worksCount +
            ", citedByCount=" + citedByCount +
            ", sourcesApiUrl=" + sourcesApiUrl +
            ", updatedDate=" + updatedDate +
        "}";
    }
}
