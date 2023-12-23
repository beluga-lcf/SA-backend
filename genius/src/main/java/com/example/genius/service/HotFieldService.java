package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.entity.HotField;
import com.example.genius.entity.HotSpot;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotFieldService extends IService<HotField> {
    List<HotField> getTop10();
}
