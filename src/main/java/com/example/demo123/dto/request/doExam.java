package com.example.demo123.dto.request;

import java.util.List;

public class doExam {
 private Long courseId;
 private List<userAnswer> data;
 private Long idExam;
 private String time;
 private Long userId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<userAnswer> getData() {
        return data;
    }

    public void setData(List<userAnswer> data) {
        this.data = data;
    }

    public Long getIdExam() {
        return idExam;
    }

    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
