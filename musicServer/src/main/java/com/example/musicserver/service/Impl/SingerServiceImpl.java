package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.SingerMapper;
import com.example.musicserver.entity.Singer;
import com.example.musicserver.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    static Snowflake snowflake = new Snowflake(1,4);

    //查找singer 信息
    @Override
    public List<Singer> getSingerList(Singer singer) {
        return singerMapper.selectSinger(singer);
    }

    //添加singer信息
    @Override
    public boolean addSinger(Singer singer) {
        return singerMapper.insert(singer) > 0;
    }

    //根据组件更新singer信息
    @Override
    public boolean updateSingerByPrimaryKey(Singer singer) {
        return singerMapper.updateSingerByPrimaryKey(singer) > 0;
    }
}
