package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.common.utils.UploadImage;
import com.example.musicserver.entity.Singer;
import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.SongList;
import com.example.musicserver.entity.UserData;
import com.example.musicserver.service.Impl.SingerServiceImpl;
import com.example.musicserver.service.Impl.SongListServiceImpl;
import com.example.musicserver.service.Impl.SongServiceImpl;
import com.example.musicserver.service.Impl.UserDataServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/4/3
 */
@Controller
@ResponseBody
public class UploadController {
    @Autowired
    private SingerServiceImpl singerService;

    @Autowired
    private UserDataServiceImpl userDataService;

    @Autowired
    private SongListServiceImpl songListService;

    @RequestMapping(value = "upload/SingerImage", method = RequestMethod.POST)
    public String uploadSingerImage(MultipartFile file, Singer singer) throws IOException {
        //调用图片上传工具类
        UploadImage uploadImage = new UploadImage();
        String s = uploadImage.uploadImage(file);

        Singer singer1 = new Singer();
        singer1.setSingerId(singer.getSingerId());
        singer1.setPhoto(s);

        singerService.updateSingerByPrimaryKey(singer1);

        return new SuccessMessage<Null>("图片上传成功").getMessage();
    }

    @RequestMapping(value = "upload/SongListImage", method = RequestMethod.POST)
    public String uploadSongListImage(MultipartFile file, SongList songList) throws IOException {
        //调用图片上传工具类
        UploadImage uploadImage = new UploadImage();
        String s = uploadImage.uploadImage(file);

        SongList songList1 = new SongList();
        songList1.setSongListId(songList.getSongListId());
        songList1.setPhoto(s);

        songListService.updateSongListByPrimaryKey(songList1);

        return new SuccessMessage<Null>("图片上传成功").getMessage();
    }

    @RequestMapping(value = "/upload/userDataImage", method = RequestMethod.POST)
    public String userDataImage(MultipartFile file, UserData userData) throws IOException {
        //调用图片上传工具类
        UploadImage uploadImage = new UploadImage();
        String s = uploadImage.uploadImage(file);
        UserData userData1 = new UserData();
        userData1.setUserId(userData.getUserId());
        userData1.setPhoto(s);

        userDataService.updateUserData(userData1);

        return new SuccessMessage<Null>("图片上传成功").getMessage();
    }


}
