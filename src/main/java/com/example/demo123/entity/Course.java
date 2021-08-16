package com.example.demo123.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    private String title;
    private String thumbnail;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String sortDescription;
    private Long coreExpert;
    private float price;
    @Column(name = "rating_toltal")
    private float rating_toltal;
    private float total_money;
    private float withdrawn_money;
    private float remaining_amount;
    private float sale;
    private String status;

    public float getRatingToltal() {
        return rating_toltal;
    }

    public void setRatingToltal(float ratingToltal) {
        this.rating_toltal = ratingToltal;
    }

    @OneToMany(mappedBy = "course")
    private List<user_course> user_course = new ArrayList<>();
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Course() {
    }

    public Course(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String title, String thumbnail, String content, String sortDescription, Long coreExpert, float price, float rating_toltal, float total_money, float withdrawn_money, float remaining_amount, String status, Category category,float sale) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.sortDescription = sortDescription;
        this.coreExpert = coreExpert;
        this.price = price;
        this.rating_toltal = rating_toltal;
        this.total_money = total_money;
        this.withdrawn_money = withdrawn_money;
        this.remaining_amount = remaining_amount;
        this.status = status;
        this.category = category;
        this.sale = sale;
    }

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<Vote> votes = new ArrayList<>();

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

    public float getRating_toltal() {
        return rating_toltal;
    }

    public void setRating_toltal(float rating_toltal) {
        this.rating_toltal = rating_toltal;
    }

    public float getTotal_money() {
        return total_money;
    }

    public void setTotal_money(float total_money) {
        this.total_money = total_money;
    }

    public float getWithdrawn_money() {
        return withdrawn_money;
    }

    public void setWithdrawn_money(float withdrawn_money) {
        this.withdrawn_money = withdrawn_money;
    }

    public float getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(float remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
