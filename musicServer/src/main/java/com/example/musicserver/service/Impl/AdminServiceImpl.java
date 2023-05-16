package com.example.musicserver.service.Impl;

import com.example.musicserver.dao.AdminMapper;
import com.example.musicserver.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.downgoon.snowflake.Snowflake;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    static Snowflake snowflake = new Snowflake(1,1);

    @Override
    public boolean verifyAdminPassword(String name, String password) {
        return adminMapper.verifyPassword(name, password) > 0;
    }

}
