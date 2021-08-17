package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "payment")
public class payment extends BaseEntity{
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long userId;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long courseId;
    private String code;
    private float money;
    private String stk;
    private String nameBank;
    private String status;

    public payment() {
    }

    public payment(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, Long userId, Long courseId, String code, float money, String stk, String nameBank, String status) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.userId = userId;
        this.courseId = courseId;
        this.code = code;
        this.money = money;
        this.stk = stk;
        this.nameBank = nameBank;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getStk() {
        return stk;
    }

    public void setStk(String stk) {
        this.stk = stk;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }
}
