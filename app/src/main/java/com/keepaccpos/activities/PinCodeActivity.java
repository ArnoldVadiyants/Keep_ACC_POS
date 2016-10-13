package com.keepaccpos.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.helpers.SessionManager;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.DataPost;
import com.keepaccpos.network.data.UserLab;
import com.keepaccpos.network.model.Packet;
import com.keepaccpos.network.model.TableData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinCodeActivity extends AppCompatActivity {
    private static String TAG = "PinCodeActivity";
    private Button mBtnPinNumber0;
    private Button mBtnPinNumber1;
    private Button mBtnPinNumber2;
    private Button mBtnPinNumber3;
    private Button mBtnPinNumber4;
    private Button mBtnPinNumber5;
    private Button mBtnPinNumber6;
    private Button mBtnPinNumber7;
    private Button mBtnPinNumber8;
    private Button mBtnPinNumber9;
    private Button mBtnPinDelete;
    private Button mBtnPinCorrect;

    private ImageView mImViewPinDot1;
    private ImageView mImViewPinDot2;
    private ImageView mImViewPinDot3;
    private ImageView mImViewPinDot4;

    private String mPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_pin);
        if(UserLab.getInstance().getPinCode() != null)
        {
            startActivity(new Intent(PinCodeActivity.this, MainActivity.class));
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mBtnPinNumber0 = (Button)findViewById(R.id.activity_pin_number0);
        mBtnPinNumber1 = (Button)findViewById(R.id.activity_pin_number1);
        mBtnPinNumber2 = (Button)findViewById(R.id.activity_pin_number2);
        mBtnPinNumber3 = (Button)findViewById(R.id.activity_pin_number3);
        mBtnPinNumber4 = (Button)findViewById(R.id.activity_pin_number4);
        mBtnPinNumber5 = (Button)findViewById(R.id.activity_pin_number5);
        mBtnPinNumber6 = (Button)findViewById(R.id.activity_pin_number6);
        mBtnPinNumber7 = (Button)findViewById(R.id.activity_pin_number7);
        mBtnPinNumber8 = (Button)findViewById(R.id.activity_pin_number8);
        mBtnPinNumber9 = (Button)findViewById(R.id.activity_pin_number9);
        mBtnPinDelete = (Button)findViewById(R.id.activity_pin_delete_btn);
        mBtnPinCorrect = (Button)findViewById(R.id.activity_pin_correct_btn);
        mImViewPinDot1 = (ImageView) findViewById(R.id.activity_pin_dot1);
        mImViewPinDot2 = (ImageView) findViewById(R.id.activity_pin_dot2);
        mImViewPinDot3 = (ImageView) findViewById(R.id.activity_pin_dot3);
        mImViewPinDot4 = (ImageView) findViewById(R.id.activity_pin_dot4);
        mBtnPinNumber0.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber1.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber2.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber3.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber4.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber5.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber6.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber7.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber8.setOnClickListener(mPinBtnClickListener);
        mBtnPinNumber9.setOnClickListener(mPinBtnClickListener);
        mBtnPinDelete.setOnClickListener(mPinBtnClickListener);
        mBtnPinCorrect.setOnClickListener(mPinBtnClickListener);





    }
    private void deletePin()
    {
       mPinCode = null;
        displayDots();

    }
    private void correctPin()
    {
        if (mPinCode != null && mPinCode.length() > 0 && mPinCode.length() <= 4 ) {
            if(mPinCode.length()==1){
                mPinCode = null;
            }
           else {
                mPinCode = mPinCode.substring(0, mPinCode.length() - 1);
            }
        }
        displayDots();

    }

    private void addToPin(String pinNumber)
    {

        if(pinNumber == null)
        {
            return;
        }
        if(pinNumber.length() !=1)
        {
            return;
        }
        try {
            Integer.parseInt(pinNumber);
        }
        catch (NumberFormatException e)
        {
            return;
        }
        if(mPinCode == null)
        {
            mPinCode = pinNumber;
            displayDots();
            return;
        }
        int lengthPin =  mPinCode.length();

        if(lengthPin >=4)
        {
            return;
        }
        mPinCode+=pinNumber;
        displayDots();


    }
    private void displayDots()
    {
        if(mPinCode == null)
        {
            mImViewPinDot1.setSelected(false);
            mImViewPinDot2.setSelected(false);
            mImViewPinDot3.setSelected(false);
            mImViewPinDot4.setSelected(false);
            return;
        }
        int lengthPin =  mPinCode.toCharArray().length;
        if(lengthPin == 4)
        {
            mImViewPinDot1.setSelected(true);
            mImViewPinDot2.setSelected(true);
            mImViewPinDot3.setSelected(true);
            mImViewPinDot4.setSelected(true);
            sendPin();
        }
        else if(lengthPin == 3)
        {
            mImViewPinDot1.setSelected(true);
            mImViewPinDot2.setSelected(true);
            mImViewPinDot3.setSelected(true);
            mImViewPinDot4.setSelected(false);        }
        else if(lengthPin == 2)
        {
            mImViewPinDot1.setSelected(true);
            mImViewPinDot2.setSelected(true);
            mImViewPinDot3.setSelected(false);
            mImViewPinDot4.setSelected(false);
        }
        else if(lengthPin == 1)
        {
            mImViewPinDot1.setSelected(true);
            mImViewPinDot2.setSelected(false);
            mImViewPinDot3.setSelected(false);
            mImViewPinDot4.setSelected(false);
        }

    }
