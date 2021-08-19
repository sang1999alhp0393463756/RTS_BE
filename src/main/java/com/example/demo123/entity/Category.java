package com.example.demo123.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Course> courses = new ArrayList<>();
    private String status;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, List<Course> courses,String status) {
        this.name = name;
        this.courses = courses;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCourses(List<Course> course) {
        this.courses = course;
    }
}
