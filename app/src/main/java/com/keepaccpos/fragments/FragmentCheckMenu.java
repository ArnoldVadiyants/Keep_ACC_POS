package com.keepaccpos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepaccpos.models.FeedObject;
import com.keepaccpos.helpers.ItemOffsetDecoration;
import com.keepaccpos.R;
import com.keepaccpos.adapters.MenuFeedAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnold on 22.07.2016.
 */
public class FragmentCheckMenu extends Fragment {
    private static final int VERTICAL_ITEM_SPACE = 48;
    private GridLayoutManager gLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_check_menu, container, false);
        List<FeedObject> rowListItem = getAllItemList();
        gLayout = new GridLayoutManager(getActivity(), 4);

        RecyclerView rView = (RecyclerView)rootView.findViewById(R.id.fragment_check_menu_recycler_view);

        rView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));


        rView.setHasFixedSize(true);
        rView.setLayoutManager(gLayout);

        MenuFeedAdapter rcAdapter = new MenuFeedAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);
        return rootView;
    }
    private List<FeedObject> getAllItemList(){

        List<FeedObject> allItems = new ArrayList<FeedObject>();
        allItems.add(new FeedObject("Feed Item 1","  260$", R.drawable.sample_0));
        allItems.add(new FeedObject("Feed Item 2","  260$", R.drawable.sample_1));
        allItems.add(new FeedObject("Feed Item 3","  260$", R.drawable.sample_2));
        allItems.add(new FeedObject("Feed Item 4","  260$", R.drawable.sample_3));
        allItems.add(new FeedObject("Feed Item 5","  260$", R.drawable.sample_4));
        allItems.add(new FeedObject("Feed Item 6","  260$", R.drawable.sample_5));
        allItems.add(new FeedObject("Feed Item 7","  260$", R.drawable.sample_6));
        allItems.add(new FeedObject("Feed Item 8","  260$", R.drawable.sample_7));


        return allItems;
    }
}
