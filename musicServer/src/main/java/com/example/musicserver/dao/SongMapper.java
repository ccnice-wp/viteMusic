package com.example.musicserver.dao;

import com.example.musicserver.entity.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/14
 */
@Mapper
@Repository
public interface SongMapper {

    //增加歌曲信息
    int insert(Song song);

    //删除歌曲信息
    int deleteByPrimaryKey(String songId);

    //更新歌曲信息
    int updateByPrimaryKey(Song song);

    //查询歌曲信息
    List<Song> selectSong(Song song);

    //查询歌曲播放排名根据播放次数
    List<Song> selectSongOrderByPhoto();
     
}
