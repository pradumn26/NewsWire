package com.developinggeek.thebetterlawyernewsapp.Rest;

import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DELL-PC on 8/4/2017.
 */

public interface ApiInterface
{

    @GET("get_recent_posts/")
    Call<PostsResponse> getRecentPosts();

    @GET("get_search_results/")
    Call<PostsResponse> getSearchResults(@Query("search") String val);

    @GET("get_category_posts/")
    Call<PostsResponse> getCategoryById(@Query("id") String id);

}
