package com.keepaccpos.adapters;

/**
 * Created by Arnold on 23.07.2016.
 */

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.R;
import com.keepaccpos.network.data.BillBody;
import com.keepaccpos.interfaces.BillItemClickListener;
import com.keepaccpos.view_holders.BillFeedViewHolder;

import java.util.List;

public class BillFeedAdapter extends RecyclerView.Adapter<BillFeedViewHolder> {
    private Context mContext;
    private List<BillBody> itemList;
    private final BillItemClickListener listener;

    public BillFeedAdapter(Context context, List<BillBody> itemList,BillItemClickListener listener ) {
        this.itemList = itemList;
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public BillFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list_item, null);
        BillFeedViewHolder rcv = new BillFeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(BillFeedViewHolder holder, int position) {
        holder.bind(itemList.get(position), listener);
        String name = itemList.get(position).getName();
        String count = itemList.get(position).getCount();
        String price = itemList.get(position).getPrice();
        String sum = itemList.get(position).getSumPrice();
        String sold = itemList.get(position).getSold();
        int countInt = 0;
        try {
            countInt = (int) Double.parseDouble(count);
            holder.count.setText("" + countInt);
        } catch (NumberFormatException e) {
            holder.count.setText(count);
        }
        holder.name.setText(name);
        holder.price.setText(price);
        holder.sum.setText(sum);
        boolean soldBool = false;
        try {
            soldBool = Boolean.parseBoolean(sold);
        } catch (NumberFormatException e) {
        }
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (soldBool) {
                color = mContext.getResources().getColor(R.color.colorItemSelected, null);
            } else {
                color = mContext.getResources().getColor(android.R.color.transparent, null);
            }
        } else {
            if (soldBool) {
                color = mContext.getResources().getColor(R.color.colorItemSelected);
            } else {
                color = mContext.getResources().getColor(android.R.color.transparent);
            }
        }
        holder.container.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
