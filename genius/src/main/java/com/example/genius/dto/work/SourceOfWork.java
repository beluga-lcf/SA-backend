package com.example.genius.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SourceOfWork {
    public boolean isAccessable;
    public String soureId;
    public String sourceName;
}
