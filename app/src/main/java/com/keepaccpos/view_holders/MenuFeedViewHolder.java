package com.keepaccpos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keepaccpos.R;

/**
 * Created by Arnold on 23.07.2016.
 */


public class MenuFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView feedImage;
    public TextView feedTitle;
    public TextView feedPrice;

    public MenuFeedViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        feedImage = (ImageView)itemView.findViewById(R.id.feed_image);
        feedTitle = (TextView)itemView.findViewById(R.id.feed_title);
        feedPrice = (TextView)itemView.findViewById(R.id.feed_price);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}