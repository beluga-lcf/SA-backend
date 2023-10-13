package com.example.genius.entity;

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
 * @since 2023-10-13
 */
@TableName("works_open_access")
@ApiModel(value = "WorksOpenAccess对象", description = "")
public class WorksOpenAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private Boolean isOa;

    private String oaStatus;

    private String oaUrl;

    private Boolean anyRepositoryHasFulltext;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public Boolean getIsOa() {
        return isOa;
    }

    public void setIsOa(Boolean isOa) {
        this.isOa = isOa;
    }
    public String getOaStatus() {
        return oaStatus;
    }

    public void setOaStatus(String oaStatus) {
        this.oaStatus = oaStatus;
    }
    public String getOaUrl() {
        return oaUrl;
    }

    public void setOaUrl(String oaUrl) {
        this.oaUrl = oaUrl;
    }
    public Boolean getAnyRepositoryHasFulltext() {
        return anyRepositoryHasFulltext;
    }

    public void setAnyRepositoryHasFulltext(Boolean anyRepositoryHasFulltext) {
        this.anyRepositoryHasFulltext = anyRepositoryHasFulltext;
    }

    @Override
    public String toString() {
        return "WorksOpenAccess{" +
            "workId=" + workId +
            ", isOa=" + isOa +
            ", oaStatus=" + oaStatus +
            ", oaUrl=" + oaUrl +
            ", anyRepositoryHasFulltext=" + anyRepositoryHasFulltext +
        "}";
    }
}
