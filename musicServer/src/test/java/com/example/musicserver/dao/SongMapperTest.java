package com.example.musicserver.dao;

import com.example.musicserver.entity.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@SpringBootTest
class SongMapperTest {

    @Autowired
    private SongMapper songMapper;

    static  Snowflake snowflake = new Snowflake(6,1);

    @Test
    void test05() {
        List<Song> songs = songMapper.selectSongOrderByPhoto();
        for (Song song : songs) {
            System.out.println(song.getName());
        }
    }

    @Test
    void test04() {
        Song song = new Song();
        song.setName("辣");
        List<Song> songs = songMapper.selectSong(song);
        System.out.println(songs);
    }

    @Test
    void test03() {
        Song song = new Song();
        song.setSongId("957170141455192064");
        song.setName("辣椒");
        songMapper.updateByPrimaryKey(song);
    }

    @Test
    void test02() {
        songMapper.deleteByPrimaryKey("957166207302897664");
    }

    @Test
    void test01() {
        Song song = new Song();
        song.setSongId(String.valueOf(snowflake.nextId()));
        song.setName("黑夜");
        song.setSingerId(String.valueOf(snowflake.nextId()));
        song.setLyric("歌词");
        song.setUrl("歌曲地址");
        song.setAlbum("黑椒");
//        song.setPhoto("/234234234");
        song.setCreatedTime(new Date().toString());
        song.setUpdateTime(new Date().toString());
        songMapper.insert(song);
    }
}