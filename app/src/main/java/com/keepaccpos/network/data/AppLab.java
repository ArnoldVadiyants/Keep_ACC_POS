package com.keepaccpos.network.data;

/**
 * Created by Arnold on 07.10.2016.
 */
public class AppLab {
 /*   private static AppLab sAppLab;

    private AppLab() {

    }

    public static AppLab getInstance() {
        if (sAppLab == null) {
            sAppLab = new AppLab();
        }
        return sAppLab;
    }*/
  public static void  removeAllData()
    {
        BillLab.getInstance().removeData();
        BanquetLab.getInstance().removeData();
        BlockLab.getInstance().removeData();
        CategoryLab.getInstance().removeData();
        DeliveryLab.getInstance().removeData();
        TableLab.getInstance().removeData();
        UserLab.getInstance().removeData();
    }

}
