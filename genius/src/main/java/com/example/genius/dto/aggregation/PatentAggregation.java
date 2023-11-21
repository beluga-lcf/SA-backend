package com.example.genius.dto.aggregation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PatentAggregation {
    @JsonProperty("applicant_country")
    private String applicantCountry;

    @JsonProperty("apply_year")
    private String applyYear;

    @JsonProperty("cpc")
    private String cpc;

    @JsonProperty("grant_year")
    private String grantYear;

    @JsonProperty("ipc")
    private String ipc;

    @JsonProperty("issue_year")
    private String issueYear;

    @JsonProperty("priority_country")
    private String priorityCountry;

    @JsonProperty("priority_year")
    private String priorityYear;

    @JsonProperty("type")
    private String type;
}
