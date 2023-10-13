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
@TableName("concepts_ancestors")
@ApiModel(value = "ConceptsAncestors对象", description = "")
public class ConceptsAncestors implements Serializable {

    private static final long serialVersionUID = 1L;

    private String conceptId;

    private String ancestorId;

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    public String getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(String ancestorId) {
        this.ancestorId = ancestorId;
    }

    @Override
    public String toString() {
        return "ConceptsAncestors{" +
            "conceptId=" + conceptId +
            ", ancestorId=" + ancestorId +
        "}";
    }
}
