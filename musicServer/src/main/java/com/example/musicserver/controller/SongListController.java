package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.SongList;
import com.example.musicserver.service.Impl.SongListServiceImpl;
import com.example.musicserver.service.Impl.SongServiceImpl;
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
 * @date 2023/4/23
 */
@Controller
@ResponseBody
public class SongListController {

    @Autowired
    private SongListServiceImpl songListService;

    @Autowired
    private SongServiceImpl songService;

    static Snowflake snowflake = new Snowflake(1, 6);

    @RequestMapping(value = "/songList/selectSongList", method = RequestMethod.POST)
    public String selectSongList(SongList songList) {

        List<SongList> songList1 = songListService.getSongList(songList);

        return new  SuccessMessage<List<SongList>>("列表查询成功", songList1).getMessage();
    }

    @RequestMapping(value = "/songList/addSongList", method = RequestMethod.POST)
    public String addSongList(SongList songList) {

        songList.setSongListId(String.valueOf(snowflake.nextId()));
        songList.setCreatedTime(new Date().toString());


        boolean b = songListService.addSongList(songList);

        if(b) {
            return new SuccessMessage<Null>("歌单添加成功").getMessage();
        } else {
            return new ErrorMessage("歌单添加失败").getMessage();
        }
    }

    @RequestMapping(value = "/songList/updateSongList", method = RequestMethod.POST)
    public String updateSongList(SongList songList) {

        boolean b = songListService.updateSongListByPrimaryKey(songList);

        if(b) {
            return new SuccessMessage<Null>("歌单信息更新成功").getMessage();
        } else {
            return new ErrorMessage("歌单信息更新失败").getMessage();
        }
    }

    @RequestMapping(value = "/songList/getSongListMusic", method = RequestMethod.POST)
    public String getSongListMusic(SongList songList) {

        List<SongList> songList1 = songListService.getSongList(songList);

        if(songList1.size() > 0) {
            String songIdListStr = songList1.get(0).getSongIdList();
            String[] songIdListArr = songIdListStr.split(",");
            List<Song> ansSongList = new ArrayList<>();
            Song song = new Song();
            if(songIdListArr.length > 0) {
                for (int i = 0; i < songIdListArr.length; i++) {
                    song.setSongId(songIdListArr[i]);
                    List<Song> songs = songService.selectSong(song);
                    ansSongList.add(songs.get(0));
                }
                return new SuccessMessage<List<Song>>("查询成功", ansSongList).getMessage();
            } else {
                return new ErrorMessage("未查询到结果").getMessage();
            }
        } else {
            return new ErrorMessage("未查询到结果").getMessage();
        }
    }

    @RequestMapping(value = "/songList/addSong", method = RequestMethod.POST)
    public String addSong(Song song) {

        SongList songList = new SongList();

        //此处的song.getName的值是songListId
        songList.setSongListId(song.getName());
        List<SongList> songList1 = songListService.getSongList(songList);
        if(songList1.get(0).getSongIdList() == null || songList1.get(0).getSongIdList().equals("")) {
            songList.setSongIdList(song.getSongId());
        } else {
            songList.setSongIdList(songList1.get(0).getSongIdList() + "," + song.getSongId());
        }
        boolean b = songListService.updateSongListByPrimaryKey(songList);

        if(b) {
            return new SuccessMessage<Null>("添加成功").getMessage();
        } else {
            return new ErrorMessage("添加歌曲错误").getMessage();
        }
    }

}
