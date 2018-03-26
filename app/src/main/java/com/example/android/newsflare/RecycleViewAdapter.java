package com.example.android.newsflare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv Jain on 26-03-2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.Recycler> {
    private static final String TAG = "Bind" ;
    private ArrayList<NewsBrief> news;

    public RecycleViewAdapter(ArrayList<NewsBrief> news) {
        this.news = news;
    }
    public Recycler onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        itemView=layoutInflater.inflate(R.layout.activity_recycle,parent,false);

        return new Recycler(itemView);

    }
    @Override
    public void onBindViewHolder(Recycler holder, int position) {

        NewsBrief newsBrief = news.get(position);
        holder.newsTitle.setText(newsBrief.getNewsTitle());
        holder.description.setText(newsBrief.getDescription());
        holder.publishedAt.setText(newsBrief.getPublishedAt());
        Picasso.get().load(newsBrief.getNewsImageURL()).into(holder.newsImg);
//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        Log.e(TAG,"Position:"+position);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class Recycler extends RecyclerView.ViewHolder{

        TextView newsTitle;
        TextView description;
        TextView publishedAt;
        ImageView newsImg;
        LinearLayout container;

        public Recycler(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.TitleNews);
            description = itemView.findViewById(R.id.Description);
            publishedAt=itemView.findViewById(R.id.PublishedAt);
            newsImg=itemView.findViewById(R.id.ImgNews);
//            container=itemView.findViewById(R.id.container);
        }
    }
}
