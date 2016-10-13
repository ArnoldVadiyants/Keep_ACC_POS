package com.keepaccpos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.DeliveryItemClickListener;
import com.keepaccpos.network.data.Delivery;

/**
 * Created by Arnold on 23.07.2016.
 */


public class DeliveryFeedViewHolder extends RecyclerView.ViewHolder{

    public TextView billNum;
    public TextView state;
    public TextView date;
    public TextView client;
    public TextView discount;
    public TextView address;
    public TextView mobile;
    public TextView date_preorder;
    public TextView time_preorder;
    public TextView sumPrice;
    public TextView reduction;
    public TextView total;
    public DeliveryFeedViewHolder(View itemView) {
        super(itemView);
         billNum = (TextView)itemView.findViewById(R.id.delivery_item_check_number);
         state =(TextView)itemView.findViewById(R.id.delivery_item_state);
        date =(TextView)itemView.findViewById(R.id.delivery_item_date);
        client =(TextView)itemView.findViewById(R.id.delivery_item_client);
        discount =(TextView)itemView.findViewById(R.id.delivery_item_discount);
        address =(TextView)itemView.findViewById(R.id.delivery_item_address);
        mobile =(TextView)itemView.findViewById(R.id.delivery_item_mobile_number);
        date_preorder =(TextView)itemView.findViewById(R.id.delivery_item_date_preorder);
        time_preorder =(TextView)itemView.findViewById(R.id.delivery_item_time_preorder);
        sumPrice =(TextView)itemView.findViewById(R.id.delivery_item_sum_price);
        reduction =(TextView)itemView.findViewById(R.id.delivery_item_reduction);
        total =(TextView)itemView.findViewById(R.id.delivery_item_total);

    }
    public void bind(final Delivery item, final DeliveryItemClickListener listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

}