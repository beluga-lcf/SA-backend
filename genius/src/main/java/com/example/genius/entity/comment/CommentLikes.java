package com.example.genius.entity.comment;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@IdClass(CommentLikesId.class)
@Table(name = "comment_likes", schema = "customer")
public class CommentLikes {

    @Id
    @Column(name = "comment_id")
    private Integer commentId;

    @Id
    @Column(name = "user_id")
    private Integer userId;
}
