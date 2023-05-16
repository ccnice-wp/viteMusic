package com.example.musicserver.dao;

import com.example.musicserver.entity.UserSongList;
import org.assertj.core.annotations.Beta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@SpringBootTest
class UserSongListMapperTest {

    @Autowired
    private UserSongListMapper userSongListMapper;

    static  Snowflake snowflake = new Snowflake(8,1);

    @Test
    void test04() {
        UserSongList userSongList = new UserSongList();
        userSongList.setUserSongListId("95720215048024064");
        List<UserSongList> userSongLists = userSongListMapper.selectUserSongList(userSongList);
        System.out.println(userSongLists);
    }

    @Test
    void test03() {
        userSongListMapper.deleteByPrimaryKey("957210296937615360");
    }

    @Test
    void test02() {
        //更新用户歌单
        UserSongList userSongList = new UserSongList();
        userSongList.setUserSongListId("973556788312047616");
//        userSongList.setName("快乐");
//        userSongList.setPhoto("src");
        userSongList.setSongIdList("test");
        userSongListMapper.updateByPrimaryKey(userSongList);
    }

    @Test
    void test01() {

        UserSongList userSongList = new UserSongList();
        userSongList.setUserSongListId(String.valueOf(snowflake.nextId()));
        userSongList.setUserId(String.valueOf(snowflake.nextId()));
        userSongList.setSongIdList(String.valueOf(snowflake.nextId()));
        userSongList.setName("睡觉");
        userSongList.setPhoto("picture");
        userSongList.setIntroduction("hello");
        userSongList.setCreatedTime(new Date().toString());

        userSongListMapper.insert(userSongList);

    }
}