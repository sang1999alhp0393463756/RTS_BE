package com.example.demo123.dto.response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class userRespon {
    private Long id;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String status;
    private List<String> role;
    private String description;
    private String avatar;
    private String dob;

    public userRespon() {
    }

    public userRespon(Long id, String username, String fullName, String phoneNumber, String status, List<String> role, String description, String avatar, String dob) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.role = role;
        this.description = description;
        this.avatar = avatar;
        this.dob = dob;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRoles() {
        return role;
    }

    public void setRoles(List<String> roles) {
        this.role = roles;
    }
}
