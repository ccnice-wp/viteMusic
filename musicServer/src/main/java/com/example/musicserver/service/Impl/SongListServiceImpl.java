package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.SongListMapper;
import com.example.musicserver.entity.SongList;
import com.example.musicserver.service.SongListService;
import com.example.musicserver.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    static Snowflake snowflake = new Snowflake(1,5);

    @Override
    public List<SongList> getSongList(SongList songList) {
        return songListMapper.selectSongList(songList);
    }

    @Override
    public boolean addSongList(SongList songList) {
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public boolean updateSongListByPrimaryKey(SongList songList) {
        return songListMapper.updateByPrimaryKey(songList) > 0;
    }
}
