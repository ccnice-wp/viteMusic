package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.Comment;
import com.example.musicserver.service.Impl.CommentServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Controller
@ResponseBody
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    static Snowflake snowflake = new Snowflake(1,2);

    //增加歌曲或者歌单的评论
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    public String commentAdd(Comment comment) {
        comment.setCommentId(String.valueOf(snowflake.nextId()));
        boolean b = commentService.commentAdd(comment);
        if (b) {
            return new SuccessMessage<Null>("评论成功！").getMessage();
        } else {
            return new ErrorMessage("评论失败！").getMessage();
        }
    }

    //删除歌曲或者歌单的评论
    @RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
    public String deleteComment(String commentId) {
        boolean b = commentService.deleteComment(commentId);
        if (b) {
            return new SuccessMessage<Null>("评论删除成功！").getMessage();
        } else {
            return new ErrorMessage("评论删除失败！").getMessage();
        }
    }

    //获得指定歌曲的评论,前端应传入songId
    @RequestMapping(value = "/comment/song/details", method = RequestMethod.GET)
    public String selectSongComment(Comment comment) {
        List<Comment> comments = commentService.selectCommentBySongId(comment);
        return new SuccessMessage<List<Comment>>("歌曲评论",comments).getMessage();
    }

    //获得指定歌单的评论,前端应传入songListId
    @RequestMapping(value = "/comment/songList/details", method = RequestMethod.GET)
    public String selectSongListComment(Comment comment) {
        List<Comment> comments = commentService.selectCommentBySongId(comment);
        return new SuccessMessage<List<Comment>>("歌单评论",comments).getMessage();
    }

}
