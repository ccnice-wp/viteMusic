package com.example.musicserver.service;

import com.example.musicserver.entity.Song;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface SongService {

    boolean addSong(Song song);

    List<Song> selectSong(Song song);

    boolean updateSong(Song song);

    List<Song> selectSongOrderByPhoto();
}
