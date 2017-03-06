package com.dstechnologies.gurumantras;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DARSHAN on 7/5/2016.
 */
public class Session {

    Context context;
    public static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String PREF_NAME="pref_name";
    public static final String FLAG="status";
    public static final String CHECK_INTERNET_FIRSTTIME="connRequiredfirstTime";

    public Session(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public boolean getFlagStatus()
    {
        return sharedPreferences.getBoolean(FLAG,false);
    }

    public void setFlagStatus(boolean status)
    {
        editor.putBoolean(FLAG,status);
        editor.commit();
    }

    public boolean getCheckInternetFirsttime() {

        return sharedPreferences.getBoolean(CHECK_INTERNET_FIRSTTIME,false);
    }
    public void setCheckInternetFirsttime(boolean connStatus)
    {
        editor.putBoolean(CHECK_INTERNET_FIRSTTIME,connStatus);
        editor.commit();
    }

}
