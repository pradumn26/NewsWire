package com.developinggeek.thebetterlawyernewsapp.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.R;
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
    public void onBindViewHolder(NewsViewHolder holder, int position)
    {
        holder.title.setText(posts.get(position).getTitle());
        holder.brief.setText(posts.get(position).getExcerpt());
        String imgUrl = posts.get(position).getThumbnail();
        Picasso.with(mContext).load(imgUrl).into(holder.img);
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
