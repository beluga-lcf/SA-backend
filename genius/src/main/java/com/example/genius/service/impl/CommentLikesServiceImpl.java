package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.comment.CommentLikes;
import com.example.genius.mapper.CommentLikesMapper;
import com.example.genius.service.CommentLikesService;
import org.springframework.stereotype.Service;

@Service
public class CommentLikesServiceImpl extends ServiceImpl<CommentLikesMapper, CommentLikes> implements CommentLikesService {
    @Override
    public boolean likeComment(Integer commentId, Integer userId) {
        CommentLikes commentLikes = new CommentLikes();
        commentLikes.setCommentId(commentId);
        commentLikes.setUserId(userId);
        return save(commentLikes);
    }
    @Override
    public boolean unlikeComment(Integer commentId, Integer userId) {
        QueryWrapper<CommentLikes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId).eq("user_id", userId);
        return remove(queryWrapper);
    }

}
