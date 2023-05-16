package com.example.musicserver.common.net;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author CCNICE
 * @date 2023/4/3
 *
 * 设置静态资源转换，访问图片，音乐等
 */
@Configuration
public class HttpConverterConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //其中image表示访问的前缀。"file:F:/img/"是文件真实的存储路径
        registry.addResourceHandler("/image/**").addResourceLocations("file:/Users/ccnice/Desktop/graduation project/code/musicServer/src/main/resources/static/images/");
        registry.addResourceHandler("/music/**").addResourceLocations("file:/Users/ccnice/Desktop/graduation project/code/musicServer/src/main/resources/static/musics/");
        registry.addResourceHandler("/musics/**").addResourceLocations("file:/Users/ccnice/Desktop/graduation project/code/musicServer/src/main/resources/static/music01/");
    }

}
