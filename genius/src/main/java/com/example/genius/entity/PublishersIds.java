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
@TableName("publishers_ids")
@ApiModel(value = "PublishersIds对象", description = "")
public class PublishersIds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String publisherId;

    private String openalex;

    private String ror;

    private String wikidata;

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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
    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    @Override
    public String toString() {
        return "PublishersIds{" +
            "publisherId=" + publisherId +
            ", openalex=" + openalex +
            ", ror=" + ror +
            ", wikidata=" + wikidata +
        "}";
    }
}
