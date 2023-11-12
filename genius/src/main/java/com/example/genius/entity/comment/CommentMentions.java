package com.example.genius.entity.comment;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@IdClass(CommentMentionsId.class)
@Table(name = "comment_mentions", schema = "customer")
public class CommentMentions {

    @Id
    @Column(name = "comment_id")
    private Integer commentId;

    @Id
    @Column(name = "mentioned_user_id")
    private Integer mentionedUserId;
}
