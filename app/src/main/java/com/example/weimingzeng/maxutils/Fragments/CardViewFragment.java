package com.example.weimingzeng.maxutils.Fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.netUtils.BaseObserver;
import com.example.weimingzeng.maxutils.netUtils.DownLoadObserver;
import com.example.weimingzeng.maxutils.netUtils.RetrofitHelper;
import com.example.weimingzeng.maxutils.netUtils.RxHelper;
import com.example.weimingzeng.maxutils.netUtils.api.BaseApi;
import com.example.weimingzeng.maxutils.netUtils.test.BookBean;
import com.example.weimingzeng.maxutils.netUtils.test.DownloadObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CardViewFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AlertDialog loading_dialog;
    private TextView textView;
    StringBuilder sb = new StringBuilder();
    private BaseApi api;

    public CardViewFragment() {
    }

    public static CardViewFragment newInstance() {
        CardViewFragment fragment = new CardViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading_dialog = new AlertDialog.Builder(getContext()).setMessage("loading...").create();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        api = RetrofitHelper.createApi(BaseApi.class);
//        new RxHelper().setObservable(api.getTop250(10)).run(new BaseObserver<Top250Bean>() {
//            @Override
//            public void onSuccess(Top250Bean response) {
//                Log.d("test", response.getTitle());
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_view, container, false);
        textView = view.findViewById(R.id.text);
        CardView cardView = view.findViewById(R.id.cv_test);
        cardView.setOnClickListener(this);
        SwipeDismissBehavior<View> swipe = new SwipeDismissBehavior();
        swipe.setDragDismissDistance(300);
        swipe.setListener(
                new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {
                        Toast.makeText(getContext(),
                                "Card swiped !!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                    }
                });
//        swipe.setSwipeDirection(
//                SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) cardView.getLayoutParams();

//        coordinatorParams.setBehavior(swipe);
        return view;
    }


    @Override
    public void onClick(View v) {
        BaseObserver observer = new BaseObserver<BookBean>() {
            @Override
            public void onSuccess(BookBean response) {
                sb.append(response.getTitle() + "\n");
                textView.setText(sb);
            }

            @Override
            public void onError(String msg) {
                Log.d("error", msg);
            }
        };

        DownloadObserver observe = new DownloadObserver("aaa") {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onError(String errorMsg) {
            }

            @Override
            protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                textView.setText("下载中：" + progress + "%");
                if (done) {
                    textView.setEnabled(true);
                    textView.setText("文件下载");
                    textView.setText("下载文件路径：" + filePath);

                }

            }
        };
//        api.getTop250(5)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Function<Top250Bean, ObservableSource<BookBean>>() {
//                    @Override
//                    public ObservableSource<BookBean> apply(Top250Bean top250Bean) throws Exception {
//                        for (Top250Bean.SubjectsBean sub : top250Bean.getSubjects()) {
//                            String id = sub.getId();
//                            Log.d("error", "sendRequest");
//                            return api.getBook(id)
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread());
//                        }
//                        return null;
//                    }
//                }).subscribe(observer);
        String url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk";
        final String fileName = "alipay.apk";

//        new RxHelper().setObservable(api.downloadFile(url)).run();
        DownLoadObserver observer1 = new DownLoadObserver(fileName) {
            @Override
            protected void success(long bytesRead, long contentLength, float progress, String filePath) {
                textView.setText(progress + "");
                if (progress == 100) {
                    textView.setText("下载完成：路径" + filePath);
                }
            }
        };
        api.downloadFile(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
