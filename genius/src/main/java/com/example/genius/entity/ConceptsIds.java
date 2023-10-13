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
@TableName("concepts_ids")
@ApiModel(value = "ConceptsIds对象", description = "")
public class ConceptsIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String conceptId;

    private String openalex;

    private String wikidata;

    private String wikipedia;

    private String umlsAui;

    private String umlsCui;

    private Long mag;

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    public String getOpenalex() {
        return openalex;
    }

    public void setOpenalex(String openalex) {
        this.openalex = openalex;
    }
    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }
    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }
    public String getUmlsAui() {
        return umlsAui;
    }

    public void setUmlsAui(String umlsAui) {
        this.umlsAui = umlsAui;
    }
    public String getUmlsCui() {
        return umlsCui;
    }

    public void setUmlsCui(String umlsCui) {
        this.umlsCui = umlsCui;
    }
    public Long getMag() {
        return mag;
    }

    public void setMag(Long mag) {
        this.mag = mag;
    }

    @Override
    public String toString() {
        return "ConceptsIds{" +
            "conceptId=" + conceptId +
            ", openalex=" + openalex +
            ", wikidata=" + wikidata +
            ", wikipedia=" + wikipedia +
            ", umlsAui=" + umlsAui +
            ", umlsCui=" + umlsCui +
            ", mag=" + mag +
        "}";
    }
}
