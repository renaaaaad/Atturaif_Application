package project.graduation.atturaif_application;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class MySharedPreference {
    //Instance of class to be SingleTone
    public static SharedPreferences prf;

    // private constructor
    private MySharedPreference() {
    }

    public static SharedPreferences getInstance(Context context) {
        if (prf == null) {
            prf = context.getSharedPreferences(Constant.Keys.USER_DETAILS, Context.MODE_PRIVATE);
        }//end if
        return prf;
    }//end SharedPreferences


    //----------------editing SharedPref---------------------
    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.clear().commit();
    }//end clearData

    public static void clearValue(Context context, String key) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.remove(key).commit();
    }//end clearValue


    //--------------adding data---------------
    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value).commit();

    }//end putString

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putFloat(key, value).commit();
    }//end putString


    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(key, value).commit();
    }//end putInt

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value).commit();
    }//end putBoolean

    public static <T> void setList(Context context, String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(context, key, json);
    }

    public static void set(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    //------------pull data-----------
    public static String getString(Context context, String key, String valueDefaulte) {
        return getInstance(context).getString(key, valueDefaulte);
    }//end getString

    public static int getInt(Context context, String key, int valueDefaulte) {
        return getInstance(context).getInt(key, valueDefaulte);
    }//end getInt

    public static boolean getBoolean(Context context, String key, boolean valueDefaulte) {
        return getInstance(context).getBoolean(key, valueDefaulte);
    }//end getBoolean

    public static float getFolat(Context context, String key, float valueDefaulte) {
        return getInstance(context).getFloat(key, valueDefaulte);
    }//end getBoolean


} // class
