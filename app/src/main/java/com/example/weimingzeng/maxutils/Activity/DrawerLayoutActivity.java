package com.example.weimingzeng.maxutils.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.weimingzeng.maxutils.Fragments.CardViewFragment;
import com.example.weimingzeng.maxutils.Fragments.PersonFragment;
import com.example.weimingzeng.maxutils.Fragments.TabLayoutFragment;
import com.example.weimingzeng.maxutils.Fragments.VideoFragment;
import com.example.weimingzeng.maxutils.Fragments.WebViewFragment;
import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.lab.RecyclerActivity;
import com.example.weimingzeng.maxutils.lab.hook.HookUtils;
import com.example.weimingzeng.maxutils.lab.hook.HookedOnClickListener;

public class DrawerLayoutActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDlLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDlLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //下面的代码主要通过actionbardrawertoggle将toolbar与drawablelayout关联起来.可以点击toolbar图标弹出navigationView
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDlLayout, toolbar, 0, 0);
        toggle.syncState();//加上同步
        mDlLayout.addDrawerListener(toggle);
        HookedOnClickListener hooked = new HookedOnClickListener();
        View.OnClickListener ori = HookUtils.hookOnClickListener(findViewById(R.id.button1), hooked);
        hooked.setOriginClickListener(ori);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //写右侧滑栏的点击事件逻辑
        switch (item.getItemId()) {
            case R.id.nav_person:
                PersonFragment personFragment = PersonFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fr_content, personFragment).commit();
                break;
            case R.id.nav_movie:
                CardViewFragment cardViewFragment = CardViewFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fr_content, cardViewFragment).commit();
                break;
            case R.id.nav_news:
//                VideoFragment videoFragment = VideoFragment.newInstance();
                WebViewFragment webViewFragment = WebViewFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fr_content, webViewFragment).commit();
                break;
            default:
                TabLayoutFragment fragment = TabLayoutFragment.newInstance("", item.getItemId() + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fr_content, fragment).commit();
        }

        mDlLayout.closeDrawers();
        return false;
    }

    public void startRx(View view) {
        Intent intent = new Intent(DrawerLayoutActivity.this, RecyclerActivity.class);
        startActivity(intent);
    }
}
