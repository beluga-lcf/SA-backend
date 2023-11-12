package com.example.generated.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Authors对象", description = "")
public class Authors implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String orcid;

    private String displayName;

    private Object displayNameAlternatives;

    private Integer worksCount;

    private Integer citedByCount;

    private String lastKnownInstitution;

    private String worksApiUrl;

    private LocalDateTime updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getDisplayNameAlternatives() {
        return displayNameAlternatives;
    }

    public void setDisplayNameAlternatives(Object displayNameAlternatives) {
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

    public String getLastKnownInstitution() {
        return lastKnownInstitution;
    }

    public void setLastKnownInstitution(String lastKnownInstitution) {
        this.lastKnownInstitution = lastKnownInstitution;
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
        return "Authors{" +
            "id = " + id +
            ", orcid = " + orcid +
            ", displayName = " + displayName +
            ", displayNameAlternatives = " + displayNameAlternatives +
            ", worksCount = " + worksCount +
            ", citedByCount = " + citedByCount +
            ", lastKnownInstitution = " + lastKnownInstitution +
            ", worksApiUrl = " + worksApiUrl +
            ", updatedDate = " + updatedDate +
        "}";
    }
}
