package com.example.genius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.genius.entity.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
