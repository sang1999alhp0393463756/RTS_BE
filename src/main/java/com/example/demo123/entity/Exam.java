package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exam")
public class Exam extends BaseEntity{
    private String status;
    private String title;
    private String security;
    private String description;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "exam")
    private List<UserExam> userExams = new ArrayList<>();

    public Exam() {
    }

    public Exam(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String status, String title, String security, String description, Course course) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.status = status;
        this.title = title;
        this.security = security;
        this.description = description;
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
