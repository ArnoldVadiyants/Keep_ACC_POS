package com.keepaccpos.adapters;

/**
 * Created by Arnold on 23.07.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.BanquetItemClickListener;
import com.keepaccpos.network.data.Banquet;
import com.keepaccpos.view_holders.BanquetFeedViewHolder;

import java.util.List;

public class BanquetFeedAdapter extends RecyclerView.Adapter<BanquetFeedViewHolder> {

    private List<Banquet> itemList;
    private final BanquetItemClickListener listener;

    public BanquetFeedAdapter(List<Banquet> itemList, BanquetItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public BanquetFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banquet_list_item, null);
        BanquetFeedViewHolder rcv = new BanquetFeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(BanquetFeedViewHolder holder, int position) {
        holder.bind(itemList.get(position), listener);
        String billNum = itemList.get(position).getBillNum();
        String state = itemList.get(position).getState();
        String dateTimeStart = itemList.get(position).getDateTimeStart();
        String dateTimeFinish = itemList.get(position).getDateTimeFinish();
        String staff = itemList.get(position).getStaff();
        String mobile = itemList.get(position).getMobile();
        String modifier = itemList.get(position).getModifier();
        String dateTime = itemList.get(position).getDateTime();
        String client = itemList.get(position).getClient();
        String hall = itemList.get(position).getHall();
        String countGuest = itemList.get(position).getCountGuest();
        String discount = itemList.get(position).getDiscount();
        String prepay = itemList.get(position).getPrepay();
        String sumPrice = itemList.get(position).getSumPrice();
        String reduction = itemList.get(position).getReduction();
        String total = itemList.get(position).getTotal();

        holder.billNum.setText(billNum);
        holder.state.setText(state);
        holder.dateStart.setText(dateTimeStart);
        holder.dateFinish.setText(dateTimeFinish);
        holder.staff.setText(staff);
        holder.mobile.setText(mobile);
        holder.changes.setText(modifier);
        holder.date.setText(dateTime);
        holder.client.setText(client);
        holder.hall.setText(hall);
        holder.countGuest.setText(countGuest);
        holder.discount.setText(discount);
        holder.prepay.setText(prepay);
        holder.sumPrice.setText(sumPrice);
        holder.reduction.setText(reduction);
        holder.total.setText(total);


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
