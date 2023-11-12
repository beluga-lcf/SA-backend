package com.example.genius.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorOfWork {
    public boolean isAccessable;
    public String authorId;
    public String authorName;
}
