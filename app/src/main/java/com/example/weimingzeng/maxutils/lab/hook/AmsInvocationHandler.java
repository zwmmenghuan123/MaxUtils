package com.example.weimingzeng.maxutils.lab.hook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.example.weimingzeng.maxutils.MaxApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Ams的动态代理类,每次执行startActivity都会执行到这里
 * Date: 2018/7/17
 * author:Weiming Max Zeng
 */
public class AmsInvocationHandler implements InvocationHandler{
    private Object iActivityManagerObject;//原本系统的IActivityManager
    private Class proxyActivity;

    public AmsInvocationHandler(Object iActivityManagerObject, Class proxyActivity) {
        this.iActivityManagerObject = iActivityManagerObject;
        this.proxyActivity = proxyActivity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int indext = 0;
        if ("startActivity".contains(method.getName())) {
            Log.d("AmsInvocationHandler", "Activity已经开始启动" + proxy.getClass().getName());
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    //找到Intent参数
                    //伪造一个代理的Intent，代理Intent启动的是proxyActivity
                    Intent proxyIntent = new Intent();
                    proxyIntent.setComponent(new ComponentName(MaxApplication.class.getPackage().getName(), proxyActivity.getName()));
                    proxyIntent.putExtra("realIntent", (Intent)args[i]);
                    Log.d("realIntent", ((Intent)args[i]).getComponent().toShortString());
                    args[i] = proxyIntent;
                    break;
                }
            }
        }
        return method.invoke(iActivityManagerObject, args);
    }
}
