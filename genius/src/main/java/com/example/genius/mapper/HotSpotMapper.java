package com.example.genius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.genius.entity.HotField;
import com.example.genius.entity.HotSpot;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HotSpotMapper extends BaseMapper<HotSpot> {
    @Select("SELECT * FROM customer.hotspot ORDER BY hotnum DESC LIMIT 10")
    List<HotSpot> getTop10();

}
