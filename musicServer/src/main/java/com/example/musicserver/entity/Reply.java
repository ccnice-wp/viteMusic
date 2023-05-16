package com.example.musicserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengJianhong
 * @date 2023/3/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//评论回复
public class Reply {

    private String replyId;         //回复评论ID
    private String commentId;         //评论ID
    private String userId;         //回复用户ID
    private String content;         //回复内容
    private String createdTime;         //回复时间

}
