package com.example.demo123.dto.response;

public class SliderResponse {
    private String title;
    private String notes;
    private String status;

    public SliderResponse() {
    }

    public SliderResponse(String title, String notes, String status) {
        this.title = title;
        this.notes = notes;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
