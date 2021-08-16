package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "testimonials")
public class testimonial extends BaseEntity{
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String yourJob;
    private String feedback;

    public testimonial() {
    }

    public testimonial(User user, String yourJob, String feedback) {
        this.user = user;
        this.yourJob = yourJob;
        this.feedback = feedback;
    }

    public testimonial(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, User user, String yourJob, String feedback) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.user = user;
        this.yourJob = yourJob;
        this.feedback = feedback;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getYourJob() {
        return yourJob;
    }

    public void setYourJob(String yourJob) {
        this.yourJob = yourJob;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
