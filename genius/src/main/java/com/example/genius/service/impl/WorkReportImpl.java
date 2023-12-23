package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.UseridRelatedOpenalexid;
import com.example.genius.entity.WorkReport;
import com.example.genius.mapper.UseridRelatedOpenalexidMapper;
import com.example.genius.mapper.WorkReportMapper;
import com.example.genius.service.UseridRelatedOpenalexService;
import com.example.genius.service.WorkReportService;
import org.springframework.stereotype.Service;

@Service
public class WorkReportImpl extends ServiceImpl<WorkReportMapper, WorkReport> implements WorkReportService {
}
