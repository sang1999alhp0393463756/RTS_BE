package com.example.demo123.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path imgBlog = Paths.get("/upload-dir/Blog");
//        String imgPath = imgBlog.toFile().getAbsolutePath();
//        registry.addResourceHandler("/upload-dir/Blog/**").addResourceLocations("file:/"+imgPath+"/");
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload-dir/Blog/**").addResourceLocations("file:upload-dir\\Blog\\");
    }
}
