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

import org.w3c.dom.Text;

import java.util.List;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecentNewsAdapter.NewsViewHolder>
{

    private List<Posts> posts;
    private Context mContext;

    public RecentNewsAdapter(List<Posts> posts, Context mContext)
    {
        this.posts = posts;
        this.mContext = mContext;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_news_layout , parent ,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position)
    {
        holder.title.setText(posts.get(position).getTitle());
        holder.brief.setText(posts.get(position).getExcerpt());
        final String imgUrl = posts.get(position).getThumbnail();
        Picasso.with(mContext).load(imgUrl).into(holder.img);
        final View sharedView = holder.img;

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, ReadRecentNewsActivity.class);
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_PHOTO, imgUrl);
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE, posts.get(position).getTitle());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CONTENT, posts.get(position).getContent());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_NAME, posts.get(position).getAuthor().getName());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTVITY_AUTHOR_DESCRIPTION, posts.get(position).getAuthor().getDesp());
                intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_URL,posts.get(position).getAuthor().getUrl());

                Bundle b = new Bundle();
                b.putSerializable(AppConstants.READ_RECENT_NEWS_ACTIVITY_CATEGORY_LIST,posts.get(position).getCategories());
                intent.putExtras(b);

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, sharedView, "newsPhotoTransitionFromMainActivityToReadNewsActivity");
                mContext.startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView img;
        TextView title , brief;

        public NewsViewHolder(View itemView)
        {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.recent_news_title);
            brief = (TextView)itemView.findViewById(R.id.recent_news_brief);
            img = (ImageView)itemView.findViewById(R.id.recent_news_image);
            view = itemView.findViewById(R.id.recent_list_container);
        }

    }

}
