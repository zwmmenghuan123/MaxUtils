package com.example.weimingzeng.maxutils.lab.hook;

import android.util.Log;
import android.view.View;

import com.example.weimingzeng.maxutils.netUtils.utils.ToastUtil;

/**
 * Date: 2018/7/17
 * author:Weiming Max Zeng
 */
public class HookedOnClickListener implements View.OnClickListener {

    private static final String TAG = "HookedOnClickListener";
    private View.OnClickListener origin;

    public void setOriginClickListener(View.OnClickListener ori) {
        this.origin = ori;
    }

    @Override
    public void onClick(View v) {
        ToastUtil.show("hook view");
        Log.d(TAG, "Before click, do what you want to to.");
        if (origin != null) {
            origin.onClick(v);
        }
        Log.d(TAG, "After click, do what you want to to.");
    }
}
