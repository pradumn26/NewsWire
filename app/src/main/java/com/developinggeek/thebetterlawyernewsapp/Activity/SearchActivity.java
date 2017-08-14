package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.developinggeek.thebetterlawyernewsapp.Adapter.SearchNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchNewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String category;
    private ApiInterface apiInterface;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        category = getIntent().getStringExtra("category");

        mToolbar = (Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(category);

        mRecyclerView = (RecyclerView)findViewById(R.id.search_news_list);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView)findViewById(R.id.search_news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        fetchSearchNews();

    }

    private void fetchSearchNews()
    {
        Call<PostsResponse> call = apiInterface.getSearchResults(category);

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response)
            {

                List<Posts> posts = response.body().getPosts();

                mAdapter = new SearchNewsAdapter(posts , getApplicationContext());

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });


    }

}
