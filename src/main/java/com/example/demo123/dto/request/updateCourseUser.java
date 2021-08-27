package com.example.demo123.dto.request;

public class updateCourseUser {
    Long userID;
    Long courseID;
    String nguoi_duyet;
    float price;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getNguoi_duyet() {
        return nguoi_duyet;
    }

    public void setNguoi_duyet(String nguoi_duyet) {
        this.nguoi_duyet = nguoi_duyet;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
