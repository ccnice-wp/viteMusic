package com.example.musicserver;

import com.example.musicserver.dao.AdminMapper;
import com.example.musicserver.dao.CommentMapper;
import com.example.musicserver.entity.Admin;
import com.example.musicserver.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class MusicServerApplicationTests {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Test
    void test() {
        Object o = new Object();
    }

    @Test
    void adminTest() {

        Admin admin = new Admin();
        admin.setAdminId("00002");
        admin.setName("cc");
        admin.setPassword("111111");

        adminMapper.updateByPrimaryKey(admin);

//        adminMapper.deleteByPrimaryKey("00001");

        System.out.println(adminMapper.verifyPassword("cc","11111"));
    }

    @Test
    void commentTest() {

        Comment comment = new Comment();
        comment.setCommentId("000004");
        comment.setUserId("000002");
//        comment.setSongListId("");
        comment.setContent("你好你好");
        comment.setType("1");
        comment.setStars(3);
        comment.setSongId("000004");
        comment.setCreatedTime(new Date().toString());
        System.out.println(commentMapper.selectByPrimaryKey("000004"));

    }

}
