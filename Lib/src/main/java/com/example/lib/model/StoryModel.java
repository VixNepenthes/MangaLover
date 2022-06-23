package com.example.lib.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class StoryModel implements Serializable {
    private Long id;
    private String name;
    private String summaryContent;
    private String author;
    private String image;
    private Integer views;
    private Integer follow;
    private boolean hot;
    private boolean hide;
    private Timestamp dateCreate;
    private Integer categoryId;
    private Integer statusId;
    private Long userId;
    private String categoryName;
    private String statusName;
    private String userName;
    private String nameChapterLast;
    private Integer chapterNumberLast;

    public String getNameChapterLast() {
        return nameChapterLast;
    }

    public void setNameChapterLast(String nameChapterLast) {
        this.nameChapterLast = nameChapterLast;
    }

    public Integer getChapterNumberLast() {
        return chapterNumberLast;
    }

    public void setChapterNumberLast(Integer chapterNumberLast) {
        this.chapterNumberLast = chapterNumberLast;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummaryContent() {
        return summaryContent;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StoryModel() {
    }

    public StoryModel(String name, String image, String nameChapterLast) {
        this.name = name;
        this.image = image;
        this.nameChapterLast = nameChapterLast;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summaryContent = "+summaryContent+", image = "+image+", author = "+author+", follow = "+follow+", hot = "+hot+", dateCreate = "+dateCreate+", userName = "+userName+", userId = "+userId+", categoryName = "+categoryName+", hide = "+hide+", nameChapterLast = "+nameChapterLast+", statusId = "+statusId+", chapterNumberLast = "+chapterNumberLast+", name = "+name+", statusName = "+statusName+", id = "+id+", views = "+views+", categoryId = "+categoryId+"]";
    }
}
