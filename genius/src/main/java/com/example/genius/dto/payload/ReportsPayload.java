package com.example.genius.dto.payload;

import com.example.genius.dto.aggregation.PatentAggregation;

public class ReportsPayload {
    private ReportsPayload aggregations;
    private String order_field;
    private String order_direction;
    private Integer page;
    private Integer size;
    private String userId;
}
