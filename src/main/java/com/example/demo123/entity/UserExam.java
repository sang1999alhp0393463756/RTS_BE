package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_Exam")
public class UserExam extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
    private int numberCorrectAnswer;
    private int numberWrongAnswer;
    private Float score;
    private String status;
    private String time;

    public UserExam(User user, Exam exam, int numberCorrectAnswer, int numberWrongAnswer, Float score, String status, String time) {
        this.user = user;
        this.exam = exam;
        this.numberCorrectAnswer = numberCorrectAnswer;
        this.numberWrongAnswer = numberWrongAnswer;
        this.score = score;
        this.status = status;
        this.time = time;
    }

    public UserExam(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, User user, Exam exam, int numberCorrectAnswer, int numberWrongAnswer, Float score, String status, String time) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.user = user;
        this.exam = exam;
        this.numberCorrectAnswer = numberCorrectAnswer;
        this.numberWrongAnswer = numberWrongAnswer;
        this.score = score;
        this.status = status;
        this.time = time;
    }

    public UserExam() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getNumberCorrectAnswer() {
        return numberCorrectAnswer;
    }

    public void setNumberCorrectAnswer(int numberCorrectAnswer) {
        this.numberCorrectAnswer = numberCorrectAnswer;
    }

    public int getNumberWrongAnswer() {
        return numberWrongAnswer;
    }

    public void setNumberWrongAnswer(int numberWrongAnswer) {
        this.numberWrongAnswer = numberWrongAnswer;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
