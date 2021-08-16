package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "document")
public class document extends BaseEntity{
    private String status;
    private String title;
    private String security;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private String documentName;

    public document() {
    }

    public document(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String status, String title, String security, Course course, String documentName) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.status = status;
        this.title = title;
        this.security = security;
        this.course = course;
        this.documentName = documentName;
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

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
