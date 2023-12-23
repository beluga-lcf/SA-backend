package com.example.genius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.genius.entity.HotField;
import com.example.genius.entity.HotSpot;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HotFieldMapper extends BaseMapper<HotField> {
    @Select("SELECT * FROM customer.hotfield ORDER BY hotnum DESC LIMIT 10")
    List<HotField> getTop10();
}
