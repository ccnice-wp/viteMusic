package com.example.musicserver.dao;

import com.example.musicserver.entity.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface SongListMapper {

    //增加歌单信息
    int insert(SongList songList);

    //删除歌单信息
    int deleteByPrimaryKey(String songListId);

    //更新歌单信息
    int updateByPrimaryKey(SongList songList);

    //查找歌单信息
    List<SongList> selectSongList(SongList songList);
}
