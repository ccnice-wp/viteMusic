package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.dao.AdminMapper;
import com.example.musicserver.dao.SongListMapper;
import com.example.musicserver.dao.SongMapper;
import com.example.musicserver.entity.Admin;
import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.SongList;
import com.example.musicserver.entity.UserData;
import com.example.musicserver.service.Impl.AdminServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.downgoon.snowflake.Snowflake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
@Controller
@ResponseBody
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongListMapper songListMapper;

    static Snowflake snowflake = new Snowflake(1,1);

    @RequestMapping(value = "/admin/loginStatus",method = RequestMethod.POST)
    public String loginStatus(Admin admin) {

        if (adminService.verifyAdminPassword(admin.getName(), admin.getPassword())) {
            return new SuccessMessage<Null>("登录成功！").getMessage();
        } else {
            return new ErrorMessage("用户名或密码错误！").getMessage();
        }
    }

    @RequestMapping(value = "admin/selectInfo", method = RequestMethod.POST)
    public String selectInfo() {

        ArrayList<Object> objectsAns = new ArrayList<>();

        ArrayList<HashMap<String, String>> objects = new ArrayList<>();
        HashMap<String, String> hashMap1 = new HashMap<>();
        List<UserData> userData1 = adminMapper.selectUserCount();
        hashMap1.put("totalUsers", userData1.get(0).getTotalUsers());
        hashMap1.put("recentUsers", userData1.get(0).getRecentUsers());

        HashMap<String, String> hashMap2 = new HashMap<>();
        List<UserData> userData2 = adminMapper.selectGenderCounter();
        hashMap2.put("male", userData2.get(0).getGenderCount());
        hashMap2.put("female", userData2.get(1).getGenderCount());
        hashMap2.put("unknown", userData2.get(2).getGenderCount());

        HashMap<String, String> hashMap3 = new HashMap<>();
        Song song = new Song();
        List<Song> songs = songMapper.selectSong(song);
        hashMap3.put("totalSongs", String.valueOf(songs.size()));

        HashMap<String, String> hashMap4 = new HashMap<>();
        SongList songList = new SongList();
        List<SongList> songLists = songListMapper.selectSongList(songList);
        hashMap4.put("totalSongList", String.valueOf(songLists.size()));

        List<Song> songPlayNum = adminMapper.selectPlayNum();

        objects.add(hashMap1);
        objects.add(hashMap2);
        objects.add(hashMap3);
        objects.add(hashMap4);

        objectsAns.add(objects);
        objectsAns.add(songPlayNum);

        return new SuccessMessage<List<Object>>("查询信息", objectsAns).getMessage();
    }

}
