package com.keepaccpos.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.adapters.BillFeedAdapter;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.interfaces.BillItemClickListener;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.Banquet;
import com.keepaccpos.network.data.BanquetLab;
import com.keepaccpos.network.data.BillBody;
import com.keepaccpos.network.data.BillHead;
import com.keepaccpos.network.data.BillLab;
import com.keepaccpos.network.data.DataBill;
import com.keepaccpos.network.data.DataPost;
import com.keepaccpos.network.data.Delivery;
import com.keepaccpos.network.data.DeliveryLab;
import com.keepaccpos.network.data.TableLab;
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
public class FragmentCheck extends Fragment {
    public  final static String ARG_BLOCK_NAME = "block_name";
    public  final static String ARG_TABLE_ID = "table_id";
    public  final static String ARG_JOURNAL_ID = "journal_id";
    public  final static String ARG_TYPE= "type";

    private static final String TAG =FragmentCheck.class.getCanonicalName();


    private static final int VERTICAL_ITEM_SPACE = 48;
    private TextView mCheckNumberTV;
    private TextView mCheckSumlTV;
    private TextView mCheckReductionTV;
    private TextView mCheckTotalTV;
    private RecyclerView mCheckRecyclerView;
    private Button mCheckCancelRefine;
    private Button mCheckDetails;
    private Button mCheckRefine;
    private Button mCheckClose;
    private FragmentCheckMenu mFragmentCheckMenu;
    BillFeedAdapter mBillAdapter;
    private IMainActivityObserve mIMainActivityObserve;

    // private String mTableId;
   // private String mBlockName;
    private String mType;
    private String mJournalId;
    private String mBlockName;

    private List<BillBody> mBillBodies = new ArrayList<>();
    private List<BillHead> mBillHeads = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //    mTableId =  getArguments().getString(ARG_TABLE_ID,"");
      mBlockName =  getArguments().getString(ARG_BLOCK_NAME,"");
        mJournalId =  getArguments().getString(ARG_JOURNAL_ID,"");
        mType =  getArguments().getString(ARG_TYPE,"");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_check, container, false);
        mCheckNumberTV = (TextView) rootView.findViewById(com.keepaccpos.R.id.fragment_check_item_number);
        mCheckSumlTV = (TextView) rootView.findViewById(R.id.fragment_check_sum);
        mCheckReductionTV = (TextView) rootView.findViewById(R.id.fragment_check_reduction);
        mCheckTotalTV = (TextView) rootView.findViewById(R.id.fragment_check_total);
        mCheckRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_check_recycler_view);
        mCheckDetails = (Button) rootView.findViewById(R.id.fragment_check_settings_btn);
        mCheckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails();
            }
        });
        mCheckRefine = (Button) rootView.findViewById(R.id.fragment_check_sent_to_realization_btn);
        mCheckCancelRefine = (Button) rootView.findViewById(R.id.fragment_check_cancel_refine);
        mCheckCancelRefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.button_cancel_refine);
                builder.setNegativeButton(R.string.button_cancel_refine_change, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        refine(true);
                    }
                });
                builder.setPositiveButton(R.string.button_cancel_refine_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      cancelFull();
                    }
                });
// Set other dialog properties

// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        mCheckRefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refine(false);
            }
        });
        mCheckClose = (Button) rootView.findViewById(R.id.fragment_check_close_btn);
        mCheckClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.button_close_check);
                builder.setPositiveButton(R.string.button_cash, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        close(true);
                    }
                });
                builder.setNegativeButton(R.string.button_cashless, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        close(false);
                    }
                });
// Set other dialog properties

// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        FragmentManager fm = getChildFragmentManager();
        mFragmentCheckMenu = FragmentCheckMenu.newInstance(mType,mJournalId);

        mCheckRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCheckRecyclerView.setLayoutManager(mLayoutManager);
        mBillAdapter = new BillFeedAdapter(getActivity(),mBillBodies, new BillItemClickListener() {
            @Override
            public void onItemClick(BillBody item) {
                bill_item_click(item.getId());
            }
        });
        mCheckRecyclerView.setAdapter(mBillAdapter);
        loadBill(false);
        fm.beginTransaction().replace(R.id.fragment_check_feed_container, mFragmentCheckMenu)
                .commit();


        return rootView;
    }

   /* public static FragmentCheck newInstance(String blockName, String tableId) {
        FragmentCheck fragment = new FragmentCheck();
        Bundle args = new Bundle();
        args.putString(ARG_TABLE_ID, tableId);
        args.putString(ARG_BLOCK_NAME, blockName);
        fragment.setArguments(args);
        return fragment;
    }*/
    public void showDetails()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        View view = getActivity().getLayoutInflater().inflate(R.layout.bill_details_layout, null);
        TextView num = (TextView) view.findViewById(R.id.bill_det_bill_num);
        TextView table = (TextView) view.findViewById(R.id.bill_det_table);
        TextView state = (TextView) view.findViewById(R.id.bill_det_state);
        TextView date = (TextView) view.findViewById(R.id.bill_det_date);
        TextView waiter = (TextView) view.findViewById(R.id.bill_det_waiter);
        TextView client = (TextView) view.findViewById(R.id.bill_det_client);
        TextView guestCount = (TextView) view.findViewById(R.id.bill_det_count_guest);
        TextView sumPrice = (TextView) view.findViewById(R.id.bill_det_sum_price);
        TextView reduction = (TextView) view.findViewById(R.id.bill_det_reduction);
        TextView total = (TextView) view.findViewById(R.id.bill_det_total);
        TextView dateCreated = (TextView) view.findViewById(R.id.bill_det_created_date);
        String numStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_AUTOCALC_INDEX, mBillHeads);
        String tableStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_TABLE_NAME, mBillHeads);
        String stateStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_STATE, mBillHeads);
        String dateStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_DATETIME, mBillHeads);
        String waiterStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_WAITER, mBillHeads);
        String clientStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_CLIENT, mBillHeads);
        String guestCountStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_COUNT_GUEST, mBillHeads);
        String sumPriceStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_SUM_PRICE, mBillHeads);
        String reductionStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_REDUCTION, mBillHeads);
        String totalStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_TOTAL, mBillHeads);
        String dateCreatedStr = BillLab.getInstance().getBillHeadValue(BanquetLab.ARG_DATE_CREATED, mBillHeads);
        num.setText(numStr);
        table.setText(tableStr);
        waiter.setText(waiterStr);
        state.setText(stateStr);
        date.setText(dateStr);
        client.setText(clientStr);
        guestCount.setText(guestCountStr);
        sumPrice.setText(sumPriceStr);
        reduction.setText(reductionStr);
        total.setText(totalStr);
        dateCreated.setText(dateCreatedStr);
        builder.setTitle(R.string.button_check_details);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

   public void bill_item_click(final String itemId) {
       BillBody billBody = BillLab.getInstance().getBillBody(itemId,mBillBodies);
       String billName = billBody.getName();
       String bill_item_count = billBody.getCount();
       final int countCurrent;
       try
       {
           countCurrent =(int) Double.parseDouble(bill_item_count);
       }catch (NumberFormatException e)
       {
           return;
       }
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
       View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_biil_click, null);
       ImageButton plus = (ImageButton) view.findViewById(R.id.dialog_bill_click_plus_btn);
       ImageButton minus = (ImageButton) view.findViewById(R.id.dialog_bill_click_minus_btn);
       Button addMode = (Button) view.findViewById(R.id.dialog_bill_click_add_mode);
       final EditText countText = (EditText) view.findViewById(R.id.dialog_bill_click_count_tv);
       countText.setFocusable(false);
       countText.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {

               countText.setFocusableInTouchMode(true);

               return false;
           }
       });
       countText.setText(""+ countCurrent);
       plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String val = countText.getText().toString();
               int  count = 0;
               try
               {
                 count = Integer.parseInt(val);
               }catch (NumberFormatException e)
               {
                   return;
               }
               count++;
               countText.setText(""+count);
           }
       });
       minus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String val = countText.getText().toString();
               int  count = 0;
               try
               {
                   count = Integer.parseInt(val);
               }catch (NumberFormatException e)
               {
                   return;
               }
               count--;
               countText.setText(""+count);
           }
       });
       builder.setTitle(billName);
       builder.setView(view);
       builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User clicked OK button
               String val = countText.getText().toString();
               int  count = 0;
               try
               {
                   count = Integer.parseInt(val);
               }catch (NumberFormatException e)
               {
                   return;
               }
               if(countCurrent == count)
               {
                   return;
               }
               updateBillPost(itemId,""+count);
           }
       });
       builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User cancelled the dialog
           }
       });
// Set other dialog properties

