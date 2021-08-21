package com.example.demo123.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class updateCourseRequest {
    private String title;
    private String thumbnail;
    private String content;
    private String sortDescription;
    private Long coreExpert;
    private float price;
    @NotBlank
    private Long category_id;
    private float sale;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSortDescription() {
        return sortDescription;
    }

    public void setSortDescription(String sortDescription) {
        this.sortDescription = sortDescription;
    }

    public Long getCoreExpert() {
        return coreExpert;
    }

    public void setCoreExpert(Long coreExpert) {
        this.coreExpert = coreExpert;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }
}
