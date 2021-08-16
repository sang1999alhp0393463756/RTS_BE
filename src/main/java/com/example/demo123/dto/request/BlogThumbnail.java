package com.example.demo123.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class BlogThumbnail {
    private MultipartFile imageBlog;

    public MultipartFile getImageBlog() {
        return imageBlog;
    }

    public void setImageBlog(MultipartFile imageBlog) {
        this.imageBlog = imageBlog;
    }
}
