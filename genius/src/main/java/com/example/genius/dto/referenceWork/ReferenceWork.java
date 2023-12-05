package com.example.genius.dto.referenceWork;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReferenceWork {
    public String workId;
    public String workName;
    public String sourceName;
    public String publicationYear;

}