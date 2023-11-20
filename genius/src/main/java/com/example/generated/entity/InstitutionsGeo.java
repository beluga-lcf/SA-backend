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
@TableName("openalex.institutions_geo")
@ApiModel(value = "InstitutionsGeo对象", description = "")
public class InstitutionsGeo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String institutionId;

    private String city;

    private String geonamesCityId;

    private String region;

    private String countryCode;

    private String country;

    private Double latitude;

    private Double longitude;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGeonamesCityId() {
        return geonamesCityId;
    }

    public void setGeonamesCityId(String geonamesCityId) {
        this.geonamesCityId = geonamesCityId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "InstitutionsGeo{" +
            "institutionId = " + institutionId +
            ", city = " + city +
            ", geonamesCityId = " + geonamesCityId +
            ", region = " + region +
            ", countryCode = " + countryCode +
            ", country = " + country +
            ", latitude = " + latitude +
            ", longitude = " + longitude +
        "}";
    }
}
