package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.comment.CommentReport;
import com.example.genius.mapper.CommentReportMapper;
import com.example.genius.service.CommentReportService;
import org.springframework.stereotype.Service;

@Service
public class CommentReportImpl extends ServiceImpl<CommentReportMapper, CommentReport> implements CommentReportService {
}
