package com.keepaccpos;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    Button mTablesBtn;
    Button mDeliveryBtn;
    Button mBanquetBtn;
    FrameLayout mFragmentContainer;

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

        showFragmentTables();
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

    private void showFragmentBanquet()
    {
        if(mBanquetBtn.isSelected())
        {
            return;
        }
        mTablesBtn.setSelected(false);
        mBanquetBtn.setSelected(true);
        mDeliveryBtn.setSelected(false);

    }
    private void showFragmentDelivery()
    {
        if(mDeliveryBtn.isSelected())
        {
            return;
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
