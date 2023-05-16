package com.example.musicserver.dao;

import com.example.musicserver.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface ReplyMapper {

    //增加回复评论
    int insert(Reply reply);

    //根据主键删除评论
    int deleteByPrimaryKey(String replyId);

    //根据评论ID查询回复评论
    List<Reply> selectReplyByCommentId(String commentId);
}
