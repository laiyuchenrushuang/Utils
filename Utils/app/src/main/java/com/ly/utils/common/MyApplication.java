package com.ly.utils.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.ly.utils.Utils.UiAdapterUtil;

/**
 * Created by seatrend on 2018/8/20.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks{


    private static MyApplication applicationContext;

    private static String phone;
    public Activity currentActivity=null;



    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        UiAdapterUtil.setDensity(this);
        //CrashHandler.getInstance().init(this);
    }

    public static MyApplication getMyApplicationContext(){
        return applicationContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //currentActivity=activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
