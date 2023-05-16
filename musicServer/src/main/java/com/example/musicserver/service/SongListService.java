package com.example.musicserver.service;

import com.example.musicserver.entity.SongList;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface SongListService {

    //查询歌单
    List<SongList> getSongList(SongList songList);

    //添加歌单
    boolean addSongList(SongList songList);

    //更新歌单
    boolean updateSongListByPrimaryKey(SongList songList);
}
