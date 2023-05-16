package com.example.musicserver.dao;

import com.example.musicserver.entity.SongList;
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
class SongListMapperTest {

    @Autowired
    private SongListMapper songListMapper;

    static Snowflake snowflake = new Snowflake(5,1);

    @Test
    void test04() {
        //更新
        SongList songList = new SongList();
        songList.setSongListId("957097728931401728");
        songList.setSongIdList(String.valueOf(snowflake.nextId()));
        songList.setName("嘿嘿");
        songList.setPhoto("aaaa");
        songList.setStyle("asasa");
        songList.setIntroduction("发生什么了");
        songListMapper.updateByPrimaryKey(songList);

    }

    @Test
    void test03() {
        //查询

        SongList songList = new SongList();
        songList.setSongListId("957097689395892224");
        List<SongList> songLists = songListMapper.selectSongList(songList);
        System.out.println(songLists);
    }

    @Test
    void test02() {
        songListMapper.deleteByPrimaryKey("957094778473418752");
    }

    @Test
    void test01() {
        SongList songList = new SongList();
        songList.setSongListId(String.valueOf(snowflake.nextId()));
        songList.setSongIdList(String.valueOf(snowflake.nextId()));
        songList.setName("demo");
        songList.setPhoto("picture");
        songList.setStyle("摇滚");
        songList.setIntroduction("啦啦啦啦啦");
        songList.setCreatedTime(new Date().toString());

        songListMapper.insert(songList);
    }
}