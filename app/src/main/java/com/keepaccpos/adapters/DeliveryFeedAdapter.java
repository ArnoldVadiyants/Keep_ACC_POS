package com.keepaccpos.adapters;

/**
 * Created by Arnold on 23.07.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.DeliveryItemClickListener;
import com.keepaccpos.network.data.Delivery;
import com.keepaccpos.view_holders.DeliveryFeedViewHolder;

import java.util.List;

public class DeliveryFeedAdapter extends RecyclerView.Adapter<DeliveryFeedViewHolder> {

    private List<Delivery> itemList;
    private final DeliveryItemClickListener listener;

    public DeliveryFeedAdapter(List<Delivery> itemList, DeliveryItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public DeliveryFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_item, null);
        DeliveryFeedViewHolder rcv = new DeliveryFeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(DeliveryFeedViewHolder holder, int position) {
        holder.bind(itemList.get(position), listener);
        String billNum = itemList.get(position).getBillNum();
        String state = itemList.get(position).getState();
        String date = itemList.get(position).getDate();
        String mobile = itemList.get(position).getMobile();
        String address = itemList.get(position).getAddress();
        String client = itemList.get(position).getClient();
        String timePreorder = itemList.get(position).getTimePreOrder();
        String datePreorder = itemList.get(position).getDatePreOrder();
        String discount = itemList.get(position).getDiscount();
        String sumPrice = itemList.get(position).getSumPrice();
        String reduction = itemList.get(position).getReduction();
        String total = itemList.get(position).getTotal();

        holder.billNum.setText(billNum);
        holder.state.setText(state);
        holder.date.setText(date);
        holder.address.setText(address);
        holder.mobile.setText(mobile);
        holder.client.setText(client);
        holder.date_preorder.setText(datePreorder);
        holder.time_preorder.setText(timePreorder);
        holder.discount.setText(discount);
        holder.sumPrice.setText(sumPrice);
        holder.reduction.setText(reduction);
        holder.total.setText(total);


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
