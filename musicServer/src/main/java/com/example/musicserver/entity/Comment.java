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

//歌曲评论
public class Comment {

    private String commentId;           //评论ID
    private String userId;           //用户ID
    private String songId;           //歌曲ID
    private String songListId;      //歌单ID
    private String type;            //区分是歌单评论(1)还是歌曲评论(0)
    private Integer stars;          //评论点赞数
    private String content;           //评论内容
    private String createdTime;           //评论时间

}
