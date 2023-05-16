package com.example.musicserver.service;

import com.example.musicserver.entity.UserData;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public interface UserDataService {

    boolean verifyUserPassword(UserData userData);

    List<UserData> getUserDataList(UserData userData);

    boolean addUserData(UserData userData);

    boolean verifyUserName(UserData userData);

    boolean updateUserData(UserData userData);
}
