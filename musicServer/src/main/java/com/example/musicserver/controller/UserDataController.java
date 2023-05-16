package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.UserData;
import com.example.musicserver.service.Impl.UserDataServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/4/2
 */
@Controller
@ResponseBody
public class UserDataController {

    @Autowired
    private UserDataServiceImpl userDataService;

    static Snowflake snowflake = new Snowflake(1,7);

    @RequestMapping(value = "/userData/getUserList", method = RequestMethod.POST)
    public String getUserList(UserData userData) {

        List<UserData> userDataList = userDataService.getUserDataList(userData);

        return new SuccessMessage<List<UserData>>("用户列表", userDataList).getMessage();
    }

    @RequestMapping(value = "/userData/addUserData", method = RequestMethod.POST)
    public String addUserData(UserData userData) {

        boolean b = userDataService.verifyUserName(userData);

        if (b) {

            return new ErrorMessage("该用户名已被注册").getMessage();
        } else {

            userData.setUserId(String.valueOf(snowflake.nextId()));
            userData.setCreatedTime(new Date().toString());
            userData.setUpdateTime(new Date().toString());

            boolean b1 = userDataService.addUserData(userData);
            if(b1) {
                return  new SuccessMessage<Null>("注册成功").getMessage();
            } else {
                return new ErrorMessage("注册失败").getMessage();
            }
        }
    }

    @RequestMapping(value = "/userData/userLogin", method = RequestMethod.POST)
    public String userLogin(UserData userData) {

        boolean b = userDataService.verifyUserPassword(userData);

        if (b) {

            List<UserData> userDataList = userDataService.getUserDataList(userData);

            return new SuccessMessage<List<UserData>>("登录成功", userDataList).getMessage();
        } else {
            return new ErrorMessage("登录失败").getMessage();
        }
    }

    @RequestMapping(value = "/userData/updateUserData", method = RequestMethod.POST)
    public String updateUserData(UserData userData) {
        userData.setUpdateTime(new Date().toString());
        userDataService.updateUserData(userData);
        return new SuccessMessage<Null>("更新成功").getMessage();
    }
}
