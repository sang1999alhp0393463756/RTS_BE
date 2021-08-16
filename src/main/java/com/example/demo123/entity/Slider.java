package com.example.demo123.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sliders")
public class Slider extends BaseEntity{
    private String title;
    private String image;
    private String link;
    private String notes;
    private String status;

    public Slider() {
    }

    public Slider(String title, String image, String link, String notes, String status) {
        this.title = title;
        this.image = image;
        this.link = link;
        this.notes = notes;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
