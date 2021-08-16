package com.example.demo123.dto.response;

import com.example.demo123.entity.UserExam;

import java.util.List;

public class objectResultDetailTest {
    List<UserExam> UserExam;
    List<resultTestDetail> resultTestDetail;

    public objectResultDetailTest() {
    }

    public objectResultDetailTest(List<com.example.demo123.entity.UserExam> userExam, List<com.example.demo123.dto.response.resultTestDetail> resultTestDetail) {
        UserExam = userExam;
        this.resultTestDetail = resultTestDetail;
    }

    public List<com.example.demo123.entity.UserExam> getUserExam() {
        return UserExam;
    }

    public void setUserExam(List<com.example.demo123.entity.UserExam> userExam) {
        UserExam = userExam;
    }

    public List<com.example.demo123.dto.response.resultTestDetail> getResultTestDetail() {
        return resultTestDetail;
    }

    public void setResultTestDetail(List<com.example.demo123.dto.response.resultTestDetail> resultTestDetail) {
        this.resultTestDetail = resultTestDetail;
    }
}
