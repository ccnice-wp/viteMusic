package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.ReplyMapper;
import com.example.musicserver.entity.Reply;
import com.example.musicserver.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    static Snowflake snowflake = new Snowflake(1,3);

    @Override
    public boolean replyAdd(Reply reply) {
        return replyMapper.insert(reply) > 0;
    }

    @Override
    public boolean deleteReplyByPrimaryKey(String replyId) {
        return replyMapper.deleteByPrimaryKey(replyId) > 0;
    }

    @Override
    public List<Reply> selectReplyList(String commentId) {
        return replyMapper.selectReplyByCommentId(commentId);
    }
}
