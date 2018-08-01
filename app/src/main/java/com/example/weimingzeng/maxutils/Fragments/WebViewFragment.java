package com.example.weimingzeng.maxutils.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.views.MaxWebView;

public class WebViewFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MaxWebView webView;
    private String mParam1;
    private String mParam2;
    private ProgressBar progressBar;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        webView = new MaxWebView(getContext().getApplicationContext());//防止内存泄漏
        webView.loadUrl("http://www.baidu.com/");
        webView.setProgressBar(progressBar);
        FrameLayout layout = view.findViewById(R.id.webViewFragment);
        layout.addView(webView, 0);
        return view;
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.onDestroy();
        }

        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
        }
        return true;
    }
}
