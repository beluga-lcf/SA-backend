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
@TableName("works_locations")
@ApiModel(value = "WorksLocations对象", description = "")
public class WorksLocations implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String sourceId;

    private String landingPageUrl;

    private String pdfUrl;

    private Boolean isOa;

    private String version;

    private String license;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public String getLandingPageUrl() {
        return landingPageUrl;
    }

    public void setLandingPageUrl(String landingPageUrl) {
        this.landingPageUrl = landingPageUrl;
    }
    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
    public Boolean getIsOa() {
        return isOa;
    }

    public void setIsOa(Boolean isOa) {
        this.isOa = isOa;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "WorksLocations{" +
            "workId=" + workId +
            ", sourceId=" + sourceId +
            ", landingPageUrl=" + landingPageUrl +
            ", pdfUrl=" + pdfUrl +
            ", isOa=" + isOa +
            ", version=" + version +
            ", license=" + license +
        "}";
    }
}
