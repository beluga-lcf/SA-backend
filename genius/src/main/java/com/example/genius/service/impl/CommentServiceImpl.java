package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.comment.Comment;
import com.example.genius.mapper.CommentMapper;
import com.example.genius.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public List<Comment> getAllCommentsByWorkId(String workId) {
        //获取所有评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_id", workId);
        return list(queryWrapper);
    }
    @Override
    public boolean createComment(Comment comment) {
        return save(comment);
    }
    @Override
    public boolean deleteComment(Integer commentId) {
        return removeById(commentId);
    }

    @Override
    public Integer getLikeCount(Integer commentId) {
        Comment comment = getById(commentId);
        return comment != null ? comment.getLikeCount() : 0;
    }

}
