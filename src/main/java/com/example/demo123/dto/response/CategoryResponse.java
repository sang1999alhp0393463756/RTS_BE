package com.example.demo123.dto.response;

import java.util.Date;

public class CategoryResponse extends BaseRespon{

    private String name;
    private Long courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
