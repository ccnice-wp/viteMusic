package com.example.musicserver.controller;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.entity.Singer;
import com.example.musicserver.service.Impl.SingerServiceImpl;
import com.example.musicserver.service.SingerService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Date;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/4/3
 */
@Controller
@ResponseBody
public class SingerController {

    @Autowired
    private SingerServiceImpl singerService;

    static Snowflake snowflake = new Snowflake(1,4);


    @RequestMapping(value = "/singer/getSingerList", method = RequestMethod.POST)
    public String getSingerList(Singer singer) {

        List<Singer> singerList = singerService.getSingerList(singer);

        return new SuccessMessage<List<Singer>>("歌手列表", singerList).getMessage();

    }

    @RequestMapping(value = "/singer/updateByPrimaryKey", method = RequestMethod.POST)
    public String updateByPrimaryKey(Singer singer) {
        boolean b = singerService.updateSingerByPrimaryKey(singer);

        if(b) {
            return new SuccessMessage<Null>("歌手信息更新成功").getMessage();
        } else {
            return new ErrorMessage("歌手信息更新失败").getMessage();
        }
    }

    @RequestMapping(value = "/singer/addSinger", method = RequestMethod.POST)
    public String addSinger(Singer singer) {

        singer.setSingerId(String.valueOf(snowflake.nextId()));
        singer.setCreatedTime(new Date().toString());

        boolean b = singerService.addSinger(singer);

        if (b) {
            return new SuccessMessage<Null>("歌手信息添加成功").getMessage();
        } else {
            return new ErrorMessage("歌手信息添加失败").getMessage();
        }
    }
}
