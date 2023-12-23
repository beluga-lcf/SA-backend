package com.example.genius.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.HotField;
import com.example.genius.entity.HotSpot;
import com.example.genius.mapper.HotFieldMapper;
import com.example.genius.service.HotFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HotFieldServiceImpl extends ServiceImpl<HotFieldMapper, HotField> implements HotFieldService {

    @Autowired
    HotFieldMapper hotFieldMapper;

    @Override
    @Transactional
    public List<HotField> getTop10() {
        return hotFieldMapper.getTop10();
    }
}
