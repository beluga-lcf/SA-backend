package com.example.genius.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationOfWork {
    public boolean isAccessable;
    public String pdf_url;
}
