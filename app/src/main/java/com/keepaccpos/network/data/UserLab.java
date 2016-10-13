package com.keepaccpos.network.data;

import android.support.annotation.Nullable;

/**
 * Created by Arnold on 01.10.2016.
 */
public class UserLab {
    private String mPinCode;
    private static UserLab sUserLab;

    private UserLab() {

    }

    public static UserLab getInstance() {
        if (sUserLab == null) {
            sUserLab = new UserLab();
        }
        return sUserLab;
    }
    @Nullable
    public String getPinCode() {
        return mPinCode;
    }

    public void setPinCode(String pinCode) {
        this.mPinCode = pinCode;
    }
    public void removeData() {
        mPinCode = null;
    }

}