// Create the AlertDialog
       AlertDialog dialog = builder.create();
       dialog.show();
   }

   public static FragmentCheck newInstance(String type, String blockName,String journalId) {
       FragmentCheck fragment = new FragmentCheck();
       Bundle args = new Bundle();
       args.putString(ARG_BLOCK_NAME, blockName);
       args.putString(ARG_JOURNAL_ID, journalId);
       args.putString(ARG_TYPE, type);

       fragment.setArguments(args);
       return fragment;
   }
    private void updateViews()
    {
        BillLab billLab = BillLab.getInstance();
        String checkNumber = "null";
        try
        {
            checkNumber = billLab.getBillHead(BillHead.AUTOCALC_INDEX,mBillHeads).getValue();

        }
        catch (NullPointerException e)
        {
        }
        String sum = "null";
        try
        {
            sum = billLab.getBillHead(BillHead.SUM_PRICE,mBillHeads).getValue();

        }
        catch (NullPointerException e)
        {
        }
        String reduction = "null";
        try
        {
            reduction = billLab.getBillHead(BillHead.REDUCTION,mBillHeads).getValue();

        }
        catch (NullPointerException e)
        {
        }
        String total = "null";
        try
        {
            total = billLab.getBillHead(BillHead.TOTAL,mBillHeads).getValue();

        }
        catch (NullPointerException e)
        {
        }


        mCheckNumberTV.setText(checkNumber);
        mCheckSumlTV.setText(sum);
        mCheckTotalTV.setText(total);
        mCheckReductionTV.setText(reduction);
       // List<Table> tables = TableLab.getInstance().getTables(mBlockName);
        //String checkNum = TableLab.getInstance().getTable(mTableId,tables).get;
   //    mCheckNumberTV.setText(mTableId);
    }
    private void loadBill(boolean isUpdate) {
        List<BillBody> billBodies = BillLab.getInstance().getBillBodies(mType, mJournalId);
        List<BillHead> billHeads = BillLab.getInstance().getBillHeads(mType, mJournalId);

        if (!isUpdate&&billBodies !=null && billHeads !=null) {
            updateData(billBodies,billHeads);
        } else {
            HashMap<String, String> tableDataMap = new HashMap<>();
            tableDataMap.put("cId", "0");
            tableDataMap.put("id", mJournalId);
            tableDataMap.put("type", mType);
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
                        BillLab.getInstance().setBill(mType, mJournalId, billBodies, billHeads);
                        updateData(billBodies,billHeads);

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
    private void updateData(List<BillBody> billBodies, List<BillHead> billHeads)
    {
        mBillBodies.clear();
        mBillBodies.addAll(billBodies);
        mBillAdapter.notifyDataSetChanged();
        mBillHeads.clear();
        mBillHeads.addAll(billHeads);
        updateViews();
    }
    public void updateData()
    {
        List<BillBody> billBodies = BillLab.getInstance().getBillBodies(mType, mJournalId);
        List<BillHead> billHeads = BillLab.getInstance().getBillHeads(mType, mJournalId);
        if(billBodies == null || billHeads == null)
        {
            return;
        }
        mBillBodies.clear();
        mBillBodies.addAll(billBodies);
        mBillAdapter.notifyDataSetChanged();
        mBillHeads.clear();
        mBillHeads.addAll(billHeads);
        updateViews();
    }
    private void updateBillPost(String itemId, String count)
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","0");
        tableDataMap.put("id", mJournalId);
        tableDataMap.put("rowId",itemId);
        tableDataMap.put("type",mType);
        tableDataMap.put("itemId","");
        tableDataMap.put("name",itemId);
        tableDataMap.put("count",count);
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity,PacketPost.PacketType.UpdateItemBill,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(activity).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost =  response.body();
                Log.d(TAG, dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
                {
                    if(dataPost.getData())
                    {
                        loadBill(true);
                    }

                    return;
                }
                String descr = dataPost.getDescription();
                Toast.makeText(activity, dataPost.getTitle() + " : " + getDescriptionError(descr), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void refine(boolean isCancel)
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","0");
        tableDataMap.put("id", mJournalId);
        tableDataMap.put("type",mType);
        final Activity activity = getActivity();
        PacketPost.PacketType packetType;
        if(isCancel)
        {
            packetType = PacketPost.PacketType.CancelRefine;
        }
        else
        {
            packetType = PacketPost.PacketType.Refine;

        }
        Packet packet = PacketPost.getPacket(activity,packetType,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(activity).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost =  response.body();
                Log.d(TAG, dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
                {
                    if(dataPost.getData())
                    {
                        loadBill(true);
                    }
                    return;
                }
                String descr = dataPost.getDescription();
                Toast.makeText(activity, dataPost.getTitle() + " : " + getDescriptionError(descr), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void close(boolean isCashType)
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","0");
        tableDataMap.put("id", mJournalId);
        tableDataMap.put("type",mType);
        String cashType;
        if(isCashType)
        {
            cashType = "cash";
        }
        else
        {
            cashType = "cashless";
        }
        tableDataMap.put("closeType",cashType);

        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity,PacketPost.PacketType.Close,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(activity).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost =  response.body();
                Log.d(TAG, dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    if (dataPost.getData()) {
                        closeBill();
                        getActivity().onBackPressed();
                        return;
                    }

                }
                    String descr = dataPost.getDescription();
                    Toast.makeText(activity, dataPost.getTitle() + " : " +getDescriptionError(descr) ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private  String getDescriptionError(String description)
    {
       String tmp = "";
        int index =  description.indexOf("<");
        try
        {
            description =description.substring(0, index);
            tmp = description;
        }
        catch (IndexOutOfBoundsException e)
        {

        }
        return tmp;
    }

    private void cancelFull()
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","0");
        tableDataMap.put("id", mJournalId);
        tableDataMap.put("type",mType);

        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity,PacketPost.PacketType.CancelFull,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(activity).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost =  response.body();
                Log.d(TAG, dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    if (dataPost.getData()) {
                        closeBill();
                        getActivity().onBackPressed();
                        return;
                    }
                    String descr = dataPost.getDescription();
                    Toast.makeText(activity, dataPost.getTitle() + " : " + getDescriptionError(descr), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
    private void closeBill()
    {
        BillLab.getInstance().removeBill(mType, mJournalId);
        switch (mType)
        {
            case PacketPost.TYPE_TABLE:
                TableLab.getInstance().removeTables(mBlockName);
                break;
            case PacketPost.TYPE_BANQUETTE:
                BanquetLab.getInstance().removeData();
          //      BanquetLab.getInstance().removeBanquet(mJournalId);
                break;
            case PacketPost.TYPE_DELIVERY:
                DeliveryLab.getInstance().removeDeliveries();
            //    DeliveryLab.getInstance().removeDelivery(mJournalId);
                break;
        }
    }

    @Override
    public void onDestroy() {

        String sum = "null";
        String discount = "null";
        String reduction = "null";
        String total = "null";
        BillHead billHead;
        switch (mType)
        {
            case PacketPost.TYPE_BANQUETTE:
                 billHead = BillLab.getInstance().getBillHead(BillHead.SUM_PRICE, mBillHeads);
                if (billHead != null) {
                    sum = billHead.getValue();
                }

                billHead = BillLab.getInstance().getBillHead(BillHead.DISCOUNT, mBillHeads);
                if (billHead != null) {
                    discount = billHead.getValue();
                }
                billHead = BillLab.getInstance().getBillHead(BillHead.REDUCTION, mBillHeads);
                if (billHead != null) {
                    reduction = billHead.getValue();
                }
                billHead = BillLab.getInstance().getBillHead(BillHead.TOTAL, mBillHeads);
                if (billHead != null) {
                    total = billHead.getValue();
                }


                Banquet banquet = BanquetLab.getInstance().getBanquet(mJournalId);
                if (banquet != null) {
                    banquet.setSumPrice(sum);
                    banquet.setReduction(reduction);
                    banquet.setTotal(total);
                    banquet.setDiscount(discount);
                }
                break;
            case PacketPost.TYPE_DELIVERY:
                 billHead = BillLab.getInstance().getBillHead(BillHead.SUM_PRICE, mBillHeads);
                if (billHead != null) {
                    sum = billHead.getValue();
                }
                billHead = BillLab.getInstance().getBillHead(BillHead.DISCOUNT, mBillHeads);
                if (billHead != null) {
                    discount = billHead.getValue();
                }
                billHead = BillLab.getInstance().getBillHead(BillHead.REDUCTION, mBillHeads);
                if (billHead != null) {
                    reduction = billHead.getValue();
                }
                billHead = BillLab.getInstance().getBillHead(BillHead.TOTAL, mBillHeads);
                if (billHead != null) {
                    total = billHead.getValue();
                }


                Delivery delivery = DeliveryLab.getInstance().getDelivery(mJournalId);
                if (delivery != null) {
                    delivery.setSumPrice(sum);
                    delivery.setReduction(reduction);
                    delivery.setTotal(total);
                    delivery.setDiscount(discount);
                }
                break;
        }
        super.onDestroy();
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
