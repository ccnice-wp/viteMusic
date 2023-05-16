package com.example.musicserver.dao;

import com.example.musicserver.entity.Singer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/25
 */
@SpringBootTest
class SingerMapperTest {

    @Autowired
    private SingerMapper singerMapper;


    static Snowflake snowflake =  new Snowflake(4,1);

    @Test
    void test04() {
        Singer singer = new Singer();

//        singer.setSingerId("956909715961942016");
        singer.setName("ccnice");

        List<Singer> singers = singerMapper.selectSinger(singer);
        System.out.println(singers);
    }

    @Test
    void test03() {
        //更新
        Singer singer = new Singer();
        singer.setSingerId("956909715961942016");
        singer.setBirth(new Date().toString());
        singer.setName("ccnice");
        singer.setGender("男");
        singer.setLocation("China");
        singer.setIntroduction("嘿嘿");
        singer.setPhoto("demo");
        singer.setCreatedTime(new Date().toString());
        singerMapper.updateSingerByPrimaryKey(singer);
    }

    @Test
    void test02() {
        singerMapper.deleteSingerByPrimaryKey("956909564404961280");
    }

    @Test
    void test01() {

        Singer singer = new Singer();
        singer.setSingerId(String.valueOf(snowflake.nextId()));
        singer.setBirth(new Date().toString());
        singer.setName("ccnice");
        singer.setGender("男");
        singer.setLocation("China");
        singer.setIntroduction("demo");
        singer.setPhoto("demo");
        singer.setCreatedTime(new Date().toString());

        singerMapper.insert(singer);
    }
}