package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Model.AuthorLink;
import com.developinggeek.thebetterlawyernewsapp.R;

import java.util.ArrayList;

/**
 * Created by DELL-PC on 8/11/2017.
 */

public class ReadNewsAuthorListViewAdapter extends ArrayAdapter<AuthorLink>
{
    Context mContext;
    ArrayList<AuthorLink> mAuthors;

    public ReadNewsAuthorListViewAdapter(@NonNull Context context, ArrayList<AuthorLink> authors) {
        super(context,0);
        mContext= context;
        mAuthors= authors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        convertView= layoutInflater.inflate(R.layout.author_link_layout,parent,false);
        ((TextView) convertView.findViewById(R.id.read_news_activity_author_name_textview)).setText(mAuthors.get(position).getName());
        ((TextView) convertView.findViewById(R.id.read_news_activity_author_description_textview)).setText(mAuthors.get(position).getDescription());
        return convertView;
    }

    @Override
    public int getCount() {
        return mAuthors.size();
    }


}
