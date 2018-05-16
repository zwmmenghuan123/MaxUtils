package com.example.weimingzeng.maxutils.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.weimingzeng.maxutils.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DrawerLayoutActivity extends AppCompatActivity {

    private DrawerLayout mDlLayout;
    private ListView mLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer_layout);
        setContentView(R.layout.layout_drawer);
        mDlLayout = findViewById(R.id.drawer_layout);
        mLeftMenu = findViewById(R.id.lv_drawer);
        String[] ss = new String[] {"新闻", "音乐", "电影"};
        List ll = new ArrayList(Arrays.asList(ss));
        mLeftMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ll));
    }
}
