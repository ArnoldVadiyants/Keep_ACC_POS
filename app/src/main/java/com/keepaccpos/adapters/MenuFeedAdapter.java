package com.keepaccpos.adapters;

/**
 * Created by Arnold on 23.07.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.IMenu;
import com.keepaccpos.interfaces.MenuItemClickListener;
import com.keepaccpos.utils.DisplayImageLoaderOptions;
import com.keepaccpos.view_holders.MenuFeedViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MenuFeedAdapter extends RecyclerView.Adapter<MenuFeedViewHolder> {

    private List<IMenu> itemList;
    private final MenuItemClickListener listener;
    public MenuFeedAdapter(List<IMenu> itemList,MenuItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public MenuFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card_view, null);
        MenuFeedViewHolder rcv = new MenuFeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MenuFeedViewHolder holder, int position) {
        holder.bind(itemList.get(position), listener);
        String name = itemList.get(position).getName();
        String price = itemList.get(position).getPrice();
        String imageUrl = itemList.get(position).getImageUrl();
        holder.feedTitle.setText(name);
        if(price == null || price.isEmpty())
        {
            holder.feedPrice.setVisibility(View.GONE);
        }
        else
        {
            holder.feedPrice.setVisibility(View.VISIBLE);
            holder.feedPrice.setText(price);
        }
        if(imageUrl == null || imageUrl.isEmpty())
        {
            holder.feedImage.setImageResource(R.drawable.image_not_food_photo);
        }
        else
        {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(imageUrl,holder.feedImage, DisplayImageLoaderOptions.getInstance());
        }

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
