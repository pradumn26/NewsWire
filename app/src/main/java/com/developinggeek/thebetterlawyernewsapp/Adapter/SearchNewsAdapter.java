package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Activity.ReadRecentNewsActivity;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL-PC on 8/11/2017.
 */

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.MySearchViewHolder>
{

    private List<Posts> posts;
    private Context mContext;

    public SearchNewsAdapter(List<Posts> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @Override
    public MySearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_news_item , parent ,false);
        return new MySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySearchViewHolder holder, final int position) {

        holder.title.setText(posts.get(position).getTitle());
        final String imageUrl = posts.get(position).getThumbnail();
        Picasso.with(mContext).load(imageUrl).into(holder.img);
        final View sharedView = holder.img;

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReadRecentNewsActivity.class);
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_PHOTO, imageUrl);
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE, posts.get(position).getTitle());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CONTENT, posts.get(position).getContent());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_NAME, posts.get(position).getAuthor().getName());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTVITY_AUTHOR_DESCRIPTION, posts.get(position).getAuthor().getDesp());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_URL,posts.get(position).getAuthor().getUrl());

                Bundle b = new Bundle();
                b.putSerializable(AppConstants.READ_RECENT_NEWS_ACTIVITY_CATEGORY_LIST,posts.get(position).getCategories());
                intent.putExtras(b);

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, sharedView, "newsPhotoTransitionFromMainActivityToReadNewsActivity");
                mContext.startActivity(intent , activityOptionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MySearchViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView title;
        View view;

        public MySearchViewHolder(View itemView)
        {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.search_news_image);
            title = (TextView)itemView.findViewById(R.id.search_news_title);
            view = itemView;
        }
    }

}
