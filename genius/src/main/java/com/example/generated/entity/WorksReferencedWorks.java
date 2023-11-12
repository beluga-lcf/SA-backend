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
@TableName("openalex.works_referenced_works")
@ApiModel(value = "WorksReferencedWorks对象", description = "")
public class WorksReferencedWorks implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String referencedWorkId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getReferencedWorkId() {
        return referencedWorkId;
    }

    public void setReferencedWorkId(String referencedWorkId) {
        this.referencedWorkId = referencedWorkId;
    }

    @Override
    public String toString() {
        return "WorksReferencedWorks{" +
            "workId = " + workId +
            ", referencedWorkId = " + referencedWorkId +
        "}";
    }
}
