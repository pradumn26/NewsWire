package com.developinggeek.thebetterlawyernewsapp.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.developinggeek.thebetterlawyernewsapp.Activity.ReadRecentNewsActivity;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.thefinestartist.utils.service.ServiceUtil.getWindowManager;


public class RecentNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Posts> posts;
    private Context mContext;
    private int newsStripCount = 0;

    public RecentNewsAdapter(List<Posts> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recent_news_layout1, parent, false);
            return new ViewHolder0(view);
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_news_layout, parent, false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == 0) {
            final ViewHolder0 viewHolder0 = (ViewHolder0) holder;

            Animation animationToLeft = new TranslateAnimation(AppConstants.width, 0, 0, 0);
            animationToLeft.setDuration(5000);
            animationToLeft.setRepeatCount(Animation.INFINITE);
            viewHolder0.textView.setAnimation(animationToLeft);

            animationToLeft.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    viewHolder0.textView.setText(posts.get(newsStripCount).getTitle());
                    if (newsStripCount == (posts.size() - 1))
                        newsStripCount = 0;
                    else
                        newsStripCount++;
                }
            });

        } else {
            ViewHolder1 viewHolder2 = (ViewHolder1) holder;
            if (posts.get(position).isShowShimmer())
                viewHolder2.shimmerFrameLayout.startShimmerAnimation();
            else {
                viewHolder2.shimmerFrameLayout.stopShimmerAnimation();
                viewHolder2.title.setText(posts.get(position).getTitle());
                viewHolder2.title.setBackground(null);
                viewHolder2.linearLayout1.setVisibility(View.GONE);
                viewHolder2.linearLayout2.setVisibility(View.GONE);
                final String imgUrl = posts.get(position).getThumbnail();
                if (imgUrl != null)
                    Picasso.with(mContext).load(imgUrl).into(viewHolder2.img);
                else
                    viewHolder2.img.setImageResource(R.mipmap.image_not_available);
                final View sharedView = viewHolder2.img;

                viewHolder2.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ReadRecentNewsActivity.class);
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_PHOTO, imgUrl);
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE, posts.get(position).getTitle());
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CONTENT, posts.get(position).getContent());
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_NAME, posts.get(position).getAuthor().getName());
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTVITY_AUTHOR_DESCRIPTION, posts.get(position).getAuthor().getDesp());
                        intent.putExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_URL, posts.get(position).getAuthor().getUrl());
                        intent.putExtra(AppConstants.APP_ID, posts.get(position).getId() + "");
                        Log.i("appid", posts.get(position).getId() + " ");

                        Bundle b = new Bundle();
                        b.putSerializable(AppConstants.READ_RECENT_NEWS_ACTIVITY_CATEGORY_LIST, posts.get(position).getCategories());
                        intent.putExtras(b);

                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, sharedView, "newsPhotoTransitionFromMainActivityToReadNewsActivity");
                        mContext.startActivity(intent, activityOptionsCompat.toBundle());
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ViewHolder0 extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder0(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.headline);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        ShimmerFrameLayout shimmerFrameLayout;
        View view;
        ImageView img;
        TextView title;
        LinearLayout linearLayout1, linearLayout2;

        public ViewHolder1(View itemView) {
            super(itemView);
            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linear_layout_under_textview);
            linearLayout2 = (LinearLayout) itemView.findViewById(R.id.linear_layout_under_textview_2);
            shimmerFrameLayout = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmerView_in_recentAdapter);
            title = (TextView) itemView.findViewById(R.id.recent_news_title);
            img = (ImageView) itemView.findViewById(R.id.recent_news_image);
            view = itemView.findViewById(R.id.recent_list_container);


        }
    }
}
