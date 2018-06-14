package com.example.weimingzeng.maxutils.netUtils.netException;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by weiming.zeng on 2017/12/25.
 */

public class ErrorHandler {
    private static volatile Handler mHandler;

    static {
        mHandler = new Handler(Looper.getMainLooper());
    }
    public static void postRunable(Runnable runnable) {
        mHandler.post(runnable);
    }

    /**
     * 处理登录被踢的情况
     *
     * @param error
     * @param reason
     */
    public static void handleTokenExpire(final String error, final String reason, final Response response) {
        postRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    //如果是欢迎页刷新token失败，把逻辑交回给欢迎页处理
//                    AccountManager.getInstance().clearRefreshToken();
                    if (response.request().url().toString().contains("/v1/token")) {
                        toUiError(error, reason, response.request());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从子线程转到UI线程
     *
     * @param result
     */
    public static void toUiSuccess(final String result, final Request request) {
        postRunable(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

//    // {"reason":"last_login_time: 2017-09-29 11:33:31","error":"30","message":"this account has logged in another device"}
//    public static void gotoLogin(String error, String reason) {
//        final Activity activity = AppManager.getAppManager().currentActivity();
//        if (error.equals("30") || error.equals("10006")) {  // 被踢
//            String alertText = "你的账号于" + getLogTime(reason) + "在其它设备登录，如非本人操作，登录密码可能已泄露，请修改密码。";
//            final LogoutDialog mLogoutDialg = new LogoutDialog(activity, alertText, "确定", Gravity.CENTER);
//            mLogoutDialg.setLeftListener(new DialogOnClickListener() {
//                @Override
//                public void onClickListener(Dialog dialog, View v) {
//                    AccountManager.getInstance().clearRefreshToken();
//                    AppManager.getAppManager().finishAllActivity();
//                    mLogoutDialg.dismiss();
//                    activity.startActivity(new Intent(activity, LoginCloudActivity.class));
//                }
//            });
//            Log.e("handleTokenExpire", "show");
//            mLogoutDialg.show();
//        } else { // 登录过期
//            toUiError(error, "登录过期，请重新登录", null);
//            ToastUtil.show("登录过期，请重新登录");
//            AppManager.getAppManager().finishAllActivity();
//            activity.startActivity(new Intent(activity, LoginCloudActivity.class));
//        }
//
//    }

    public static String getLogTime(String reason) {
        if (TextUtils.isEmpty(reason) || !reason.startsWith("last_login_time")) {
            return "";
        }
        try {
            String time = reason.substring(reason.indexOf(":") + 2);
//        //2017-09-29 11:33:31
            return time.replace("-", "年").replace("-", "月").replace(" ", "日").replace(":", "时").replace(":", "分") + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void toUiError(final String code, final String message, Request request) {
        postRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    String errorMsg = TextUtils.isEmpty(message) ? Err2MsgUtils.getErrMsg(code) : message;
                } catch (Exception e) {
                }
            }
        });
    }
}
