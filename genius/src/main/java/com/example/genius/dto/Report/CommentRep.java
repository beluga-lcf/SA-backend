package com.example.genius.dto.Report;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentRep {

    private Integer id;
    private String reporterName;
    private String reporteeComment;
    private Integer reporteeCommentId;
    private int reporter_id;
    private int ischeck;
    private LocalDateTime time;
    private String description;
    private String reason;
    public CommentRep(int id,String reporterName,String reporteeComment,int reporteeCommentId,int reporter_id,int ischeck,LocalDateTime time,String description,String reason) {
        this.id = id;
        this.reporterName = reporterName;
        this.reporteeComment = reporteeComment;
        this.reporteeCommentId = reporteeCommentId;
        this.reporter_id = reporter_id;
        this.ischeck = ischeck;
        this.time = time;
        this.description = description;
        this.reason = reason;
    }
}
