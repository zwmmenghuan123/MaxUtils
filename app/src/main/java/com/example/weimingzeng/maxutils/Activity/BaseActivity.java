package com.example.weimingzeng.maxutils.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.weimingzeng.maxutils.Fragments.BaseFragment;
import com.example.weimingzeng.maxutils.R;

import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        initToolBar();
        initData();
        initPresenter();
    }

    protected void initToolBar() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void initPresenter() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment .isVisible() && fragment instanceof BaseFragment) {
                return ((BaseFragment) fragment).onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
