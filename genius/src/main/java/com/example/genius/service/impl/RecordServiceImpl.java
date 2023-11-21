package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.Record;
import com.example.genius.mapper.RecordMapper;
import com.example.genius.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
}
