package com.keepaccpos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.IMenu;
import com.keepaccpos.interfaces.MenuItemClickListener;

/**
 * Created by Arnold on 23.07.2016.
 */


public class MenuFeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView feedImage;
    public TextView feedTitle;
    public TextView feedPrice;

    public MenuFeedViewHolder(View itemView) {
        super(itemView);
        feedImage = (ImageView)itemView.findViewById(R.id.feed_image);
        feedTitle = (TextView)itemView.findViewById(R.id.feed_title);
        feedPrice = (TextView)itemView.findViewById(R.id.feed_price);

    }
    public void bind(final IMenu item, final MenuItemClickListener listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }


/*
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
*/

}