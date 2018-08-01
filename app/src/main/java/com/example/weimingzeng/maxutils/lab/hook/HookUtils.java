package com.example.weimingzeng.maxutils.lab.hook;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.weimingzeng.maxutils.Activity.DrawerLayoutActivity;
import com.example.weimingzeng.maxutils.ProxyActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date: 2018/7/17
 * author:Weiming Max Zeng
 */
public class HookUtils {

    /**
     * hook view的listener
     * @param hookListener 需要hook的listener
     * @return 返回被hook的View的原始listener，如果需要原始View的点击生效，需要手动执行该listener或用来初始化新的hooklistener
     */
    public static View.OnClickListener hookOnClickListener(View view, View.OnClickListener hookListener) {
        try {
            // 得到 View 的 ListenerInfo 对象
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);
            // 得到ListenerInfo里的 OnClickListener 对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field onClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            onClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) onClickListener.get(listenerInfo);
            // 用自定义的 OnClickListener 替换原始的 OnClickListener
            onClickListener.set(listenerInfo, hookListener);
            return originOnClickListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hookAms() {
        try {
//            Class<?> activityManager = Class.forName("android.app.ActivityManager");
//            Field iActivityManagerSingleton = activityManager.getDeclaredField("IActivityManagerSingleton");
            Class<?> activityManager = Class.forName("android.app.ActivityManagerNative");
            Field iActivityManagerSingleton = activityManager.getDeclaredField("gDefault");
            iActivityManagerSingleton.setAccessible(true);
            Object defaultValue = iActivityManagerSingleton.get(null);//静态属性直接获取
            //拿到Singleton类,mInstance是Singleton的泛型成员变量,在这里mInstance就是IActivityManager的实例
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = SingletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //defaultValue是Singleton的实例对象,拿到IActivityManager实例
            Object iActivityManagerObject = mInstanceField.get(defaultValue);
            //开始动态代理，用代理对象替换掉真实的ActivityManager
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            AmsInvocationHandler handlerProxy = new AmsInvocationHandler(iActivityManagerObject, ProxyActivity.class);//把原本真实的iActivityManager进行代理，以此添加其他东西
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IActivityManagerIntercept}, handlerProxy);
            //用新生成的代理类替换钓原本的IActivityManager
            mInstanceField.set(defaultValue, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //无注册Manifest启动的Activity，必需继承自Activity，否则onCreate的时候会遇到第二次检查Manifest文件
    public static void hookSystemHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            //获取主线程对象
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThread = currentActivityThreadMethod.invoke(null);
            //获取H内部类字段
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Handler handler = (Handler) mH.get(activityThread);
            //获取callBack字段,然后替换成我们自己的callBack
            Field callBack = Handler.class.getDeclaredField("mCallback");
            callBack.setAccessible(true);
            callBack.set(handler, new ActivityThreadHandlerCallback(handler));
        } catch (Exception e) {

        }
    }
}
