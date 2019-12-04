package com.example.rchinicz.getcab2.model.model.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref
{
    public static String getPreference(Context context, String key)
    {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }
    public static void savePreference(Context context, String key, String value)
    {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
