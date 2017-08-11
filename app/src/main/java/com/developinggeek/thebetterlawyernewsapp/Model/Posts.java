package com.developinggeek.thebetterlawyernewsapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL-PC on 8/4/2017.
 */

public class Posts
{
    @SerializedName("author")
    private Author author;

    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("slug")
    private String slug;

    @SerializedName("url")
    private String url;

    @SerializedName("status")
    private String status;

    @SerializedName("title")
    private String title;

    @SerializedName("title_plain")
    private String titlePlain;

    @SerializedName("content")
    private String content;

    @SerializedName("excerpt")
    private String excerpt;

    @SerializedName("date")
    private String date;

    @SerializedName("comment_count")
    private Integer commentCount;

    @SerializedName("comment_status")
    private String commentStatus;

    @SerializedName("thumbnail")
    private String thumbnail;

    public Posts() {}

    public Posts(Integer id, String type, String slug, String url, String status, String title,
                 String titlePlain, String content, String excerpt, String date, Integer commentCount,
                 String commentStatus, String thumbnail)
    {
        this.id = id;
        this.type = type;
        this.slug = slug;
        this.url = url;
        this.status = status;
        this.title = title;
        this.titlePlain = titlePlain;
        this.content = content;
        this.excerpt = excerpt;
        this.date = date;
        this.commentCount = commentCount;
        this.commentStatus = commentStatus;
        this.thumbnail = thumbnail;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlePlain() {
        return titlePlain;
    }

    public void setTitlePlain(String titlePlain) {
        this.titlePlain = titlePlain;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
