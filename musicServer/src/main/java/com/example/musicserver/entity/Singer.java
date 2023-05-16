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
//歌手表
public class Singer {

    private String singerId;            //歌手ID
    private String name;            //歌手姓名
    private String gender;            //歌手性别
    private String photo;            //歌手图片
    private String birth;            //歌手出生日期
    private String location;            //歌手地区
    private String introduction;            //歌手介绍
    private String createdTime;            //创建时间
}
