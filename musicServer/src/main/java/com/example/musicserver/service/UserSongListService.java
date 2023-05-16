package com.example.musicserver.service;

import com.example.musicserver.entity.UserSongList;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface UserSongListService {

    boolean addUserSongList(UserSongList userSongList);

    boolean deleteUserSongList(UserSongList userSongList);

    boolean updateUserSongList(UserSongList userSongList);

    List<UserSongList> selectUserSongList(UserSongList userSongList);
}
