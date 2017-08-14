package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.blurry.Blurry;


public class CollapseNewsAdapter extends RecyclerView.Adapter<CollapseNewsAdapter.MyNewsViewHolder>
{


    private List<Posts> posts;
    private Context mContext;

    public CollapseNewsAdapter(List<Posts> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }


    @Override
    public MyNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_collapse_news , parent ,false);
        return new MyNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyNewsViewHolder holder, int position) {

        holder.tv_news.setText(posts.get(position).getTitle().toString());

        Picasso.with(mContext).load(posts.get(position).getThumbnail()).into(holder.img);
       // Picasso.with(mContext).load(posts.get(position).getThumbnail()).into(holder.imgSmall);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyNewsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img , imgSmall;
        TextView tv_news;
        RelativeLayout container;

        public MyNewsViewHolder(View itemView ) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.main_collapse_img);
            tv_news = (TextView)itemView.findViewById(R.id.main_collapse_text);
            imgSmall = (ImageView)itemView.findViewById(R.id.main_collapse_small_img);
            container = (RelativeLayout)itemView.findViewById(R.id.main_collapse_container);

           // Blurry.with(mContext).radius(25).sampling(2).onto(container);
            //to blur main image
            //Bitmap bitmap = ((BitmapDrawable)imgSmall.getDrawable()).getBitmap();
            //Blurry.with(mContext).capture(imgSmall).into(img);

        }
    }

}
