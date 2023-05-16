package com.example.musicserver.service;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface AdminService {

    //验证admin用户和密码
    boolean verifyAdminPassword(String name, String password);
}
