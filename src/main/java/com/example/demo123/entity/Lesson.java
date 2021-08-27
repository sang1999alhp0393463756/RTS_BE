package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity{
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String image;
    private String linkVideo;
    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "lesson")
    private List<userLesson> userLessons = new ArrayList<>();

    public Lesson() {
    }

    public Lesson(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String title, String content, String image, String linkVideo, String status,  Course course) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.title = title;
        this.content = content;
        this.image = image;
        this.linkVideo = linkVideo;
        this.status = status;

        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
