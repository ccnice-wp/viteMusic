package com.example.musicserver.dao;

import com.example.musicserver.entity.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface SingerMapper {

    //增加歌手信息
    int insert(Singer singer);

    //根据主键更新歌手信息
    int updateSingerByPrimaryKey(Singer singer);

    //根据主键删除歌手信息
    int deleteSingerByPrimaryKey(String singerId);

    //根据主键查询歌手信息
    List<Singer> selectSinger(Singer singer);



}
