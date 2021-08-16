package com.example.demo123.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class courseRequest {
    @NotBlank
    private String title;
    @NotBlank
    private MultipartFile thumbnail;
    @NotBlank
    private String content;
    @NotBlank
    private String sortDescription;
    @NotBlank
    private Long coreExpert;
    @NotBlank
    private float price;
    @NotBlank
    private Long category_id;
    private float sale;

    public courseRequest() {
    }

    public courseRequest(String title, MultipartFile thumbnail, String content, String sortDescription, Long coreExpert, float price, Long category_id, float sale) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.sortDescription = sortDescription;
        this.coreExpert = coreExpert;
        this.price = price;
        this.category_id = category_id;
        this.sale = sale;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
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
}
