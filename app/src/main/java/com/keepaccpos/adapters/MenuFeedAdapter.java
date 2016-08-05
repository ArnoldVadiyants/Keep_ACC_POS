package com.keepaccpos.adapters;

/**
 * Created by Arnold on 23.07.2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.models.FeedObject;
import com.keepaccpos.R;
import com.keepaccpos.view_holders.MenuFeedViewHolder;

import java.util.List;

public class MenuFeedAdapter extends RecyclerView.Adapter<MenuFeedViewHolder> {

    private List<FeedObject> itemList;
    private Context context;

    public MenuFeedAdapter(Context context, List<FeedObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public MenuFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card_view, null);
        MenuFeedViewHolder rcv = new MenuFeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MenuFeedViewHolder holder, int position) {
        holder.feedTitle.setText(itemList.get(position).getTitle());
        holder.feedPrice.setText(itemList.get(position).getPrice());
        holder.feedImage.setImageResource(itemList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
