package com.example.demo123.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogRespon extends BaseRespon{
    private userRespon user;
    private String thumbnail;
    private String title;
    private String content;
    private String sortDescription;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public userRespon getUser() {
        return user;
    }

    public void setUser(userRespon user) {
        this.user = user;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSortDescription() {
        return sortDescription;
    }

    public void setSortDescription(String sortDescription) {
        this.sortDescription = sortDescription;
    }


    public BlogRespon(userRespon user, String thumbnail, String title, String content, String sortDescription,String status) {
        this.user = user;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.sortDescription = sortDescription;
        this.status = status;
    }

    public BlogRespon(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, userRespon user, String thumbnail, String title, String content, String sortDescription,  String status) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.user = user;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.sortDescription = sortDescription;
        this.status = status;
    }
}
