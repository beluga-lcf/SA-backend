package com.example.genius.dto.payload;

import com.example.genius.dto.aggregation.ArticleAggregation;
import lombok.Data;

@Data
public class ArticlesPayload {
    private ArticleAggregation aggregations;
    private String order_field;
    private String order_direction;
    private Integer page;
    private Integer size;
    private String userId;
}
