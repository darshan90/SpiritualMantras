package com.dstechnologies.gurumantras;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DARSHAN on 6/4/2016.
 */
public class Singleton extends Application {

    public static Singleton singleton=new Singleton();

    public static SharedPreferences sharedPreferences;
    public static String SHAREDPREFRANCE_NAME="pref";
    public static boolean FLAG=false;

    public static Singleton getInstance()
    {
        return singleton;
    }





}
