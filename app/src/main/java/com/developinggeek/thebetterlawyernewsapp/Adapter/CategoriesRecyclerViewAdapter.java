package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Activity.SingleCategory;
import com.developinggeek.thebetterlawyernewsapp.Model.Categories;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Pradumn on 14-Aug-17.
 */

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Categories> mCategories;

    public CategoriesRecyclerViewAdapter(Context context, ArrayList<Categories> categories) {
        mContext= context;
        mCategories= categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.category_recycler_view_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(mCategories.get(position).getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext, SingleCategory.class);
                intent.putExtra(AppConstants.SINGLE_CATEGORY_ID_STRING,mCategories.get(position).getId()+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            view= itemView;
            textView= (TextView) itemView.findViewById(R.id.category_textView_in_readNewsActivity);
        }
    }
}
