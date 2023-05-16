package com.example.musicserver.service;

import com.example.musicserver.entity.Singer;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface SingerService {

    List<Singer> getSingerList(Singer singer);

    boolean addSinger(Singer singer);

    boolean updateSingerByPrimaryKey(Singer singer);

}
