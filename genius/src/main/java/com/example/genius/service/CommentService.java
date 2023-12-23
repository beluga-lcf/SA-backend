package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.entity.comment.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getAllCommentsByWorkIdAndType(String workId,String type);//获取所有评论
    boolean createComment(Comment comment);
    boolean deleteComment(Integer commentId);
    Integer getLikeCount(Integer commentId);


}
