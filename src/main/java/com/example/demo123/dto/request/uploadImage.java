package com.example.demo123.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class uploadImage {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
