package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.entity.HotSpot;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotSpotService extends IService<HotSpot> {
    List<HotSpot> getTop10();
}
