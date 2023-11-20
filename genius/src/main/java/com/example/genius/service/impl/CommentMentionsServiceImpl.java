package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.comment.CommentMentions;
import com.example.genius.mapper.CommentMentionsMapper;
import com.example.genius.service.CommentMentionsService;
import org.springframework.stereotype.Service;

@Service
public class CommentMentionsServiceImpl extends ServiceImpl<CommentMentionsMapper, CommentMentions> implements CommentMentionsService {
}
