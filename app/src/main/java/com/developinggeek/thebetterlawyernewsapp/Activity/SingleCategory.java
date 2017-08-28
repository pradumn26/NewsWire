package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;

import com.developinggeek.thebetterlawyernewsapp.Adapter.RecentNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Adapter.SearchNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.developinggeek.thebetterlawyernewsapp.Rest.ExceptionHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCategory extends AppCompatActivity {
    List<Posts> posts;
    private SearchNewsAdapter mAdapter;
    private String categoryIdString;
    private ApiInterface apiInterface;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));
        setContentView(R.layout.activity_single_category);

        categoryIdString = getIntent().getStringExtra(AppConstants.SINGLE_CATEGORY_ID_STRING);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.singleCategoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SingleCategory.this));
        mRecyclerView.setHasFixedSize(true);

        fetchNews();
    }

    private void fetchNews() {
        Call<PostsResponse> call = apiInterface.getCategoryById(categoryIdString);

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                posts = response.body().getPosts();

                for (Posts post : posts)
                    post.setShowShimmer(true);
                mAdapter = new SearchNewsAdapter(posts, SingleCategory.this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (Posts post : posts)
                            post.setShowShimmer(false);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 3000);
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        startActivity(intent);
    }
}
