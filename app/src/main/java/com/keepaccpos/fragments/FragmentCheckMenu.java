package com.keepaccpos.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.adapters.MenuFeedAdapter;
import com.keepaccpos.helpers.ItemOffsetDecoration;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.interfaces.IMenu;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.BillBody;
import com.keepaccpos.network.data.BillHead;
import com.keepaccpos.network.data.BillLab;
import com.keepaccpos.network.data.Category;
import com.keepaccpos.network.data.CategoryLab;
import com.keepaccpos.network.data.DataPost;
import com.keepaccpos.network.data.DataBill;
import com.keepaccpos.network.data.DataCategory;
import com.keepaccpos.network.data.Menu;
import com.keepaccpos.interfaces.MenuItemClickListener;
import com.keepaccpos.network.model.Packet;
import com.keepaccpos.network.model.TableData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arnold on 22.07.2016.
 */
public class FragmentCheckMenu extends Fragment {
    private static final String TAG = FragmentCheckMenu.class.getCanonicalName();
    private ArrayList<IMenu> mMenuList = new ArrayList<>();
    private ImageButton mBackBtn;
    private static final int VERTICAL_ITEM_SPACE = 48;
    MenuFeedAdapter mMenuAdapter;
    private String mJournalId;
    private String mType;
    private List<BillBody> mBillBodies = new ArrayList<>();
    private List<BillHead> mBillHeads = new ArrayList<>();
    private IMainActivityObserve mIMainActivityObserve;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJournalId = getArguments().getString(FragmentCheck.ARG_JOURNAL_ID, "");
        mType = getArguments().getString(FragmentCheck.ARG_TYPE, "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_menu, container, false);
        GridLayoutManager gLayout = new GridLayoutManager(getActivity(), 4);
        mBackBtn = (ImageButton) rootView.findViewById(R.id.fragment_check_menu_back_button);
        mBackBtn.setVisibility(View.GONE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Category> categories = CategoryLab.getInstance().getCategories();
                List<IMenu> iMenus = new ArrayList<IMenu>();
                iMenus.addAll(categories);
                updateData(iMenus);
            }
        });
        RecyclerView mMenuRView = (RecyclerView) rootView.findViewById(R.id.fragment_check_menu_recycler_view);

        mMenuRView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));


        mMenuRView.setHasFixedSize(true);
        mMenuRView.setLayoutManager(gLayout);
        mMenuAdapter = new MenuFeedAdapter(mMenuList, new MenuItemClickListener() {
            @Override
            public void onItemClick(IMenu item) {
                if (item instanceof Category) {
                    loadBill(((Category) item).getName(), false);
                } else if (item instanceof Menu) {
                    addToBill(((Menu) item).getId(), ((Menu) item).getLinkPosmenu(), "1");
                }
            }
        });
        mMenuRView.setAdapter(mMenuAdapter);
        loadCategories(false);
        return rootView;
    }

    public static FragmentCheckMenu newInstance(String type,String journalId) {
        FragmentCheckMenu fragment = new FragmentCheckMenu();
        Bundle args = new Bundle();
        args.putString(FragmentCheck.ARG_JOURNAL_ID, journalId);
        args.putString(FragmentCheck.ARG_TYPE, type);

        fragment.setArguments(args);
        return fragment;
    }

    private void loadBill(final String category, boolean isUpdate) {
        List<Menu> menuList = BillLab.getInstance().getMenuList(mType, mJournalId, category);

        if (!isUpdate && menuList != null) {
            List<IMenu> iMenus = new ArrayList<IMenu>();
            iMenus.addAll(menuList);
            updateData(iMenus);

        } else {
            HashMap<String, String> tableDataMap = new HashMap<>();
            tableDataMap.put("cId", "0");
            tableDataMap.put("id", mJournalId);
            tableDataMap.put("type", mType);
            tableDataMap.put("category", category);
            final Activity activity = getActivity();
            Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.GetBill, new TableData(tableDataMap));

            final ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(getString(R.string.msg_loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            Call<DataBill> call = FactoryApi.getInstance(activity).getBill(packet);
            call.enqueue(new Callback<DataBill>() {
                @Override
                public void onResponse(Call<DataBill> call, Response<DataBill> response) {
                    progressDialog.dismiss();
                    DataBill dataBill = response.body();
                    Log.d(TAG, dataBill.getResult());
                    if (dataBill.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                        List<BillBody> billBodies = dataBill.getData().getBillBody();
                        List<BillHead> billHeads = dataBill.getData().getBillHead();
                        List<Menu> menuList = dataBill.getData().getMenu();
                        List<IMenu> iMenus = new ArrayList<IMenu>();
                        iMenus.addAll(menuList);
                        updateData(iMenus);
                        BillLab.getInstance().setBill(mType, mJournalId, billBodies, billHeads);
                        BillLab.getInstance().setMenuList(mType, mJournalId, category, menuList);
                        if (mIMainActivityObserve != null) {
                            mIMainActivityObserve.updateBill();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataBill> call, Throwable t) {

                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }

    }

    private void updateData(List<IMenu> menu) {
        if (menu.isEmpty()) {
            Toast.makeText(getActivity(), "Not items", Toast.LENGTH_SHORT).show();
            return;
        }
        mMenuList.clear();
        mMenuList.addAll(menu);
        mMenuAdapter.notifyDataSetChanged();
        if (menu.get(0) instanceof Menu) {
            mBackBtn.setVisibility(View.VISIBLE);
        } else if (menu.get(0) instanceof Category) {
            mBackBtn.setVisibility(View.GONE);
        }


    }

    private void addToBill(String itemId, final String category, String count) {
        HashMap<String, String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId", "0");
        tableDataMap.put("id", mJournalId);
        tableDataMap.put("type", mType);
        tableDataMap.put("itemId", itemId);
        tableDataMap.put("count", count);
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.AddItemToBill, new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(activity).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost = response.body();
                Log.d(TAG, dataPost.getResult());
                if (dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    if (dataPost.getData()) {
                        loadBill(category, true);
                    } else {
                        Toast.makeText(activity, dataPost.getTitle() + " : " + dataPost.getMessage(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(activity, dataPost.getTitle() + " : " + dataPost.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void loadCategories(boolean isUpdate) {
        List<Category> categories = CategoryLab.getInstance().getCategories();
        if (!isUpdate && !categories.isEmpty()) {
            List<IMenu> iMenus = new ArrayList<IMenu>();
            iMenus.addAll(categories);
            updateData(iMenus);
            return;
        }
        HashMap<String, String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId", "0");
        tableDataMap.put("type", "PosJournal");
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.GetCategories, new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataCategory> call = FactoryApi.getInstance(activity).getCategories(packet);
        call.enqueue(new Callback<DataCategory>() {
            @Override
            public void onResponse(Call<DataCategory> call, Response<DataCategory> response) {
                progressDialog.dismiss();
                DataCategory dataCategory = response.body();
                Log.d(TAG, dataCategory.getResult());
                if (dataCategory.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    List<Category> categoryList = dataCategory.getData();
                    CategoryLab.getInstance().setCategories(categoryList);
                    List<IMenu> iMenus = new ArrayList<IMenu>();
                    iMenus.addAll(categoryList);
                    updateData(iMenus);
                }
            }

            @Override
            public void onFailure(Call<DataCategory> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        //  super.onAttach(context);
        Log.d(TAG, "onAttach");
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;
            onAttach(a);
        }
     /*   if (context instanceof  com.materialdesign.FeedConsumer){
        // // Activity  activity=(Activity) context;
         //   if (activity instanceof com.materialdesign.FeedConsumer) {
            //    feedConsumer = (com.materialdesign.FeedConsumer) context;
            onAttach(hostActivity);
           }
       // }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IMainActivityObserve) {
            Log.d(TAG, "onAttachAct");
            mIMainActivityObserve = (IMainActivityObserve) activity;
        }
    }
}
