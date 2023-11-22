package com.example.genius.dto.aggregation;

import lombok.Data;

@Data
public class ArticlesAggregation {
    String type ;
    String subject ;
    String year ;
    String source ;
    String license;
    String lang ;
    String institution ;
    String funding ;
    String collection ;
}
