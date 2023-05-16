package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.SongMapper;
import com.example.musicserver.entity.Song;
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
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    static Snowflake snowflake = new Snowflake(1,6);

    @Override
    public List<Song> selectSongOrderByPhoto() {
        return songMapper.selectSongOrderByPhoto();
    }

    //添加歌曲
    @Override
    public boolean addSong(Song song) {
        return songMapper.insert(song) > 0;
    }

    //查询歌曲
    @Override
    public List<Song> selectSong(Song song) {
        return songMapper.selectSong(song);
    }

    //更新歌曲
    @Override
    public boolean updateSong(Song song) {
        return songMapper.updateByPrimaryKey(song) > 0;
    }
}
