package com.example.genius.entity.storage;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sinstitutions")
public class SInstitutions {
    // simplified
    private String id;
    private String displayName;
    private String logo;
    private int citedByCount;
    private String concepts;
}
