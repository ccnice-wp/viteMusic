package com.example.musicserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengJianhong
 * @date 2023/3/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//歌曲表
public class Song {

    private String songId;      //歌曲id
    private String singerId;        //歌手ID
    private String singer;        //歌手名字
    private String name;        //歌曲名
    private String album;        //专辑名
    private String lyric;        //歌词
    private String url;         //歌曲地址
    private int photo;        //歌曲图片
    private String createdTime;        //创建时间
    private String updateTime;        //更新时间
}
