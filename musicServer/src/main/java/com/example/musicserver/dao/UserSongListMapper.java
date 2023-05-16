package com.example.musicserver.dao;

import com.example.musicserver.entity.UserSongList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface UserSongListMapper {

    //创建用户歌单
    int insert(UserSongList userSongList);

    //删除用户歌单
    int deleteByPrimaryKey(String userSongListId);

    //更新用户歌单
    int updateByPrimaryKey(UserSongList userSongList);

    //查找用户歌单
    List<UserSongList> selectUserSongList(UserSongList userSongList);

}
