package com.example.musicserver.service;

import com.example.musicserver.entity.Reply;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface ReplyService {

    boolean replyAdd(Reply reply);

    boolean deleteReplyByPrimaryKey(String replyId);

    List<Reply> selectReplyList(String commentId);

}
