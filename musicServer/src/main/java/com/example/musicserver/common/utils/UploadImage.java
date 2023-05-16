package com.example.musicserver.common.utils;

import com.example.musicserver.common.net.ErrorMessage;
import com.example.musicserver.common.net.SuccessMessage;
import com.example.musicserver.controller.SingerController;
import com.example.musicserver.entity.Singer;
import com.example.musicserver.service.Impl.SingerServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author CCNICE
 * @date 2023/4/3
 *
 * 图片上传工具类
 */

public class UploadImage {

    //定义目标路径,图片最终放置的位置
    private final static String BASE_PATH = "/Users/ccnice/Desktop/graduation project/code/musicServer/src/main/resources/static/images/";
    public String uploadImage(MultipartFile file) {


        //获取上传的图片名称
        String fileName = file.getOriginalFilename();
        //使用UUID给上传的图片重新命名
        assert fileName != null;
        String newFileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
        //创建一个文件实例对象
        File image = new File(BASE_PATH + newFileName);

        //对文件进行上传操作
        try {
            file.transferTo(image);

            return "/api/image/"+newFileName;
        } catch (IOException e)  {
            e.printStackTrace();

            return new ErrorMessage("上传失败").getMessage();
        }
    }

}
