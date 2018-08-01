package com.example.weimingzeng.maxutils.lab.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Date: 2018/7/18
 * author:Weiming Max Zeng
 */
public class ActivityThreadHandlerCallback implements Handler.Callback {
    private Handler handler;

    public ActivityThreadHandlerCallback(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        //如果msg.what为100的时候，则是需要启动Activity，对此进行拦截,将真正要启动的intent的component设置到代理的Intent中，以此启动真正的Activity
        if (msg.what == 100) {
            //msg.obj对应的是ActivityClientRecord
            try {
                Field intent = msg.obj.getClass().getDeclaredField("intent");
                intent.setAccessible(true);
                Intent proxyIntent = (Intent) intent.get(msg.obj);
                Intent realIntent = proxyIntent.getParcelableExtra("realIntent");
                Log.d("realIntent", "handleMessage");
                if (realIntent != null) {
                    proxyIntent.setComponent(realIntent.getComponent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回false，则会继续执行handleMessage，调用系统的exeStartActvity方法
        return false;
    }
}
