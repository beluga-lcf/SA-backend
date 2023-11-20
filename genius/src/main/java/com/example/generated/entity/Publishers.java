package com.example.generated.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("openalex.publishers")
@ApiModel(value = "Publishers对象", description = "")
public class Publishers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String displayName;

    private Object alternateTitles;

    private Object countryCodes;

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

    public Object getAlternateTitles() {
        return alternateTitles;
    }

    public void setAlternateTitles(Object alternateTitles) {
        this.alternateTitles = alternateTitles;
    }

    public Object getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(Object countryCodes) {
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
            "id = " + id +
            ", displayName = " + displayName +
            ", alternateTitles = " + alternateTitles +
            ", countryCodes = " + countryCodes +
            ", hierarchyLevel = " + hierarchyLevel +
            ", parentPublisher = " + parentPublisher +
            ", worksCount = " + worksCount +
            ", citedByCount = " + citedByCount +
            ", sourcesApiUrl = " + sourcesApiUrl +
            ", updatedDate = " + updatedDate +
        "}";
    }
}
