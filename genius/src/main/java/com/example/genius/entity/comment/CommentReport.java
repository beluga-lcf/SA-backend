package com.example.genius.entity.comment;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer.comment_report")
public class CommentReport {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("reporter_name")
    private String reporterName;
    @TableField("reportee_comment")
    private String reporteeComment;
    @TableField("reportee_comment_id")
    private Integer reporteeCommentId;
    @TableField("reporter_id")
    private int reporterId;
    @TableField("ischeck")
    private int ischeck;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;
    @TableField("description")
    private String description;
    @TableField("reason")
    private String reason;
    public CommentReport(){

    }
}
