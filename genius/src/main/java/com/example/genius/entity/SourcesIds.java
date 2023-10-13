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
@TableName("sources_ids")
@ApiModel(value = "SourcesIds对象", description = "")
public class SourcesIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sourceId;

    private String openalex;

    private String issnL;

    private String issn;

    private Long mag;

    private String wikidata;

    private String fatcat;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public String getOpenalex() {
        return openalex;
    }

    public void setOpenalex(String openalex) {
        this.openalex = openalex;
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
    public Long getMag() {
        return mag;
    }

    public void setMag(Long mag) {
        this.mag = mag;
    }
    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }
    public String getFatcat() {
        return fatcat;
    }

    public void setFatcat(String fatcat) {
        this.fatcat = fatcat;
    }

    @Override
    public String toString() {
        return "SourcesIds{" +
            "sourceId=" + sourceId +
            ", openalex=" + openalex +
            ", issnL=" + issnL +
            ", issn=" + issn +
            ", mag=" + mag +
            ", wikidata=" + wikidata +
            ", fatcat=" + fatcat +
        "}";
    }
}
