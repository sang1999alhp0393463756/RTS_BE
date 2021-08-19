package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_course")
public class user_course implements Serializable{


    private User user;
    private Course course;
    private String status;
    private String date;
    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public user_course() {
    }

    public user_course(User user, Course course,String status,String date) {
        this.user = user;
        this.course = course;
        this.status = status;
        this.date = date;
    }
}
