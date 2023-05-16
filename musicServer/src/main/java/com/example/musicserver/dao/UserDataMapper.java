package com.example.musicserver.dao;

import com.example.musicserver.entity.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChengJianhong
 * @date 2023/3/17
 */
@Mapper
@Repository
public interface UserDataMapper {

    //增加用户
    int insert(UserData userData);

    //删除用户
    int deleteByPrimaryKey(String userId);

    //更新用户
    int updateByPrimaryKey(UserData userData);

    //查找用户
    List<UserData> selectUserData(UserData userData);

    //查看该用户名是否已被注册
    int verifyUserName(UserData userData);

    //验证用户名和密码
    int verifyUserPassword(UserData userData);



}
