package com.developinggeek.thebetterlawyernewsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developinggeek.thebetterlawyernewsapp.Adapter.RecentNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupremeCourt extends Fragment {

    private ApiInterface apiInterface;
    private RecyclerView mRecyclerView;

    public SupremeCourt() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_supreme_court, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.SupremeCourtList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        fetchGovernmentNews();

        return view;
    }

    private void fetchGovernmentNews()
    {
        Call<PostsResponse> call = apiInterface.getCategoryById("34+74");

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response)
            {
                List<Posts> posts = response.body().getPosts();

                mRecyclerView.setAdapter(new RecentNewsAdapter(posts , getContext()));
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });
    }

}
