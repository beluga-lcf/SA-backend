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
@TableName("works_related_works")
@ApiModel(value = "WorksRelatedWorks对象", description = "")
public class WorksRelatedWorks implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String relatedWorkId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getRelatedWorkId() {
        return relatedWorkId;
    }

    public void setRelatedWorkId(String relatedWorkId) {
        this.relatedWorkId = relatedWorkId;
    }

    @Override
    public String toString() {
        return "WorksRelatedWorks{" +
            "workId=" + workId +
            ", relatedWorkId=" + relatedWorkId +
        "}";
    }
}
