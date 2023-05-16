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
//歌单列表
public class SongList {

    private String songListId;              //歌单ID
    private String songIdList;              //歌曲ID列表
    private String name;              //歌单名
    private String photo;              //歌单封面
    private String introduction;              //歌单介绍
    private String style;              //歌单风格
    private String createdTime;              //创建时间
}
