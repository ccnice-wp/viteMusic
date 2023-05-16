package com.example.musicserver.dao;

import com.example.musicserver.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface CommentMapper {

    //根据主键删除评论
    int deletePrimaryKey(String commentId);

    //增加评论
    int insert(Comment comment);

    //根据主键查询评论
    List<Comment> selectByPrimaryKey(String commentId);


    //查询评论
    List<Comment> selectCommentList(Comment comment);
}
