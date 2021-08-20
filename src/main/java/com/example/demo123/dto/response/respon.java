package com.example.demo123.dto.response;

public class respon {
    private String success;
    private String status;

    public respon(String success, String status) {
        this.success = success;
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
