package com.example.demo123.dto.request;

public class activePaymentRequest {
    private float withdrawn_money;
    private Long course_id;
    private Long paymentId;

    public float getWithdrawn_money() {
        return withdrawn_money;
    }

    public void setWithdrawn_money(float withdrawn_money) {
        this.withdrawn_money = withdrawn_money;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
