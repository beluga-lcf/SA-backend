package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.HotSpot;
import com.example.genius.mapper.HotSpotMapper;
import com.example.genius.service.HotSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotSpotServiceImpl extends ServiceImpl<HotSpotMapper, HotSpot> implements HotSpotService {
    @Autowired
    HotSpotMapper hotSpotMapper;

    @Override
    @Transactional
    public List<HotSpot> getTop10() {
        return hotSpotMapper.getTop10();
    }
}
