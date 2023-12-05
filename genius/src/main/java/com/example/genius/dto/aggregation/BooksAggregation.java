package com.example.genius.dto.aggregation;

import lombok.Data;

@Data
public class BooksAggregation {
    private String publication_type;
    private String publisher;
    private String subject;
    private String year;
}
