package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Model.Category;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoriesAdapter extends BaseAdapter {
    public CategoriesAdapter(List<Category> categoryList, Context mContext) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    private List<Category> categoryList;
    Context mContext;
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView=view;
        if(rowView==null){
            rowView= LayoutInflater.from(mContext).inflate(R.layout.layout_item,null);
            TextView name=(TextView) rowView.findViewById(R.id.label);
            ImageView image=(ImageView) rowView.findViewById(R.id.image);

            Picasso.with(mContext).load(categoryList.get(i).getImageURL()).into(image);
            name.setText(categoryList.get(i).getName());



        }
        return rowView;
    }
}
