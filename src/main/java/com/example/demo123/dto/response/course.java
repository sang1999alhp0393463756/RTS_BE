package com.example.demo123.dto.response;

import com.example.demo123.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class course {
    private Long id;
    private String title;
    private String thumbnail;
    private String content;
    private String sortDescription;
    private Long core_Expert;
    private float price;
    private float ratingToltal;
    private long categoryId;
    private String categoryName;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date createDate;
    private String createdBy;
    private String status;
    private float sale;
    private userRespon expert;
    private Date date_duyet;
    private String email_duyet;

    public Date getDate_duyet() {
        return date_duyet;
    }

    public void setDate_duyet(Date date_duyet) {
        this.date_duyet = date_duyet;
    }

    public String getEmail_duyet() {
        return email_duyet;
    }

    public void setEmail_duyet(String email_duyet) {
        this.email_duyet = email_duyet;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public userRespon getExpert() {
        return expert;
    }

    public void setExpert(userRespon expert) {
        this.expert = expert;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRatingToltal() {
        return ratingToltal;
    }

    public void setRatingToltal(float ratingToltal) {
        this.ratingToltal = ratingToltal;
    }

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
        return core_Expert;
    }

    public void setCoreExpert(Long coreExpert) {
        this.core_Expert = coreExpert;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getCore_Expert() {
        return core_Expert;
    }

    public void setCore_Expert(Long core_Expert) {
        this.core_Expert = core_Expert;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
