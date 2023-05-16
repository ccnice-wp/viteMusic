package com.example.musicserver.dao;

import com.example.musicserver.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author CCNICE
 * @Date 2023/3/25
 */
@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;
    @Test
    void test01() {
//        List<Comment> comments = commentMapper.commentOfSongId("000004");
//        System.out.println(comments);
    }

    @Test
    void test02() {
        Comment comment = new Comment();
        comment.setCommentId("000005");
        comment.setContent("000005");
        comment.setSongId("");
        comment.setSongListId("000003");
        comment.setUserId("000002");
        comment.setStars(100);
        comment.setType("1");
        comment.setCreatedTime(new Date().toString());

        commentMapper.insert(comment);

//        List<Comment> comments = commentMapper.commentOfSongListId("000003");
//        System.out.println(comments);
    }
}