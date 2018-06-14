package com.example.weimingzeng.maxutils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Date: 2018/5/22
 * author:Weiming Max Zeng
 */
public class MaxApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "MaxApplication";
    private static Context mContext;
    private static boolean isBackGround;//判断当前App是否处于后台
    public int count;
    private static MaxApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mApplication = this;
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (count == 0) {
            isBackGround = false;
        }
        count++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) {
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
            isBackGround = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public static Context getAppContext() {
        return mContext;
    }

    public static boolean isBackGround() {
        return isBackGround;
    }

    public static MaxApplication getPhApplication() {
        return mApplication;
    }
}
