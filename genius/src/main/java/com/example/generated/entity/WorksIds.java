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
@TableName("works_ids")
@ApiModel(value = "WorksIds对象", description = "")
public class WorksIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String openalex;

    private String doi;

    private Long mag;

    private String pmid;

    private String pmcid;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getOpenalex() {
        return openalex;
    }

    public void setOpenalex(String openalex) {
        this.openalex = openalex;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Long getMag() {
        return mag;
    }

    public void setMag(Long mag) {
        this.mag = mag;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getPmcid() {
        return pmcid;
    }

    public void setPmcid(String pmcid) {
        this.pmcid = pmcid;
    }

    @Override
    public String toString() {
        return "WorksIds{" +
            "workId = " + workId +
            ", openalex = " + openalex +
            ", doi = " + doi +
            ", mag = " + mag +
            ", pmid = " + pmid +
            ", pmcid = " + pmcid +
        "}";
    }
}
