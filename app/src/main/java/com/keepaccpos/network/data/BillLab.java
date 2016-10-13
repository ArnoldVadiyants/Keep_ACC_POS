package com.keepaccpos.network.data;

/**
 * Created by Arnold on 12.04.2016.
 */

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class BillLab {
    private static final String KEY_MENU_ID = "com.keepaccpos.network.data.key_id";
    private HashMap<String, List<BillBody>> mBillBodies = new HashMap<>();
    private HashMap<String, List<BillHead>> mBillHeads = new HashMap<>();
    private HashMap<String, List<Menu>> mBillMenu = new HashMap<>();

    private static BillLab sBillBodyLab;

    private BillLab() {

    }

    public static BillLab getInstance() {
        if (sBillBodyLab == null) {
            sBillBodyLab = new BillLab();
        }
        return sBillBodyLab;
    }
    public void setBill(String type,String journalId,List<BillBody> billBodies,List<BillHead> billHeads) {
        String key = type+journalId;
        this.mBillBodies.put(key,billBodies);
        this.mBillHeads.put(key,billHeads);
    }
    public void removeBill(String type, String journalId) {
        String key = type+journalId;
        this.mBillBodies.remove(key);
        this.mBillHeads.remove(key);
        Set<String> keysSet = mBillMenu.keySet();
        String[]keys =mBillMenu.keySet().toArray(new String[keysSet.size()]);
        for(String k : keys)
        {
            if(k.startsWith(key+KEY_MENU_ID))
            {
                mBillMenu.remove(k);
            }
        }
    }
    public void removeData() {
        mBillBodies.clear();
        mBillHeads.clear();
        mBillMenu.clear();
    }


    @Nullable
    public List<BillBody> getBillBodies(String type, String journalId) {
        String key = type+journalId;

        return mBillBodies.get(key);
    }

    public void setBillBodies(String type, String journalId, List<BillBody> billBodies) {
        String key = type+journalId;

        this.mBillBodies.put(key, billBodies);
    }

    @Nullable
    public BillBody getBillBody(String id, List<BillBody> billBodies) {
        for (BillBody t : billBodies) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }


    @Nullable
    public BillBody getBillBodyByName(String title, List<BillBody> billBodies) {
        for (BillBody t : billBodies) {
            if (t.getName().equals(title)) {
                return t;
            }
        }
        return null;
    }
    @Nullable
    public List<BillHead> getBillHeads(String type,String journalId) {
        String key = type+journalId;

        return mBillHeads.get(key);
    }

    public void setBillHeads(String type,String journalId, List<BillHead> billHeads) {
        String key = type+journalId;

        this.mBillHeads.put(key, billHeads);
    }

    @Nullable
    public BillHead getBillHead(String name, List<BillHead> billHeads) {
        for (BillHead b : billHeads) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }
    public String getBillHeadValue(String name, List<BillHead> billHeads) {
        for (BillHead b : billHeads) {
            if (b.getName().equals(name)) {
                String val = b.getValue();
                if(val == null)
                {
                 return "";
                }
                else return val;
            }
        }
        return "";
    }
    @Nullable
    public List<Menu> getMenuList(String type,String journalId, String category) {
        String key = type+journalId;

        return mBillMenu.get(key+KEY_MENU_ID+category);
    }

    public void setMenuList(String type, String journalId,String category, List<Menu> billMenuList) {
        String key = type+journalId;

        this.mBillMenu.put(key+KEY_MENU_ID+category, billMenuList);
    }

    @Nullable
    public Menu getMenu(String id, List<Menu> billMenuList) {
        for (Menu t : billMenuList) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }



    @Nullable
    public Menu getMenuByName(String title, List<Menu> billMenuList) {
        for (Menu t : billMenuList) {
            if (t.getName().equals(title)) {
                return t;
            }
        }
        return null;
    }

}

