package com.example.demo123.dto.response;

import com.example.demo123.entity.Course;
import com.example.demo123.entity.User;
import com.example.demo123.entity.payment;

import java.util.List;

public interface PaymentRespon {
  Long getId();
  String getCreated_date();
  String getModified_date();
  String getCode();
  Long getCourse_id();
  float getMoney();
  String getName_bank();
  String getStatus();
  String getStk();
  Long getUser_id();
  String getFull_name();
  String getName_course();
}
