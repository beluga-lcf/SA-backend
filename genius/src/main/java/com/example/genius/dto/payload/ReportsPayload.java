package com.example.genius.dto.payload;

import com.example.genius.dto.aggregation.ReportsAggregation;
import lombok.Data;

@Data
public class ReportsPayload {
    private ReportsAggregation aggregations;
    private String order_field;
    private String order_direction;
    private Integer page;
    private Integer size;
    private String user_id;
}
