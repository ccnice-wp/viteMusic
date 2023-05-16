package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.UserDataMapper;
import com.example.musicserver.entity.UserData;
import com.example.musicserver.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserDataMapper userDataMapper;

    static Snowflake snowflake = new Snowflake(1,7);


    @Override
    public List<UserData> getUserDataList(UserData userData) {
        return userDataMapper.selectUserData(userData);
    }

    @Override
    public boolean addUserData(UserData userData) {
        return userDataMapper.insert(userData) > 0;
    }

    @Override
    public boolean verifyUserName(UserData userData) {
        return userDataMapper.verifyUserName(userData) > 0;
    }

    @Override
    public boolean updateUserData(UserData userData) {
        return userDataMapper.updateByPrimaryKey(userData) > 0;
    }

    //验证用户名和密码
    @Override
    public boolean verifyUserPassword(UserData userData) {
        return userDataMapper.verifyUserPassword(userData) > 0;
    }
}
