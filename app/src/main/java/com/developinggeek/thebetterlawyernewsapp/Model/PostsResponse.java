package com.developinggeek.thebetterlawyernewsapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL-PC on 8/4/2017.
 */

public class PostsResponse
{

    @SerializedName("status")
    private String status;

    @SerializedName("count")
    private Integer count;

    @SerializedName("count_total")
    private Integer countTotal;

    @SerializedName("pages")
    private Integer pages;

    @SerializedName("posts")
    private List<Posts> posts;


    public PostsResponse() {}

    public PostsResponse(String status, Integer count, Integer countTotal, Integer pages, List<Posts> posts)
    {
        this.status = status;
        this.count = count;
        this.countTotal = countTotal;
        this.pages = pages;
        this.posts = posts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }


}
