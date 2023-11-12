package com.example.genius.entity.comment;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentLikesId implements Serializable {
    private Integer commentId;
    private Integer userId;
}
