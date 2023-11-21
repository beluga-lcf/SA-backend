package com.example.genius.dto.aggregation;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ArticleAggregation{
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
