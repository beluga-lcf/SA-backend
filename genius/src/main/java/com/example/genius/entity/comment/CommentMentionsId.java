package com.example.genius.entity.comment;

import lombok.Data;

import java.io.Serializable;
//CommentMentionsId 用于复合主键
@Data
public class CommentMentionsId implements Serializable {
    private Integer commentId;
    private Integer mentionedUserId;
}
