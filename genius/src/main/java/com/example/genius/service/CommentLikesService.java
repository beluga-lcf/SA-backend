package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.entity.comment.CommentLikes;

public interface CommentLikesService extends IService<CommentLikes> {
    boolean likeComment(Integer commentId, Integer userId);
    boolean unlikeComment(Integer commentId, Integer userId);

}
