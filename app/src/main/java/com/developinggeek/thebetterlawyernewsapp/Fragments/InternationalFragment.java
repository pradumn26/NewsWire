package com.developinggeek.thebetterlawyernewsapp.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ViewSwitcher;

import com.developinggeek.thebetterlawyernewsapp.Adapter.RecentNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternationalFragment extends Fragment {
    ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private RecyclerView mRecyclerView;
    private List<Posts> posts;
    private RecentNewsAdapter mAdapter;
    List<Posts> imageSwitcherImages=new ArrayList<>();
    List<Bitmap> bitmapArrayList=new ArrayList<>();


    public InternationalFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_international, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.international_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        fetchInternationalNews();

        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
        final Animation zoomin = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoomout);

        final ImageSwitcher imageSwitcher = (ImageSwitcher)view.findViewById(R.id.imageSwitcher3);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getActivity().getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        imageSwitcher.setInAnimation(animationFadeIn);
        imageSwitcher.setOutAnimation(animationFadeOut);

        // DisplayMetrics displayMetrics = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //float width=displayMetrics.widthPixels;
        final AnimationSet set = new AnimationSet(true);
        Animation trAnimation = new TranslateAnimation(0, 20, 0, 300);
        trAnimation.setDuration(8000);

        trAnimation.setRepeatMode(Animation.REVERSE);

        set.addAnimation(trAnimation);
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(8000);
        set.addAnimation(anim);


        final int[] animationCounter = {0};
        int i=0;
        final Handler imageSwitcherHandler;
        imageSwitcherHandler = new Handler(Looper.getMainLooper());
        imageSwitcherHandler.post(new Runnable() {
            @Override
            public void run() {
                if(imageSwitcherImages.size()>0) {
                    final ImageView imageView=(ImageView) imageSwitcher.getCurrentView();
                    if(imageSwitcherImages.size()==bitmapArrayList.size()){
                        Log.i("size3","true");
                        imageView.setImageBitmap(bitmapArrayList.get(animationCounter[0]));
                    }else {

                        Picasso.with(getContext()).load(imageSwitcherImages.get(animationCounter[0]).getThumbnail()).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                imageView.setImageBitmap(bitmap);
                                bitmapArrayList.add(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                    }


                    //imageView.startAnimation(zoomin);
                    imageView.startAnimation(zoomout);

                    imageView.startAnimation(set);
                    animationCounter[0]=animationCounter[0]+1;
                    animationCounter[0] %= imageSwitcherImages.size();
                }
                Log.i("size",bitmapArrayList.size()+"");
                Log.i("size1",imageSwitcherImages.size()+"");

                imageSwitcherHandler.postDelayed(this, 6000);
            }
        });

        return view;
    }

    private void fetchInternationalNews()
    {
        Call<PostsResponse> call = apiInterface.getCategoryById("3216");

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response)
            {
                progressDialog.cancel();
                posts = response.body().getPosts();
                imageSwitcherImages=posts;
                for(Posts post: posts)
                    post.setShowShimmer(true);
                mAdapter = new RecentNewsAdapter(posts , getContext());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(Posts post: posts)
                            post.setShowShimmer(false);
                        mAdapter.notifyDataSetChanged();
                    }
                },3000);
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                progressDialog.cancel();
            }
        });
    }

}