private void sendPin() {
    HashMap<String, String> tableDataMap = new HashMap<>();
    tableDataMap.put("cId", "");
    tableDataMap.put("password", mPinCode);
    String owner = new SessionManager(this).getLogin();
    tableDataMap.put("owner", owner);
    Packet packet = PacketPost.getPacket(this, PacketPost.PacketType.LoginPos, new TableData(tableDataMap));
      /*  Map<String,List> packetMap = new HashMap<String, List>();
        List<Map> packetList = new ArrayList<>();
        // Packet Array[0]
        Map<String, Object> packetData = new HashMap<>();
        packetData.put("networkDataId",packet.getNetworkDataId());
        packetData.put("controller", packet.getController());
        packetData.put("controllerMethod", packet.getControllerMethod());
        // ControllerData Array
        List<Object>controllerDataList = new ArrayList<>();
        // ControllerData Array[0]
        Map<String, Object> controllerDataMap = new HashMap<>();
        controllerDataMap.put("tableName",controllerData.getTableName());
        // TableData Array
        List<Object>tableDataList = new ArrayList<>();
        // TableData Array[0]
        Map<String,Object>tableDataMap = new HashMap<>();
        tableDataMap.put("cId",controllerData.getTableData().getcId());
        tableDataMap.put("email",controllerData.getTableData().getEmail());
        tableDataMap.put("password",controllerData.getTableData().getPassword());
        tableDataList.add(tableDataMap);
        controllerDataMap.put("tableData",tableDataList);
        controllerDataList.add(controllerDataMap);
        packetData.put("controllerData", controllerDataList);
        packetList.add(0,packetData);
        packetMap.put("packet",packetList);*/

     /*   Log.d(TAG,packet.toString());

        $.ajax(new AjaxOptions().url(RetrofitApiInterface.BASE_URL + "dispatcher.php?message=Handle")
                .type("POST")
                .data(packet.toString())
                .dataType("json")
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        if(params == null)
                        {
                            Log.e(TAG, "params return null");
                            return;
                        }
                        if(params[0] == null)
                        {
                            Log.e(TAG, "params[0] return null");
                            return;
                        }
                        Log.d(TAG,params[0].toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("Ajax", statusCode + " " + error);
                    }
                }));

*/
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg_authorization));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(this).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost =  response.body();
                Log.d(TAG,dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
                {
               //     SessionManager sessionManager = new SessionManager(PinCodeActivity.this);
                 //   sessionManager.addDashboardKey();
               //     if(sessionManager.getDashboardKey() != null)
              //      {
                    UserLab.getInstance().setPinCode(mPinCode);
                        startActivity(new Intent(PinCodeActivity.this, MainActivity.class));
                    finish();
                //    }
                }
                else {
                    Toast.makeText(PinCodeActivity.this,dataPost.getTitle() + " : "+ dataPost.getDescription(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(PinCodeActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }



   private View.OnClickListener mPinBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tagObj = v.getTag();
            if(tagObj == null)
            {
                return;
            }

            String tag;

            try
            {
                 tag =(String) tagObj;

            }
            catch (ClassCastException e)
            {
                return;
            }
            try {
                Integer.parseInt(tag);
                addToPin(tag);
            }
            catch (NumberFormatException e)
            {
                if(tag.equals("correct"))
                {
                    correctPin();
                }
                else if(tag.equals("delete"))
                {
                    deletePin();
                }

            }
            Log.d(TAG, "PIN code :"+mPinCode);

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
        else*/ if(id == R.id.action_session_finish)
        {
            return true;
        }
        else if(id == R.id.action_logout)
        {
            new SessionManager(PinCodeActivity.this).logout();
        }

        return super.onOptionsItemSelected(item);
    }

}
