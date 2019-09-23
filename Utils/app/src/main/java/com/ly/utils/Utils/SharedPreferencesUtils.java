package com.ly.utils.Utils;

import android.content.Context;

import com.ly.utils.common.Constants;
import com.ly.utils.common.MyApplication;

/**
 * Created by seatrend on 2018/8/22.
 */

public class SharedPreferencesUtils {
   /* public static void setIsFirst(boolean isfirst){
        MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
                .edit().putBoolean(Constants.ISFIRST,isfirst).apply();
    }
    public static boolean getIsFirst(){
        boolean b = MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
                .getBoolean(Constants.ISFIRST, true);
        return b;
    }*/

    public static void setIpAddress(String ip){
        MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
                .edit().putString(Constants.IP_K,ip).apply();
    }
    public static String getIpAddress(){
      return   MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
               .getString(Constants.IP_K,"10.10.56.7");
    }

    public static void setPort(String port){
        MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
                .edit().putString(Constants.PORT_K,port).apply();
    }
    public static String getPort(){
       return MyApplication.getMyApplicationContext().getApplicationContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
               .getString(Constants.PORT_K,"8088");
    }
}
