package com.example.musicserver.service;

import com.example.musicserver.entity.Comment;
import com.example.musicserver.service.Impl.CommentServiceImpl;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface CommentService {

    boolean commentAdd(Comment comment);

    boolean deleteComment(String commentId);

    List<Comment> selectCommentBySongId(Comment comment);

    List<Comment> selectCommentBySongListId(Comment comment);
}
