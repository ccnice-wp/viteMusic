package com.example.musicserver.dao;

import com.example.musicserver.entity.Admin;
import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    //查询全部admin信息
    List<Admin> queryAll();

    //增加admin信息
    int insert(Admin admin);

    //根据主键查询admin信息
    List<Admin> selectByPrimaryKey(String adminId);

    //根据主键更新admin信息
    int updateByPrimaryKey(Admin admin);

    //根据主键删除admin信息
    int deleteByPrimaryKey(String adminId);

    //验证密码
    int verifyPassword(String name, String password);

    //查询总注册用户与近一个月注册用户
    List<UserData> selectUserCount();

    //查询各个性别人数和未设置性别的人数
    List<UserData> selectGenderCounter();

    //查询前十的歌曲播放数量
    List<Song> selectPlayNum();
}
