package com.example.genius.dto.payload;

import com.example.genius.dto.aggregation.ArticlesAggregation;
import lombok.Data;

@Data
public class ArticlesPayload {
    private ArticlesAggregation aggregations;
    private String order_field;
    private String order_direction;
    private Integer page;
    private Integer size;
    private String user_id;
    private String strategy;
}
