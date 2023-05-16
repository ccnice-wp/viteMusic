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
//用户歌单
public class UserSongList {

    private String userSongListId;          //用户歌单ID
    private String userId;          //用户ID
    private String songIdList;          //歌曲ID列表
    private String name;          //用户歌单名
    private String photo;          //用户歌单图片
    private String introduction;          //用户歌单介绍
    private String createdTime;          //创建时间

}
