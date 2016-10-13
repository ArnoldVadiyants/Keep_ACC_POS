package com.keepaccpos.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.keepaccpos.R;
import com.keepaccpos.fragments.FragmentBanquet;
import com.keepaccpos.fragments.FragmentCheck;
import com.keepaccpos.fragments.FragmentCheckMenu;
import com.keepaccpos.fragments.FragmentDelivery;
import com.keepaccpos.fragments.FragmentTables;
import com.keepaccpos.helpers.SessionManager;
import com.keepaccpos.interfaces.IMainActivityObserve;
import com.keepaccpos.network.PacketPost;

public class MainActivity extends AppCompatActivity implements IMainActivityObserve {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static String typeFragment = PacketPost.TYPE_TABLE;
    Button mTablesBtn;
    Button mDeliveryBtn;
    Button mBanquetBtn;
    FrameLayout mFragmentContainer;
    FragmentCheck mFragmentCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTablesBtn = (Button) findViewById(R.id.activity_main_table_btn);
        mDeliveryBtn = (Button) findViewById(R.id.activity_main_delivery_btn);
        mBanquetBtn = (Button) findViewById(R.id.activity_main_banquet_btn);
        mFragmentContainer = (FrameLayout)findViewById(R.id.fragment_container) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mTablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentTables();
            }
        });
        mDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentDelivery();
            }
        });
        mBanquetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentBanquet();
            }
        });
       // showTestFragment();
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "mFragment");
            startFragment(fragment);
        }
        else
        {
            showFragmentTables();
        }

       // Toast.makeText(this, "Vasyliq", Toast.LENGTH_SHORT).show();
       // Snackbar.make(mFragmentContainer,"Vasya",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    private void showTestFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTest = new FragmentCheckMenu();
        fm.beginTransaction().replace(R.id.fragment_container, fragmentTest)
                .commit();
    }

    private void showFragmentBanquet()
    {
        if(mBanquetBtn.isSelected())
        {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new FragmentBanquet();
        fm.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();
        mTablesBtn.setSelected(false);
        mBanquetBtn.setSelected(true);
        mDeliveryBtn.setSelected(false);

    }
    private void showFragmentDelivery()
    {
        if(mDeliveryBtn.isSelected()) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new FragmentDelivery();
        fm.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();
        mTablesBtn.setSelected(false);
        mBanquetBtn.setSelected(false);
        mDeliveryBtn.setSelected(true);

    }
    private void showFragmentTables()
    {
        if(mTablesBtn.isSelected())
        {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTables = new FragmentTables();
        fm.beginTransaction().replace(R.id.fragment_container, fragmentTables)
                .commit();
        mTablesBtn.setSelected(true);
        mBanquetBtn.setSelected(false);
        mDeliveryBtn.setSelected(false);

    }

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
            new SessionManager(getApplicationContext()).finishSession();
        }
        else if(id == R.id.action_logout)
        {
          new SessionManager(getApplicationContext()).logout();
        }

        return super.onOptionsItemSelected(item);
    }


   /* private void logout()
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        Packet packet = PacketPost.getPacket(this,PacketPost.PacketType.Logout,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg_authorization));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(MainActivity.this).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
                DataPost dataPost = response.body();
                Log.d(TAG, dataPost.getResult());
                if (dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS)) {
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.setLoggedIn(false);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
*/

    @Override
    public void createFragmentCheck(String type, String blockName, String journalId) {

        FragmentManager fm = getSupportFragmentManager();
        typeFragment = type;
        mFragmentCheck = FragmentCheck.newInstance(type, blockName, journalId);
        fm.beginTransaction().replace(R.id.fragment_container, mFragmentCheck)
                .commit();
    }

    @Override
    public void updateBill() {
        if(mFragmentCheck ==null)
        {
            return;
        }
        mFragmentCheck.updateData();

    }

    public void startFragment(Fragment fragment)
    {


        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragment != null)
            {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
            }


        //       }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            fragmentManager.putFragment(outState, "mFragment",fragment);

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"@@@@ OnBackPressed");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment!=null )
        {
            Log.d(TAG,"@@@@ Fragment != null");

            if (fragment instanceof FragmentCheck)
            {
              switch (typeFragment)
              {
                  case PacketPost.TYPE_TABLE:
                                      startFragment(new FragmentTables());
                      break;
                  case PacketPost.TYPE_BANQUETTE:
                          startFragment(new FragmentBanquet());
                      break;

                  case PacketPost.TYPE_DELIVERY:
                      startFragment(new FragmentDelivery());

                      break;

              }
                return;
            }
            else if (fragment instanceof FragmentTables)
            {
            }
        }
        super.onBackPressed();
    }
}
