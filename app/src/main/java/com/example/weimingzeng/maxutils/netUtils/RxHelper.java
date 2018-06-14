package com.example.weimingzeng.maxutils.netUtils;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import com.example.weimingzeng.maxutils.netUtils.test.StringObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weiming.zeng on 2017/12/19.
 */

public class RxHelper {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者者
    private Observable observable;
    private DisposableObserver disposableObserver;

    public void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    public void unRegister() {
        mCompositeDisposable.dispose(); //取消订阅
    }

    public void run(Observable observable, DisposableObserver disposableObserver) {
        register(disposableObserver);
        observable
                .compose(schedulersTransformer())
                .subscribe(disposableObserver);
    }

    public void run(DisposableObserver<?> disposableObserver) {
        if (observable == null || disposableObserver == null) {
            return;
        }
        run(observable, disposableObserver);
    }

    public void run(StringObserver disposableObserver) {
        if (observable == null || disposableObserver == null) {
            return;
        }
        observable
                .compose(schedulersTransformer())
                .subscribe(disposableObserver);
    }

    public RxHelper setObservable(Observable b) {
        observable = b;
        return this;
    }

    public RxHelper flatMap(Function mapper) {

        return this;
    }

    private ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .timeout(5, TimeUnit.SECONDS)//重连间隔时间
                        .retry(5);//重连次数
            }
        };
    }

}
