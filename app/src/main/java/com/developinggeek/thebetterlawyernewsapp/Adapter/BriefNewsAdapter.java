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

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by DELL-PC on 8/14/2017.
 */

public class BriefNewsAdapter extends RecyclerView.Adapter<BriefNewsAdapter.MyBriefHolder>
{

    private List<Posts> posts;
    private Context mContext;

    public BriefNewsAdapter(List<Posts> posts, Context mContext)
    {
        this.posts = posts;
        this.mContext = mContext;
    }

    @Override
    public MyBriefHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brief_single_layout , parent , false);
        return new MyBriefHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBriefHolder holder, int position)
    {
        holder.title.setText(posts.get(position).getTitle());
        holder.content.setHtml(posts.get(position).getExcerpt());

        String imgUrl = posts.get(position).getThumbnail();
        Picasso.with(mContext).load(imgUrl).placeholder(R.mipmap.image_not_available).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyBriefHolder extends RecyclerView.ViewHolder
   {
       ImageView img;
       TextView title;
       HtmlTextView content;

       public MyBriefHolder(View itemView)
       {
           super(itemView);

           img = (ImageView)itemView.findViewById(R.id.brief_img);
           title = (TextView)itemView.findViewById(R.id.brief_title);
           content = (HtmlTextView)itemView.findViewById(R.id.brief_content);


       }
   }

}
