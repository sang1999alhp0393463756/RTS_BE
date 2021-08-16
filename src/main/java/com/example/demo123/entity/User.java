package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(	name = "users")
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Email
    private String username;
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 120)
    private String fullName;

    @NotBlank
    private String phoneNumber;

    private String status;
    private String tokenEmail;
    private String description;
    private String avatar;
    private String dob;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<user_course> user_course = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<testimonial> testimonials = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Vote> votes = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExam> userExams = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userLesson> userLessons = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    public User() {

    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
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


    public User(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String username, String password, String fullName, String phoneNumber, String status, String tokenEmail, String description, String avatar, String dob, Set<Role> roles) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.tokenEmail = tokenEmail;
        this.description = description;
        this.avatar = avatar;
        this.dob = dob;
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTokenEmail() {
        return tokenEmail;
    }

    public void setTokenEmail(String tokenEmail) {
        this.tokenEmail = tokenEmail;
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


    public User(String username, String password, String fullName, String phoneNumber, String status,String dob) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.dob = dob;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}