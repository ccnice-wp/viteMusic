package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.CommentMapper;
import com.example.musicserver.entity.Comment;
import com.example.musicserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    static Snowflake snowflake = new Snowflake(1,2);

    @Override
    public boolean commentAdd(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public boolean deleteComment(String commentId) {
        return commentMapper.deletePrimaryKey(commentId) > 0;
    }

    @Override
    public List<Comment> selectCommentBySongId(Comment comment) {
        return commentMapper.selectCommentList(comment);
    }

    @Override
    public List<Comment> selectCommentBySongListId(Comment comment) {
        return commentMapper.selectCommentList(comment);
    }
}
