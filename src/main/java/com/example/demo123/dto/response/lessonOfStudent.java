package com.example.demo123.dto.response;

import com.example.demo123.entity.Course;

import java.util.Date;

public interface lessonOfStudent {
     Long getId();
     String getTitle();
     String getContent();
     String getImage();
     String getLink_video();
     Long getCourse_id();
     String getCreated_date();
     String getStatus();
     String getIsStudy();
     String getShort_description();
}
