package com.keepaccpos.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.adapters.DeliveryFeedAdapter;
import com.keepaccpos.helpers.ItemOffsetDecoration;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.interfaces.DeliveryItemClickListener;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.DataDelivery;
import com.keepaccpos.network.data.DataOpen;
import com.keepaccpos.network.data.Delivery;
import com.keepaccpos.network.data.DeliveryBody;
import com.keepaccpos.network.data.DeliveryLab;
import com.keepaccpos.network.model.Packet;
import com.keepaccpos.network.model.TableData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arnold on 18.07.2016.
 */
public class FragmentDelivery extends Fragment {
    private static final String TAG = FragmentDelivery.class.getSimpleName();
    private IMainActivityObserve mIMainActivityObserve;
private Button mAddDeliveryBtn;
    private ArrayList<Delivery> mDeliveries = new ArrayList<>();
    private RecyclerView mDeliveryRecyclerView;
    DeliveryFeedAdapter mDeliveryFeedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_delivery, container, false);

        mDeliveryRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragm_delivery_recycler_view);
        mAddDeliveryBtn= (Button) rootView.findViewById(R.id.fragm_delivery_add_btn);
        mAddDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDelivery();
            }
        });
        mDeliveryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mDeliveryRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        mDeliveryRecyclerView.setLayoutManager(mLayoutManager);
        mDeliveryFeedAdapter = new DeliveryFeedAdapter(mDeliveries, new DeliveryItemClickListener() {
            @Override
            public void onItemClick(Delivery item) {
                if (mIMainActivityObserve != null) {
                    mIMainActivityObserve.createFragmentCheck(PacketPost.TYPE_DELIVERY, null, item.getId());
                }
            }
        });
        mDeliveryRecyclerView.setAdapter(mDeliveryFeedAdapter);
        loadDelivery(false);

        return rootView;
    }
    private void addDelivery()
    {
        HashMap<String, String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId", "0");
        tableDataMap.put("request", "new");
        tableDataMap.put("type", PacketPost.TYPE_DELIVERY);
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.OpenDelivery, new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataOpen> call = FactoryApi.getInstance(activity).open(packet);
        call.enqueue(new Callback<DataOpen>() {
            @Override
            public void onResponse(Call<DataOpen> call, Response<DataOpen> response) {
                progressDialog.dismiss();
                DataOpen dataOpen = response.body();
                Log.d(TAG, dataOpen.getResult());
                if (dataOpen.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    loadDelivery(true);
                    if(mIMainActivityObserve !=null)
                    {
                        mIMainActivityObserve.createFragmentCheck(PacketPost.TYPE_DELIVERY,null,""+dataOpen.getData().getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataOpen> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void loadDelivery(boolean isUpdate) {
         List<Delivery> deliveries = DeliveryLab.getInstance().getDeliveries();
        if (!isUpdate&&!deliveries.isEmpty()) {
            updateData(deliveries);
            return;
        }
        HashMap<String, String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId", "0");
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.GetDelivery, new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataDelivery> call = FactoryApi.getInstance(activity).getDelivery(packet);
        call.enqueue(new Callback<DataDelivery>() {
            @Override
            public void onResponse(Call<DataDelivery> call, Response<DataDelivery> response) {
                progressDialog.dismiss();
                DataDelivery dataDelivery = response.body();
                Log.d(TAG, dataDelivery.getResult());
                if (dataDelivery.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    DeliveryLab deliveryLab = DeliveryLab.getInstance();
                    List<List<DeliveryBody>> bodyList = dataDelivery.getData().getDeliveryBody().get(0);
                    List<Delivery> deliveryList = new ArrayList<Delivery>();
                    for (List<DeliveryBody> deliveryBody : bodyList) {
                        String id = deliveryLab.getValue(DeliveryLab.ARG_ID, deliveryBody);
                        String billNum = deliveryLab.getValue(DeliveryLab.ARG_AUTOCALC_INDEX, deliveryBody);
                        String state = deliveryLab.getValue(DeliveryLab.ARG_STATE, deliveryBody);
                        String date = deliveryLab.getValue(DeliveryLab.ARG_DATETIME, deliveryBody);
                        String discount = deliveryLab.getValue(DeliveryLab.ARG_DISCOUNT, deliveryBody);
                        String client = deliveryLab.getValue(DeliveryLab.ARG_CLIENT, deliveryBody);
                        String address = deliveryLab.getValue(DeliveryLab.ARG_ADDRESS, deliveryBody);
                        String mobile = deliveryLab.getValue(DeliveryLab.ARG_NUMBER_PHONE, deliveryBody);
                        String datePreOrder = deliveryLab.getValue(DeliveryLab.ARG_DATE_PREORDER, deliveryBody);
                        String timePreOrder = deliveryLab.getValue(DeliveryLab.ARG_TIME_PREORDER, deliveryBody);
                        String sumPrice = deliveryLab.getValue(DeliveryLab.ARG_SUM_PRICE, deliveryBody);
                        String reduction = deliveryLab.getValue(DeliveryLab.ARG_REDUCTION, deliveryBody);
                        String total = deliveryLab.getValue(DeliveryLab.ARG_TOTAL, deliveryBody);
                        Delivery delivery = new Delivery( id,  billNum,  state,  date,  client,  discount,  address,  mobile,  datePreOrder,  timePreOrder,  sumPrice,  reduction,  total);
                            deliveryList.add(delivery);
                    }
                    deliveryLab.setDeliveries(deliveryList);
                    updateData(deliveryList);
                }
            }

            @Override
            public void onFailure(Call<DataDelivery> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void updateData(List<Delivery> deliveries) {
        mDeliveries.clear();
        mDeliveries.addAll(deliveries);
        mDeliveryFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IMainActivityObserve) {
            Log.d(TAG, "onAttachAct");
            mIMainActivityObserve = (IMainActivityObserve) activity;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mIMainActivityObserve = null;
    }
}
