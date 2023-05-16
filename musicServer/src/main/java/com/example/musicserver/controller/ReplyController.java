package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.Reply;
import com.example.musicserver.service.Impl.ReplyServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
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
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyService;

    static Snowflake snowflake = new Snowflake(1,3);

    //回复评论
    @RequestMapping(value = "/reply/add", method = RequestMethod.POST)
    public String replyAdd(Reply reply) {
        reply.setReplyId(String.valueOf(snowflake.nextId()));
        boolean b = replyService.replyAdd(reply);
        if (b) {
            return new SuccessMessage<Null>("回复评论成功！").getMessage();
        } else {
            return new ErrorMessage("回复评论失败！").getMessage();
        }
    }

    //删除回复评论
    @RequestMapping(value = "/reply/delete", method = RequestMethod.POST)
    public String replyDelete(String replyId) {
        boolean b = replyService.deleteReplyByPrimaryKey(replyId);
        if (b) {
            return  new SuccessMessage<Null>("删除成功！").getMessage();
        } else {
            return new ErrorMessage("删除失败！").getMessage();
        }
    }

    //根据评论Id插叙相应的回复评论
    @RequestMapping(value = "/reply/details", method = RequestMethod.GET)
    public String selectReplyByCommentId(String commentId) {
        List<Reply> replies = replyService.selectReplyList(commentId);
        return new SuccessMessage<List<Reply>>("回复评论",replies).getMessage();
    }

}
