package com.developinggeek.thebetterlawyernewsapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL-PC on 8/4/2017.
 */

public class Author
{

    @SerializedName("id")
    private Integer id;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("nickname")
    private String nickName;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String desp;


    public Author() {}

    public Author(Integer id, String slug, String name, String firstName, String lastName,
                  String nickName, String url, String desp)
    {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.url = url;
        this.desp = desp;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
