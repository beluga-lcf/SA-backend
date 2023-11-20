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
@TableName("openalex.institutions_associated_institutions")
@ApiModel(value = "InstitutionsAssociatedInstitutions对象", description = "")
public class InstitutionsAssociatedInstitutions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String institutionId;

    private String associatedInstitutionId;

    private String relationship;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getAssociatedInstitutionId() {
        return associatedInstitutionId;
    }

    public void setAssociatedInstitutionId(String associatedInstitutionId) {
        this.associatedInstitutionId = associatedInstitutionId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "InstitutionsAssociatedInstitutions{" +
            "institutionId = " + institutionId +
            ", associatedInstitutionId = " + associatedInstitutionId +
            ", relationship = " + relationship +
        "}";
    }
}
