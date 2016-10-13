package com.keepaccpos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.BanquetItemClickListener;
import com.keepaccpos.network.data.Banquet;

/**
 * Created by Arnold on 23.07.2016.
 */


public class BanquetFeedViewHolder extends RecyclerView.ViewHolder{

    public TextView billNum;
    public TextView state;
    public TextView dateStart;
    public TextView dateFinish;
    public TextView staff;
    public TextView mobile;
    public TextView changes;
    public TextView date;
    public TextView client;
    public TextView hall;
    public TextView discount;
    public TextView countGuest;
    public TextView prepay;
    public TextView sumPrice;
    public TextView reduction;
    public TextView total;
    public BanquetFeedViewHolder(View itemView) {
        super(itemView);
         billNum = (TextView)itemView.findViewById(R.id.banquet_item_bill_number);
         state =(TextView)itemView.findViewById(R.id.banquet_item_state);
         dateStart =(TextView)itemView.findViewById(R.id.banquet_item_date_start);
         dateFinish =(TextView)itemView.findViewById(R.id.banquet_item_date_finish);
         staff =(TextView)itemView.findViewById(R.id.banquet_item_staff);
         mobile =(TextView)itemView.findViewById(R.id.banquet_item_mobile_number);
         changes =(TextView)itemView.findViewById(R.id.banquet_item_changes);
         date =(TextView)itemView.findViewById(R.id.banquet_item_date);
         client =(TextView)itemView.findViewById(R.id.banquet_item_client);
         hall =(TextView)itemView.findViewById(R.id.banquet_item_check_hall);
         countGuest =(TextView)itemView.findViewById(R.id.banquet_item_count_guest);
         discount =(TextView)itemView.findViewById(R.id.banquet_item_discount);
        prepay =(TextView)itemView.findViewById(R.id.banquet_item_prepay);
        sumPrice =(TextView)itemView.findViewById(R.id.banquet_item_sum_price);
        reduction =(TextView)itemView.findViewById(R.id.banquet_item_reduction);
        total =(TextView)itemView.findViewById(R.id.banquet_item_total);

    }
    public void bind(final Banquet item, final BanquetItemClickListener listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

}