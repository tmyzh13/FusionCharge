package com.isoftston.issuser.fusioncharge.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by issuser on 2018/4/18.
 */

public class SharePrefsUtils {
    public final static String SETTING = "Setting";
    public static void putValue(Context context,String key, int value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putInt(key, value);
        sp.commit();
    }
    public static void putValue(Context context,String key, boolean value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putBoolean(key, value);
        sp.commit();
    }
    public static void putValue(Context context,String key, String value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }
    public static int getValue(Context context,String key, int defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }
    public static boolean getValue(Context context,String key, boolean defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }
    public static String getValue(Context context,String key, String defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

}
