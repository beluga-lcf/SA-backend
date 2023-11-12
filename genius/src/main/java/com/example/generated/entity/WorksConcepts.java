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
@TableName("works_concepts")
@ApiModel(value = "WorksConcepts对象", description = "")
public class WorksConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String conceptId;

    private Double score;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "WorksConcepts{" +
            "workId = " + workId +
            ", conceptId = " + conceptId +
            ", score = " + score +
        "}";
    }
}
