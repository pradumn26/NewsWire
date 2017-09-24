package com.developinggeek.thebetterlawyernewsapp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Adapter.BriefNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BriefsFragment extends Fragment
{

    private ApiInterface apiInterface;
    private ProgressDialog mProgress;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View fragmentScrim;
    private FloatingActionButton floatingActionButton;
    private TextView retryTextView;


    public BriefsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_briefs, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.brief_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNewNews();
            }
        });

        floatingActionButton= (FloatingActionButton) view.findViewById(R.id.retry_fab);
        retryTextView= (TextView) view.findViewById(R.id.retry_textView);

        floatingActionButton.setVisibility(View.GONE);
        retryTextView.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryTextView.setText("Retrying...");
                fetchNewNews();
            }
        });

        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Loading...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        fetchNewNews();

        return view;
    }

    private void fetchNewNews()
    {
        Call<PostsResponse> call = apiInterface.getSearchResults("new");

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response)
            {
                mProgress.cancel();
                List<Posts> posts = response.body().getPosts();

                mRecyclerView.setAdapter(new BriefNewsAdapter(posts , getContext()));

                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                mProgress.cancel();
            }
        });
    }

}
