package com.example.musicserver.common.utils;

import com.example.musicserver.common.net.ErrorMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author CCNICE
 * @date 2023/4/6
 *
 * 音乐上传工具类
 */
public class UploadMusic {

    //定义目标路径,音乐最终放置的位置
    private final static String BASE_PATH = "/Users/ccnice/Desktop/graduation project/code/musicServer/src/main/resources/static/music01/";
    public String uploadMusic(MultipartFile file) {


        //获取上传的音乐名称
        String fileName = file.getOriginalFilename();

        //使用UUID给上传的音乐重新命名
        assert fileName != null;
        String newFileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
        //创建一个文件实例对象
        File music = new File(BASE_PATH + newFileName);

        //对文件进行上传操作
        try {
            file.transferTo(music);

            return "/api/musics/"+newFileName;
        } catch (IOException e)  {
            e.printStackTrace();

            return new ErrorMessage("上传失败").getMessage();
        }
    }

}
