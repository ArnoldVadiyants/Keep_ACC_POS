package com.keepaccpos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

/**
 * Created by Arnold on 18.07.2016.
 */
public class FragmentTables extends Fragment {
   private static final  String[] TEST_DATA = {"one", "two", "three", "four", "five"};

    FrameLayout mTablesFrameLayout;
    Spinner mTablesSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_tables, container, false);
        mTablesFrameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_tables_layout);
        mTablesSpinner = (Spinner)rootView.findViewById(R.id.fragment_tables_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, TEST_DATA);
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
              //  Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

return rootView;
    }
}
