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
@TableName("concepts_related_concepts")
@ApiModel(value = "ConceptsRelatedConcepts对象", description = "")
public class ConceptsRelatedConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String conceptId;

    private String relatedConceptId;

    private Double score;

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getRelatedConceptId() {
        return relatedConceptId;
    }

    public void setRelatedConceptId(String relatedConceptId) {
        this.relatedConceptId = relatedConceptId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ConceptsRelatedConcepts{" +
            "conceptId = " + conceptId +
            ", relatedConceptId = " + relatedConceptId +
            ", score = " + score +
        "}";
    }
}
