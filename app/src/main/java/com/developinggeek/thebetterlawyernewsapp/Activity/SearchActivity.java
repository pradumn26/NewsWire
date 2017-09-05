package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Adapter.SearchNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.developinggeek.thebetterlawyernewsapp.Rest.ExceptionHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;
    List<Posts> posts;
    private SearchNewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String category;
    private ApiInterface apiInterface;
    private Toolbar mToolbar;
    private FloatingActionButton floatingActionButton;
    private TextView retryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));
        setContentView(R.layout.activity_search);

        category = getIntent().getStringExtra("category");

        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(category);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchSearchNews();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.search_news_list);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.retry_fab);
        retryTextView = (TextView) findViewById(R.id.retry_textView);
        floatingActionButton.setVisibility(View.GONE);
        retryTextView.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryTextView.setText("Retrying...");
                fetchSearchNews();
            }
        });

        fetchSearchNews();
    }

    private void fetchSearchNews() {
        Call<PostsResponse> call = apiInterface.getSearchResults(category);

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                progressDialog.cancel();
                floatingActionButton.setVisibility(View.GONE);
                retryTextView.setVisibility(View.GONE);
                posts = response.body().getPosts();
                for (Posts post : posts)
                    post.setShowShimmer(true);
                mAdapter = new SearchNewsAdapter(posts, SearchActivity.this);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (Posts post : posts)
                            post.setShowShimmer(false);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 3000);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                progressDialog.cancel();
                floatingActionButton.setVisibility(View.VISIBLE);
                retryTextView.setVisibility(View.VISIBLE);
                retryTextView.setText("Tap to retry");
            }
        });
    }
}
