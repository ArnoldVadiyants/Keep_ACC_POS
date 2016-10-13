package com.keepaccpos.interfaces;

import android.support.annotation.Nullable;

/**
 * Created by Arnold on 14.05.2016.
 */
public interface IMainActivityObserve {
    void createFragmentCheck(String type, @Nullable String blockName, String checkId);
    void updateBill();
}
