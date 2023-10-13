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
@TableName("concepts_related_concepts")
@ApiModel(value = "ConceptsRelatedConcepts对象", description = "")
public class ConceptsRelatedConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String conceptId;

    private String relatedConceptId;

    private Float score;

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
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ConceptsRelatedConcepts{" +
            "conceptId=" + conceptId +
            ", relatedConceptId=" + relatedConceptId +
            ", score=" + score +
        "}";
    }
}
