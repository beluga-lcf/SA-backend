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
@TableName("institutions_ids")
@ApiModel(value = "InstitutionsIds对象", description = "")
public class InstitutionsIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String institutionId;

    private String openalex;

    private String ror;

    private String grid;

    private String wikipedia;

    private String wikidata;

    private Long mag;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }
    public String getOpenalex() {
        return openalex;
    }

    public void setOpenalex(String openalex) {
        this.openalex = openalex;
    }
    public String getRor() {
        return ror;
    }

    public void setRor(String ror) {
        this.ror = ror;
    }
    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }
    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }
    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }
    public Long getMag() {
        return mag;
    }

    public void setMag(Long mag) {
        this.mag = mag;
    }

    @Override
    public String toString() {
        return "InstitutionsIds{" +
            "institutionId=" + institutionId +
            ", openalex=" + openalex +
            ", ror=" + ror +
            ", grid=" + grid +
            ", wikipedia=" + wikipedia +
            ", wikidata=" + wikidata +
            ", mag=" + mag +
        "}";
    }
}
