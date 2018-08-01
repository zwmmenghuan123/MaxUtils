package com.example.weimingzeng.maxutils.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.weimingzeng.maxutils.netUtils.utils.LogUtils;
import com.example.weimingzeng.maxutils.netUtils.utils.NetworkUtils;
import com.example.weimingzeng.maxutils.netUtils.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Date: 2018/6/27
 * author:Weiming Max Zeng
 */
public class MaxWebView extends WebView {

    private Context context;
    private ProgressBar progressBar;

    public MaxWebView(Context context) {
        this(context, null);
    }

    public MaxWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setWebViewClient(new MaxWebViewClient());
        setWebChromeClient(new MaxWebChromeClient());
        WebSettings settings = getSettings();
        // 缓存(cache)
        settings.setAppCacheEnabled(true);      // 默认值 false
        settings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        // 存储(storage)
        settings.setDomStorageEnabled(true);    // 默认值 false
        settings.setDatabaseEnabled(true);      // 默认值 false
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        settings.setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        settings.setLoadWithOverviewMode(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        settings.setLoadWithOverviewMode(true);
        // 是否支持Javascript，默认值false
        settings.setJavaScriptEnabled(true);
        /* 设置WebView是否可以由JavaScript自动打开窗口，默认为false，通常与JavaScript的window.open()配合使用。*/
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (NetworkUtils.isConnected()) {
            // 根据cache-control决定是否从网络上取数据
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 没网，离线加载，优先加载缓存(即使已经过期)
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    public void onDestroy() {
        setWebViewClient(null);
        setWebChromeClient(null);
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        clearHistory();
        removeAllViews();
        destroy();
    }

    public void setProgressBar(ProgressBar bar) {
        progressBar = bar;
    }

    public class MaxWebViewClient extends WebViewClient {
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {//在该方法中可实现资源预加载，本地资源
            WebResourceResponse response = null;
            String url = request.getUrl().toString();
            if (url.endsWith(".png")) {
                response = getWebResourceResponse(url, "image/png", ".png");
            } else if (url.endsWith(".gif")) {
                response = getWebResourceResponse(url, "image/gif", ".gif");
            } else if (url.endsWith(".jpg")) {
                response = getWebResourceResponse(url, "image/jepg", ".jpg");
            } else if (url.endsWith(".jepg")) {
                response = getWebResourceResponse(url, "image/jepg", ".jepg");
            } else if (url.endsWith(".js")) {
                response = getWebResourceResponse("text/javascript", "UTF-8", ".js");
            } else if (url.endsWith(".css")) {
                response = getWebResourceResponse("text/css", "UTF-8", ".css");
            } else if (url.endsWith(".html")) {
                response = getWebResourceResponse("text/html", "UTF-8", ".html");
            }
            if (response != null) {
                return response;
            } else {
                return super.shouldInterceptRequest(view, request);
            }
        }

        private WebResourceResponse getWebResourceResponse(String url, String mime, String style) {
            WebResourceResponse response = null;
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + url);//从文件中查找缓存文件
            if (file == null || !file.exists()) {
                return null;
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                response = new WebResourceResponse(mime, "UTF-8", fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            LogUtils.d("webview", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            LogUtils.d("webview", "onPageFinished");
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            ToastUtil.show("加载失败");
            super.onReceivedError(view, request, error);
        }
    }

    public class MaxWebChromeClient extends WebChromeClient {
        //获取网站标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
        }

        //获取加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar == null) {
                return;
            }
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            LogUtils.d("onProgressChanged", newProgress + "");
        }
    }
}
