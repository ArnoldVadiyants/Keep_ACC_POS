package com.keepaccpos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keepaccpos.R;
import com.keepaccpos.interfaces.BillItemClickListener;
import com.keepaccpos.network.data.BillBody;

/**
 * Created by Arnold on 23.07.2016.
 */


public class BillFeedViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public TextView count;
    public TextView price;
    public TextView sum;
    public LinearLayout container;


    public BillFeedViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.check_list_item_name);
        count = (TextView)itemView.findViewById(R.id.check_list_item_count);
        price = (TextView)itemView.findViewById(R.id.check_list_item_price);
        sum = (TextView)itemView.findViewById(R.id.check_list_item_sum);
        container = (LinearLayout) itemView.findViewById(R.id.check_list_item_container);




    }
    public void bind(final BillBody item, final BillItemClickListener listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

}