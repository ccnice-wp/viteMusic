package com.example.musicserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChengJianhong
 * @date 2023/3/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//用户信息表
public class UserData {

    private String userId;          //用户ID
    private String name;          //用户名
    private String password;          //用户密码
    private String gender;          //用户性别
    private String introduction;          //用户介绍
    private String birth;          //用户生日
    private String location;          //用户家乡
    private String photo;          //用户头像
    private String createdTime;          //注册时间
    private String updateTime;          //修改时间

    private String totalUsers;
    private String recentUsers;
    private String genderCount;
}
