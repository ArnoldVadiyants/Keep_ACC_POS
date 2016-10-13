package com.keepaccpos.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.Block;
import com.keepaccpos.network.data.BlockLab;
import com.keepaccpos.network.data.DataBlock;
import com.keepaccpos.network.data.DataOpen;
import com.keepaccpos.network.data.DataTable;
import com.keepaccpos.network.data.Table;
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
 * Created by Arnold on 18.07.2016.
 */
public class FragmentTables extends Fragment {
   private static final  String[] TEST_DATA = {"one", "two", "three", "four", "five"};
    private static final String TAG = FragmentTables.class.getSimpleName();
    private static final int SCALE = 1;
    private IMainActivityObserve mIMainActivityObserve;

    private ArrayList<String> mBlocksName = new ArrayList<>();
    private String mCurrentBlock;
    FrameLayout mTablesFrameLayout;
    Spinner mTablesSpinner;
    ArrayList<Table>mTables = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_tables, container, false);
        mTablesFrameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_tables_layout);
        mTablesSpinner = (Spinner)rootView.findViewById(R.id.fragment_tables_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mBlocksName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        mTablesSpinner.setAdapter(adapter);
        // заголовок
        // выделяем элемент
        mTablesSpinner.setSelection(0);
        // устанавливаем обработчик нажатия
        mTablesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCurrentBlock = mBlocksName.get(position);
                loadTables(mCurrentBlock);
              //  Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        loadBlocks();


return rootView;
    }
    private void addTables()
    {


       // Display display = getActivity().getWindowManager().getDefaultDisplay();
        //Point size = new Point();
        // display.getSize(size);
        //  int width = size.x;
        //  int height = size.y;
        mTablesFrameLayout.removeAllViewsInLayout();
        int width = mTablesFrameLayout.getWidth();
        int height = mTablesFrameLayout.getHeight();
        for(Table t : mTables)
        {
            String tsWidth = t.getWidth();
            String tsHeight = t.getHeight();
            tsWidth = tsWidth.replace("px","");
            tsHeight = tsHeight.replace("px","");
            float tfWidth =(float) Integer.parseInt(tsWidth);
            float ftHeight = (float) Integer.parseInt(tsHeight);

            int tWidth =(int)convertPixelsToDp(tfWidth)*SCALE;
            int tHeight =(int)convertPixelsToDp(ftHeight)*SCALE;

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(tWidth, tHeight);
            Button button = new Button(getActivity());
            String name = t.getName();
            final String journalId = t.getJournalId();
            final String tId = t.getId();

            button.setTag(name);
            button.setText(name);

            String tsRadius = t.getRadius();
            tsRadius = tsRadius.replace("px","");
            float tfRadius =(float) Integer.parseInt(tsRadius)*SCALE;
            Drawable back;
            Drawable def;
            Drawable pressed;
            if(t.getState().equals("closed"))
            {
                def = createDrawable(tfRadius,android.R.color.holo_green_light);
                pressed = createDrawable(tfRadius,android.R.color.holo_green_dark);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTable(tId);
                    }
                });
            }
            else
            {
                def = createDrawable(tfRadius,R.color.btn_table_open);
                pressed = createDrawable(tfRadius,R.color.btn_table_open_clicked);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mIMainActivityObserve == null)
                        {
                            return;
                        }
                        mIMainActivityObserve.createFragmentCheck(PacketPost.TYPE_TABLE,mCurrentBlock, journalId);

                    }
                });
            }
            back =   makeSelector(def,pressed);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(back);

            }
            else
            {
                button.setBackgroundDrawable(back);

            }
            float xPos = Float.parseFloat(t.getXpos());
            float yPos = Float.parseFloat(t.getYpos());

            int  lMargin = (int)(width*xPos*SCALE);
            int  tMargin = (int)(height*yPos*SCALE);
            lp.setMargins(lMargin,tMargin,0,0);

            mTablesFrameLayout.addView(button, lp);
        }

    }
    private void openTable(final String tableId)
    {
        final String guests[] = getResources().getStringArray(R.array.count_guest_array);
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_count_guest);
        builder.setItems(guests, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openPost(tableId,guests[which]);
            }
        });
        builder.create().show();
    }
