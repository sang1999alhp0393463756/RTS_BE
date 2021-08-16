package com.example.demo123.dto.response;

import com.example.demo123.entity.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public interface examRespon {
    Long getId();
    String getCreated_by();
    String getCreated_date();
    String getModified_by();
    String getModified_date();
    String getStatus();
    Long getCourse_id();
    String getTitle();

}
