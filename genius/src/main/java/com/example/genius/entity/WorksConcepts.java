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
@TableName("works_concepts")
@ApiModel(value = "WorksConcepts对象", description = "")
public class WorksConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String conceptId;

    private Float score;

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
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "WorksConcepts{" +
            "workId=" + workId +
            ", conceptId=" + conceptId +
            ", score=" + score +
        "}";
    }
}
