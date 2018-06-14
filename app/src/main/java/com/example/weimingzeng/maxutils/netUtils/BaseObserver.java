package com.example.weimingzeng.maxutils.netUtils;

import android.app.Dialog;

import com.example.weimingzeng.maxutils.netUtils.netException.ApiException;
import com.example.weimingzeng.maxutils.netUtils.netException.NetError;
import com.example.weimingzeng.maxutils.netUtils.utils.NetworkUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 *
 * Created by weiming.zeng on 2017/12/20.
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    private Dialog dialog;

    public BaseObserver(Dialog dialog) {
        this.dialog = dialog;
    }

    public BaseObserver() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected()) {
            this.onError(new ApiException(NetError.ERROR_NO_INTERNET, "network interrupt"));
            return;
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ApiException ex = ApiException.handleException(e);
        onError(ex.getMessage());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T response);
    public abstract void onError(String msg);
}
