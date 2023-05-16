package com.example.musicserver.dao;

import com.example.musicserver.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.downgoon.snowflake.Snowflake;


import java.util.Date;
import java.util.List;


/**
 * @Author CCNICE
 * @Date 2023/3/25
 */
@SpringBootTest
class ReplyMapperTest {

    @Autowired
    private ReplyMapper replyMapper;

    static Snowflake snowflake = new Snowflake(10,21);

    @Test
    void test02() {

        System.out.println(snowflake.nextId());

    }

    @Test
    void test01() {
        Reply reply = new Reply();
        reply.setReplyId(String.valueOf(snowflake.nextId()));
        reply.setUserId("000002");
        reply.setCommentId("000003");
        reply.setContent("test01");
        reply.setCreatedTime(new Date().toString());

        replyMapper.insert(reply);
        List<Reply> replies = replyMapper.selectReplyByCommentId("000003");
        System.out.println(replies);

    }

    @Test
    void test03() {
        replyMapper.deleteByPrimaryKey("000001");
    }
}