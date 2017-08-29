package com.developinggeek.thebetterlawyernewsapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Post_Response {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("cityName")
    @Expose
    private String cityName;

    @SerializedName("userCategory")
    @Expose
    private String userCategory;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("useName")
    @Expose
    private String useName;

    @SerializedName("errorCode")
    @Expose
    private String errorCode;

    @SerializedName("emailId")
    @Expose
    private String emailId;

    @SerializedName("id")
    @Expose
    private String id;

    public String getResult() {
        return result;
    }

    public String getCityName() {
        return cityName;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public String getCity() {
        return city;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getUseName() {
        return useName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    public String getError() {
        return error;
    }

    @SerializedName("userType")

    @Expose
    private String userType;

    @SerializedName("error")
    @Expose
    private String error;
}
