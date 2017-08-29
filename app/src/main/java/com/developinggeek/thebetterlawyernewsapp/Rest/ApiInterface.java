package com.developinggeek.thebetterlawyernewsapp.Rest;

import com.developinggeek.thebetterlawyernewsapp.Model.Categories;
import com.developinggeek.thebetterlawyernewsapp.Model.PostRequest;
import com.developinggeek.thebetterlawyernewsapp.Model.Post_Response;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface ApiInterface
{

    @GET("get_recent_posts/")
    Call<PostsResponse> getRecentPosts();

    @GET("get_search_results/")
    Call<PostsResponse> getSearchResults(@Query("search") String val);

    @GET("get_category_posts/")
    Call<PostsResponse> getCategoryById(@Query("id") String id);

    @POST("saveData")
    Call<Post_Response> getPostResponse(@Body PostRequest postRequest);




}
