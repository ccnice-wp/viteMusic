package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.common.utils.UploadMusic;
import com.example.musicserver.entity.Song;
import com.example.musicserver.service.Impl.SongServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/4/5
 */
@Controller
@ResponseBody
public class SongController {

    @Autowired
    private SongServiceImpl songService;

    static Snowflake snowflake = new Snowflake(1, 5);
    static UploadMusic uploadMusic = new UploadMusic();

    @RequestMapping(value = "/song/addSong", method = RequestMethod.POST)
    public String addSong(Song song) {

        song.setSongId(String.valueOf(snowflake.nextId()));
        song.setCreatedTime(new Date().toString());
        song.setUpdateTime(new Date().toString());
        boolean b = songService.addSong(song);

        if (b) {
            return new SuccessMessage<Null>("添加歌曲成功").getMessage();
        } else {
            return new ErrorMessage("添加歌曲失败").getMessage();
        }
    }

    @RequestMapping(value = "/song/selectSong", method = RequestMethod.POST)
    public String selectSong(Song song) {
        List<Song> songs = songService.selectSong(song);
        return new SuccessMessage<List<Song>>("歌曲列表查询成功", songs).getMessage();
    }

    @RequestMapping(value = "/song/updateSong", method = RequestMethod.POST)
    public String updateSong(Song song) {

        boolean b = songService.updateSong(song);

        if (b) {
            return new SuccessMessage<Null>("歌曲信息更新成功").getMessage();
        } else {
            return new ErrorMessage("歌曲信息更新失败").getMessage();
        }
    }

    @RequestMapping(value = "/song/updateSongFile", method = RequestMethod.POST)
    public String updateSongFile(MultipartFile file, Song song) {

        String s = uploadMusic.uploadMusic(file);
        song.setUrl(s);
        boolean b = songService.updateSong(song);
        if (b) {
            return new SuccessMessage<Null>("音乐上传成功").getMessage();
        } else {
            return new ErrorMessage("音乐上传失败").getMessage();
        }
    }

    @RequestMapping(value = "/song/getRankingList", method = RequestMethod.POST)
    public String getRankingList() {
        List<Song> songs = songService.selectSongOrderByPhoto();

        return new SuccessMessage<List<Song>>("排行榜获取成功", songs).getMessage();
    }

    @RequestMapping(value = "/song/updateSongRankingList", method = RequestMethod.POST)
    public String updateSongRankingList(Song song) {

        List<Song> songs = songService.selectSong(song);
        songs.get(0).setPhoto(songs.get(0).getPhoto() + 1);

        boolean b = songService.updateSong(songs.get(0));

        if (b) {
            return new SuccessMessage<Null>("歌曲信息更新成功").getMessage();
        } else {
            return new ErrorMessage("歌曲信息更新失败").getMessage();
        }
    }

}
