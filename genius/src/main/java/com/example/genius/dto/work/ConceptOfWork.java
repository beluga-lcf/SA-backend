package com.example.genius.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConceptOfWork {
    public boolean isAccessable;
    public String conceptId;
    public String conceptName;
}