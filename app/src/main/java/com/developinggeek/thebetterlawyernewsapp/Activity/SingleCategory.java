package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.developinggeek.thebetterlawyernewsapp.Adapter.RecentNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCategory extends AppCompatActivity {
    private String categoryIdString;
    private ApiInterface apiInterface;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);

        categoryIdString= getIntent().getStringExtra(AppConstants.SINGLE_CATEGORY_ID_STRING);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.singleCategoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SingleCategory.this));
        mRecyclerView.setHasFixedSize(true);

        fetchGovernmentNews();
    }

    private void fetchGovernmentNews() {
        Call<PostsResponse> call = apiInterface.getCategoryById(categoryIdString);

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                List<Posts> posts = response.body().getPosts();

                mRecyclerView.setAdapter(new RecentNewsAdapter(posts, SingleCategory.this));
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });
    }
}