private void openPost(final String tableId, String countGuest)
{
    HashMap<String,String> tableDataMap = new HashMap<>();
    tableDataMap.put("cId","0");
    tableDataMap.put("tableId", tableId);
    tableDataMap.put("countGuest",countGuest);
    tableDataMap.put("type","Table");
    final Activity activity = getActivity();
    final Packet packet = PacketPost.getPacket(activity,PacketPost.PacketType.OpenTable,new TableData(tableDataMap));

    final ProgressDialog progressDialog = new ProgressDialog(activity);
    progressDialog.setMessage(getString(R.string.msg_loading));
    progressDialog.setCancelable(false);
    progressDialog.show();
    Call<DataOpen> call = FactoryApi.getInstance(activity).open(packet);
    call.enqueue(new Callback<DataOpen>() {
        @Override
        public void onResponse(Call<DataOpen> call, Response<DataOpen> response) {
            progressDialog.dismiss();
            DataOpen openTable =  response.body();
            Log.d(TAG, openTable.getResult());
            if(openTable.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
            {
                String journalId= ""+openTable.getData().getId();
                Table table = TableLab.getInstance().getTable(""+tableId,mCurrentBlock);
                if(table != null)
                {
                    table.setOpenState();
                    table.setJournalId(journalId);
                }
                if(mIMainActivityObserve == null)
                {
                    return;
                }
                mIMainActivityObserve.createFragmentCheck(PacketPost.TYPE_TABLE,mCurrentBlock, journalId);
            }
            else
            {
                Toast.makeText(activity, openTable.getMessage(),Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFailure(Call<DataOpen> call, Throwable t) {

            Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    });
}

    private void loadBlocks()
    {
       List<Block>blocks = BlockLab.getInstance().getBlocks();
        if(!blocks.isEmpty())
        {

            mBlocksName.clear();
            mBlocksName.add("Основной");

            for(Block b : blocks)
            {
                mBlocksName.add(b.getName());
            }
            (( ArrayAdapter<String>) mTablesSpinner.getAdapter()).notifyDataSetChanged();
            loadTables(mBlocksName.get(0));
            return;
        }
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","0");
        tableDataMap.put("type","TableEditor");
        final Activity activity = getActivity();
        Packet packet = PacketPost.getPacket(activity,PacketPost.PacketType.GetBlocks,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataBlock> call = FactoryApi.getInstance(activity).getBlocks(packet);
        call.enqueue(new Callback<DataBlock>() {
            @Override
            public void onResponse(Call<DataBlock> call, Response<DataBlock> response) {
                progressDialog.dismiss();
                DataBlock dataBlock =  response.body();
                Log.d(TAG,dataBlock.getResult());
                if(dataBlock.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
                {
                    List<Block>blocks = dataBlock.getData();
                    BlockLab.getInstance().setBlocks(blocks);
                    mBlocksName.clear();
                    mBlocksName.add("Основной");

                    for(Block b : blocks)
                    {
                        mBlocksName.add(b.getName());
                    }
                    (( ArrayAdapter<String>) mTablesSpinner.getAdapter()).notifyDataSetChanged();
                    loadTables(mBlocksName.get(0));
                }
            }

            @Override
            public void onFailure(Call<DataBlock> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void loadTables(final String name)
    {
        List<Table>tables = TableLab.getInstance().getTables(name);
        if(tables!= null) {
            mTables.clear();
            mTables.addAll(tables);
            addTables();
            return;
        }
            HashMap<String, String> tableDataMap = new HashMap<>();
            tableDataMap.put("cId", "0");
            tableDataMap.put("category", name);
            final Activity activity = getActivity();
            Packet packet = PacketPost.getPacket(activity, PacketPost.PacketType.GetTables, new TableData(tableDataMap));

            final ProgressDialog progressDialog = new ProgressDialog(activity);
            //   progressDialog.setTitle(R.string.msg_authorization);
            progressDialog.setCancelable(false);
            progressDialog.show();
            Call<DataTable> call = FactoryApi.getInstance(activity).getTables(packet);
            call.enqueue(new Callback<DataTable>() {
                @Override
                public void onResponse(Call<DataTable> call, Response<DataTable> response) {
                    progressDialog.dismiss();
                    DataTable dataTable = response.body();
                    Log.d(TAG, dataTable.getResult());
                    if (dataTable.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                        List<Table> tables = dataTable.getData().getTable();
                        TableLab.getInstance().setTables(name, tables);
                        mTables.clear();
                        mTables.addAll(tables);
                        addTables();
                    }
                }

                @Override
                public void onFailure(Call<DataTable> call, Throwable t) {

                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

    }
    public  float convertPixelsToDp(float px){
        Resources resources = getActivity().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public Drawable createDrawable(float radius, int colorValueRes){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shape.setColor(getActivity().getResources().getColor(colorValueRes,null));
        }
        else
        {
            shape.setColor(getActivity().getResources().getColor(colorValueRes));

        }
        return shape;
    }
    public  StateListDrawable makeSelector(Drawable def, Drawable pressed) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed},pressed);
        res.addState(new int[]{},def);
        return res;
    }
    @Override
    public void onAttach(Context context) {
        //  super.onAttach(context);
        Log.d(TAG, "onAttach");
        Activity a;
        if(context instanceof Activity)
        {
            a = (Activity)context;
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


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mIMainActivityObserve = null;
    }
}
