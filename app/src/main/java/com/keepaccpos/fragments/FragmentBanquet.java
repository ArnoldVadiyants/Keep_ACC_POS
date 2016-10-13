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
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.adapters.BanquetFeedAdapter;
import com.keepaccpos.helpers.ItemOffsetDecoration;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.interfaces.BanquetItemClickListener;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.Banquet;
import com.keepaccpos.network.data.BanquetBody;
import com.keepaccpos.network.data.BanquetLab;
import com.keepaccpos.network.data.DataBanquet;
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
public class FragmentBanquet extends Fragment {
    private static final String TAG = FragmentBanquet.class.getSimpleName();
    private IMainActivityObserve mIMainActivityObserve;

    private ArrayList<Banquet> mBanquets = new ArrayList<>();
    private RecyclerView mBanquetRecyclerView;
    BanquetFeedAdapter mBanquetFeedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banquet, container, false);

        mBanquetRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragm_banquet_recycler_view);


        mBanquetRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBanquetRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        mBanquetRecyclerView.setLayoutManager(mLayoutManager);
        mBanquetFeedAdapter = new BanquetFeedAdapter(mBanquets, new BanquetItemClickListener() {
            @Override
            public void onItemClick(Banquet item) {
                if (mIMainActivityObserve != null) {
                    mIMainActivityObserve.createFragmentCheck(PacketPost.TYPE_BANQUETTE, null, item.getId());
                }
            }
        });
        mBanquetRecyclerView.setAdapter(mBanquetFeedAdapter);
        loadBanquette();

        return rootView;
    }


    private void loadBanquette() {
         List<Banquet> banquets = BanquetLab.getInstance().getBanquets();
        if (!banquets.isEmpty()) {
            updateData(banquets);
            return;
        }
        HashMap<String, String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId", "0");
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.GetBanquette, new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataBanquet> call = FactoryApi.getInstance(activity).getBanquette(packet);
        call.enqueue(new Callback<DataBanquet>() {
            @Override
            public void onResponse(Call<DataBanquet> call, Response<DataBanquet> response) {
                progressDialog.dismiss();
                DataBanquet dataBanquet = response.body();
                Log.d(TAG, dataBanquet.getResult());
                if (dataBanquet.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    BanquetLab banquetLab = BanquetLab.getInstance();
                    List<List<BanquetBody>> bodyList = dataBanquet.getData().getBody().get(0);
                    List<Banquet> banquetList = new ArrayList<Banquet>();
                    for (List<BanquetBody> banquetBody : bodyList) {
                        String id = banquetLab.getValue(BanquetLab.ARG_ID, banquetBody);
                        String billNum = banquetLab.getValue(BanquetLab.ARG_AUTOCALC_INDEX, banquetBody);
                        String state = banquetLab.getValue(BanquetLab.ARG_STATE, banquetBody);
                        String dateTimeStart = banquetLab.getValue(BanquetLab.ARG_DATETIME_START, banquetBody);
                        String dateTimeFinish = banquetLab.getValue(BanquetLab.ARG_DATETIME_END, banquetBody);
                        String staff = banquetLab.getValue(BanquetLab.ARG_STAFF, banquetBody);
                        String mobile = banquetLab.getValue(BanquetLab.ARG_CONTACT_NUMBER, banquetBody);
                        String modifier = banquetLab.getValue(BanquetLab.ARG_MODIFIER, banquetBody);
                        String dateTime = banquetLab.getValue(BanquetLab.ARG_DATETIME, banquetBody);
                        String client = banquetLab.getValue(BanquetLab.ARG_CLIENT, banquetBody);
                        String hall = banquetLab.getValue(BanquetLab.ARG_HALL, banquetBody);
                        String countGuest = banquetLab.getValue(BanquetLab.ARG_COUNT_GUEST, banquetBody);
                        String discount = banquetLab.getValue(BanquetLab.ARG_DISCOUNT, banquetBody);
                        String prepay = banquetLab.getValue(BanquetLab.ARG_PREPAY, banquetBody);
                        String sumPrice = banquetLab.getValue(BanquetLab.ARG_SUM_PRICE, banquetBody);
                        String reduction = banquetLab.getValue(BanquetLab.ARG_REDUCTION, banquetBody);
                        String total = banquetLab.getValue(BanquetLab.ARG_TOTAL, banquetBody);
                        Banquet banquet = new Banquet(id, billNum, state, dateTimeStart, dateTimeFinish, staff, mobile, modifier, dateTime, client, hall, countGuest, discount, prepay, sumPrice, reduction, total);
                        banquetList.add(banquet);
                    }
                    banquetLab.setBanquets(banquetList);
                    updateData(banquetList);
                }
            }

            @Override
            public void onFailure(Call<DataBanquet> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void updateData(List<Banquet> banquets) {
        mBanquets.clear();
        mBanquets.addAll(banquets);
        mBanquetFeedAdapter.notifyDataSetChanged();
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
