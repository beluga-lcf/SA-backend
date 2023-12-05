package com.example.genius.controller;

import com.example.genius.entity.comment.Comment;
import com.example.genius.service.CommentLikesService;
import com.example.genius.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentLikesService commentLikesService;

    @GetMapping("/getByWorkId")
    public List<Comment> getCommentsByWorkId(@RequestParam String workId) {
        return commentService.getAllCommentsByWorkId(workId);
    }

    @PostMapping("/create")
    public boolean createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/delete")
    public boolean deleteComment(@RequestParam Integer commentId) {
        return commentService.deleteComment(commentId);
    }

    @PostMapping("/like")
    public boolean likeComment(@RequestParam Integer commentId, @RequestParam Integer userId) {
        return commentLikesService.likeComment(commentId, userId);
    }

    @DeleteMapping("/unlike")
    public boolean unlikeComment(@RequestParam Integer commentId, @RequestParam Integer userId) {
        return commentLikesService.unlikeComment(commentId, userId);
    }
    @GetMapping("/likes")
    public Integer getCommentLikes(@RequestParam Integer commentId) {
        return commentService.getLikeCount(commentId);
    }


}
