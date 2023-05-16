package com.example.musicserver.dao;

import com.example.musicserver.entity.UserData;
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
class UserDataMapperTest {

    @Autowired
    private UserDataMapper userDataMapper;

    static Snowflake snowflake = new Snowflake(7,1);

    @Test
    void test05() {
        //用户名和密码验证
        UserData userData = new UserData();
        userData.setPassword("111111");
        userData.setName("ccnice");
        int i = userDataMapper.verifyUserPassword(userData);
        System.out.println(i);
    }

    @Test
    void test04() {
        UserData userData = new UserData();
//        userData.setUserId("1");
        userData.setName("ccni");
//        userData.setPassword("11111");
        List<UserData> userDataList = userDataMapper.selectUserData(userData);
        System.out.println(userDataList);
    }

    @Test
    void test03() {
        //修改
        UserData userData = new UserData();
        userData.setUserId("957190288228356096");
        userData.setGender("男");
        userDataMapper.updateByPrimaryKey(userData);
    }

    @Test
    void test02() {
        //删除
        userDataMapper.deleteByPrimaryKey("957190533049880576");
    }

    @Test
    void test01() {
        UserData userData = new UserData();
        userData.setUserId(String.valueOf(snowflake.nextId()));
        userData.setName("x7");
        userData.setPassword("111111");
        userData.setGender("女");
        userData.setBirth(new Date().toString());
        userData.setLocation("China");
        userData.setIntroduction("啦啦啦");
        userData.setPhoto("不啦不啊了");
        userData.setCreatedTime(new Date().toString());
        userData.setUpdateTime(new Date().toString());

        userDataMapper.insert(userData);
    }
}