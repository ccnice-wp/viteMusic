package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.UserSongListMapper;
import com.example.musicserver.entity.UserSongList;
import com.example.musicserver.service.UserSongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class UserSongListServiceImpl implements UserSongListService {

    @Autowired
    private UserSongListMapper userSongListMapper;

    static Snowflake snowflake = new Snowflake(1,8);


    @Override
    public boolean addUserSongList(UserSongList userSongList) {
        return userSongListMapper.insert(userSongList) > 0;
    }

    @Override
    public boolean deleteUserSongList(UserSongList userSongList) {
        return userSongListMapper.deleteByPrimaryKey(userSongList.getUserSongListId()) > 0;
    }

    @Override
    public boolean updateUserSongList(UserSongList userSongList) {
        return userSongListMapper.updateByPrimaryKey(userSongList) > 0;
    }

    @Override
    public List<UserSongList> selectUserSongList(UserSongList userSongList) {
        return userSongListMapper.selectUserSongList(userSongList);
    }
}
