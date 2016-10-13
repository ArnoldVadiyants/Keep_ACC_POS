package com.keepaccpos.network.data;

/**
 * Created by Arnold on 12.04.2016.
 */

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class BanquetLab {
    public static final String ARG_ID = "id";
    public static final String ARG_AUTOCALC_INDEX = "autocalc_index";
    public static final String ARG_STATE = "state";
    public static final String ARG_DATETIME_START = "datetime_start";
    public static final String ARG_DATETIME_END = "datetime_end";
    public static final String ARG_STAFF = "staff";
    public static final String ARG_CONTACT_NUMBER = "contact_number";
    public static final String ARG_MODIFIER = "modifier";
    public static final String ARG_DATETIME = "datetime";
    public static final String ARG_WAITER = "waiter";
    public static final String ARG_CLIENT = "client";
    public static final String ARG_HALL = "hall";
    public static final String ARG_COUNT_GUEST = "count_guest";
    public static final String ARG_DISCOUNT = "discount";
    public static final String ARG_PREPAY = "prepay";
    public static final String ARG_SUM_PRICE = "sum_price";
    public static final String ARG_REDUCTION = "reduction";
    public static final String ARG_TOTAL = "total";
    public static final String ARG_TABLE_NAME = "table_name";
    public static final String ARG_DATE_CREATED = "datetime_created";


    private List<Banquet> mBanquets = new ArrayList<>();
    private static BanquetLab sBanquetLab;

    private BanquetLab() {

    }

    public static BanquetLab getInstance() {
        if (sBanquetLab == null) {
            sBanquetLab = new BanquetLab();
        }
        return sBanquetLab;
    }

    public List<Banquet> getBanquets() {

        return mBanquets;
    }
    public void removeData()
    {
        this.mBanquets.clear();
    }
    public void removeBanquet(String id)
    {
    Banquet banquet = getBanquet(id);
        if(banquet !=null)
        {
            mBanquets.remove(banquet);
        }
    }


    public void setBanquets(List<Banquet> banquette) {
        this.mBanquets = banquette;
    }

    @Nullable
    public Banquet getBanquet(String id) {
        for (Banquet b : mBanquets) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public String getValue(String argument, List<BanquetBody> banquetBodies) {
        for (BanquetBody b : banquetBodies) {
            if (b.getName().equals(argument)) {
                return b.getValue();
            }
        }
        return "";
    }

}

