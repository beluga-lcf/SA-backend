package com.example.genius.dto.payload;

import com.example.genius.dto.aggregation.BulletinsAggregation;
import lombok.Data;

@Data
public class BulletinsPayload {
    private BulletinsAggregation aggregations;
    private String order_field;
    private String order_direction;
    private Integer page;
    private Integer size;
    private String user_id;
}
