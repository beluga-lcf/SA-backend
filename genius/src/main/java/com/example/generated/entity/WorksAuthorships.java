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
@TableName("openalex.works_authorships")
@ApiModel(value = "WorksAuthorships对象", description = "")
public class WorksAuthorships implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String authorPosition;

    private String authorId;

    private String institutionId;

    private String rawAffiliationString;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getAuthorPosition() {
        return authorPosition;
    }

    public void setAuthorPosition(String authorPosition) {
        this.authorPosition = authorPosition;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getRawAffiliationString() {
        return rawAffiliationString;
    }

    public void setRawAffiliationString(String rawAffiliationString) {
        this.rawAffiliationString = rawAffiliationString;
    }

    @Override
    public String toString() {
        return "WorksAuthorships{" +
            "workId = " + workId +
            ", authorPosition = " + authorPosition +
            ", authorId = " + authorId +
            ", institutionId = " + institutionId +
            ", rawAffiliationString = " + rawAffiliationString +
        "}";
    }
}
