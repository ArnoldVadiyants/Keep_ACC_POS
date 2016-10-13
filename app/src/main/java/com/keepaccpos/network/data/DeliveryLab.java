package com.keepaccpos.network.data;

/**
 * Created by Arnold on 12.04.2016.
 */

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DeliveryLab {
    public static final String ARG_ID = "id";
    public static final String ARG_AUTOCALC_INDEX = "autocalc_index";
    public static final String ARG_STATE = "state";
    public static final String ARG_DATETIME = "datetime";
    public static final String ARG_CLIENT = "client";
    public static final String ARG_DISCOUNT = "discount";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_NUMBER_PHONE = "number_phone";
    public static final String ARG_DATE_PREORDER = "date_preorder";
    public static final String ARG_TIME_PREORDER = "time_preorder";
    public static final String ARG_SUM_PRICE = "sum_price";
    public static final String ARG_REDUCTION = "reduction";
    public static final String ARG_TOTAL = "total";


    private List<Delivery> mDeliveries = new ArrayList<>();
    private static DeliveryLab sDeliveryLab;

    private DeliveryLab() {

    }

    public static DeliveryLab getInstance() {
        if (sDeliveryLab == null) {
            sDeliveryLab = new DeliveryLab();
        }
        return sDeliveryLab;
    }

    public List<Delivery> getDeliveries() {

        return mDeliveries;
    }
    public void removeDeliveries()
    {
        this.mDeliveries.clear();
    }
    public void removeDelivery(String id) {
        Delivery delivery = getDelivery(id);
        if (delivery !=null) {
            mDeliveries.remove(delivery);
        }
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.mDeliveries = deliveries;
    }

    @Nullable
    public Delivery getDelivery(String id) {
        for (Delivery d : mDeliveries) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public String getValue(String argument, List<DeliveryBody> DeliveryBodies) {
        for (DeliveryBody b : DeliveryBodies) {
            if (b.getName().equals(argument)) {
                return b.getValue();
            }
        }
        return "";
    }
    public void removeData() {
        mDeliveries.clear();
    }
}

