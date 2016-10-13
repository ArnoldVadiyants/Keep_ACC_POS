package com.keepaccpos.helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.keepaccpos.activities.LoginActivity;
import com.keepaccpos.activities.PinCodeActivity;
import com.keepaccpos.network.data.AppLab;
import com.keepaccpos.network.data.UserLab;

import java.util.HashSet;

public class SessionManager {


    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared PreferencesActivity
    SharedPreferences pref;

    Editor editor;
    Context appContext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";


    public SessionManager(Context context) {
        this.appContext = context;
        pref = appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setCookies(HashSet<String> cookies) {
        if (cookies == null) {
            return;
        }
        // HashSet<String> cookies= getCookies();
        // cookies.addAll(newCookies);
        editor.putStringSet(KeepAccHelper.PREF_COOKIES, cookies);// commit changes
        editor.commit();

    }
    public void removeDashBoardKey()
    {
        editor.remove(KeepAccHelper.DASHBOARD_KEY);
    }

    public void addDashboardKey() {

        HashSet<String> cookies = getCookies();
        if (cookies.isEmpty()) {
            Log.e(TAG, "@@@@ cookies is null");
            return;
        }
        String dashboardKey = null;
        String prefix = KeepAccHelper.DASHBOARD_KEY + "=";
        String[] myArray = {};
        myArray = cookies.toArray(new String[cookies.size()]);
        for (int i = 0; i < cookies.size(); i++) {
            String cookie = myArray[i];
            Log.d(TAG, "@@@@ cookie " + i + " " + myArray[i]);

            if (cookie.contains(prefix)) {
                int firstIndex = cookie.indexOf(prefix) + prefix.length();
                int lastIndex = cookie.indexOf(";");
                dashboardKey = cookie.substring(firstIndex, lastIndex);
                Log.d(TAG, "@@@@ dashboardKey = " + dashboardKey);
            }
        }
        editor.putString(KeepAccHelper.DASHBOARD_KEY, dashboardKey);// commit changes
        editor.commit();
    }

    @Nullable
    public String getDashboardKey() {

        return pref.getString(KeepAccHelper.DASHBOARD_KEY, null);
    }

    public void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            return;
        }
        // HashSet<String> cookies= getCookies();
        // cookies.addAll(newCookies);
        editor.putString(KeepAccHelper.PREF_LOGIN, login);// commit changes
        editor.commit();
    }

    @NonNull
    public String getLogin() {

        return pref.getString(KeepAccHelper.PREF_LOGIN, "-1");
    }
    public void setRemember(boolean isRemember) {

        editor.putBoolean(KeepAccHelper.PREF_IS_REMEMBER, isRemember);

        // commit changes
        editor.commit();

        Log.d(TAG, "UserRegister login session modified!");
    }

    public boolean isRemember(){
        return pref.getBoolean(KeepAccHelper.PREF_IS_REMEMBER, false);
    }
    public void setLoggedIn(boolean isLoggedIn) {

        editor.putBoolean(KeepAccHelper.KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "UserRegister login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KeepAccHelper.KEY_IS_LOGGED_IN, false);
    }
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            return;
        }
        // HashSet<String> cookies= getCookies();
        // cookies.addAll(newCookies);
        editor.putString(KeepAccHelper.PREF_PASSWORD, password);// commit changes
        editor.commit();
    }

    @NonNull
    public String getPassword() {

        return pref.getString(KeepAccHelper.PREF_PASSWORD, "-1");
    }

    public HashSet<String> getCookies() {
        return (HashSet<String>) pref.getStringSet(KeepAccHelper.PREF_COOKIES, new HashSet<String>());
    }

    public void logout() {
        setLoggedIn(false);
        removeDashBoardKey();
        AppLab.removeAllData();
        appContext.startActivity(new Intent(appContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        if (appContext instanceof Activity) {
            ((Activity) appContext).finish();
        }

    }
    public  void finishSession() {
        UserLab.getInstance().setPinCode(null);
        appContext.startActivity(new Intent(appContext, PinCodeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        if (appContext instanceof Activity) {
            ((Activity) appContext).finish();
        }
    }
}

