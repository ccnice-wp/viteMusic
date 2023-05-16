package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.UserSongList;
import com.example.musicserver.service.Impl.SongServiceImpl;
import com.example.musicserver.service.Impl.UserSongListServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.downgoon.snowflake.Snowflake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/5/8
 */
@Controller
@ResponseBody
public class UserSongListController {

    @Autowired
    private UserSongListServiceImpl userSongListService;

    @Autowired
    private SongServiceImpl songService;

    static Snowflake snowflake = new Snowflake(1, 8);


    @RequestMapping(value = "/userSongList/getUserSongList", method = RequestMethod.POST)
    public String getUserSongList(UserSongList userSongList) {

        List<UserSongList> userSongLists = userSongListService.selectUserSongList(userSongList);

        return new SuccessMessage<List<UserSongList>>("查询用户歌单成功", userSongLists).getMessage();
    }

    @RequestMapping(value = "/userSongList/getSongList", method = RequestMethod.POST)
    public String getSongList(UserSongList userSongList0) {
        UserSongList userSongList = new UserSongList();
        userSongList.setUserSongListId(userSongList0.getUserSongListId());
        List<UserSongList> userSongLists = userSongListService.selectUserSongList(userSongList);
        List<Song> songs = new ArrayList<>();

        if (userSongLists.get(0).getSongIdList() != null) {
            String[] arr = userSongLists.get(0).getSongIdList().split(",");
            Song song = new Song();
            for (String s: arr) {
                if (s.equals("")) {
                    continue;
                }
                song.setSongId(s);
                List<Song> songs1 = songService.selectSong(song);
                songs.add(songs1.get(0));
            }
        }

        return new SuccessMessage<List<Song>>("查询成功", songs).getMessage();
    }

    @RequestMapping(value = "/userSongList/addUserSongLit", method = RequestMethod.POST)
    public String addUserSongList(UserSongList userSongList) {

        userSongList.setUserSongListId(String.valueOf(snowflake.nextId()));
        userSongList.setCreatedTime(new Date().toString());

        boolean b = userSongListService.addUserSongList(userSongList);

        if (b) {
            return new SuccessMessage<Null>("添加用户歌单成功").getMessage();
        } else {
            return new ErrorMessage("添加用户歌单失败").getMessage();
        }
    }

    @RequestMapping(value = "/userSongList/updateUserSongList", method = RequestMethod.POST)
    public String updateUserSongList(UserSongList userSongList) {

        System.out.println(userSongList);
        UserSongList userSongList1 = new UserSongList();
        if (userSongList.getSongIdList() == null || userSongList.getSongIdList().equals("")) {
            userSongList1.setUserSongListId(userSongList.getUserSongListId());
            userSongList1.setSongIdList(userSongList.getCreatedTime());
            userSongListService.updateUserSongList(userSongList1);
        } else {
            userSongListService.updateUserSongList(userSongList);
        }

        UserSongList userSongList2 = new UserSongList();
        userSongList2.setUserId(userSongList.getUserId());
        List<UserSongList> userSongLists = userSongListService.selectUserSongList(userSongList2);

        return new SuccessMessage<List<UserSongList>>("添加成功", userSongLists).getMessage();
    }
}
