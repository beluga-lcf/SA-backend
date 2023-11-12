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
@TableName("openalex.works_mesh")
@ApiModel(value = "WorksMesh对象", description = "")
public class WorksMesh implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String descriptorUi;

    private String descriptorName;

    private String qualifierUi;

    private String qualifierName;

    private Boolean isMajorTopic;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getDescriptorUi() {
        return descriptorUi;
    }

    public void setDescriptorUi(String descriptorUi) {
        this.descriptorUi = descriptorUi;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public String getQualifierUi() {
        return qualifierUi;
    }

    public void setQualifierUi(String qualifierUi) {
        this.qualifierUi = qualifierUi;
    }

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public Boolean getIsMajorTopic() {
        return isMajorTopic;
    }

    public void setIsMajorTopic(Boolean isMajorTopic) {
        this.isMajorTopic = isMajorTopic;
    }

    @Override
    public String toString() {
        return "WorksMesh{" +
            "workId = " + workId +
            ", descriptorUi = " + descriptorUi +
            ", descriptorName = " + descriptorName +
            ", qualifierUi = " + qualifierUi +
            ", qualifierName = " + qualifierName +
            ", isMajorTopic = " + isMajorTopic +
        "}";
    }
}
