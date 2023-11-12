package com.example.genius.entity.comment;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comment", schema = "customer")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Column
    private Timestamp time;

    @Column(name = "parent_comment_id")
    private Integer parentCommentId;

    @Column(name = "like_count")
    private Integer likeCount;
}

