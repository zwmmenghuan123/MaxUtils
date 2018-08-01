package com.example.weimingzeng.maxutils.lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.weimingzeng.maxutils.Activity.BaseActivity;
import com.example.weimingzeng.maxutils.MainActivity;
import com.example.weimingzeng.maxutils.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<String> mData;
    private MyRecyclerAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        initData();
        recycleAdapter = new MyRecyclerAdapter(RecyclerActivity.this, mData);
        // ...
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // ...
    }

    public void initData() {
        mData = new ArrayList<>();
//        mData.add("HODV-21194"); //0
//        mData.add("TEK-080"); //1
//        mData.add("IPZ-777"); //2
//        mData.add("MIMK-045"); //3
//        mData.add("HODV-21193"); //4
//        mData.add("MIDE-339"); //5
//        mData.add("IPZ-780"); //6
//        mData.add("VEC-205"); //7
//        mData.add("VEMA-113"); //8
//        mData.add("IPZ-776"); //9
//        mData.add("MIAD-923"); //10
//        mData.add("ARM-513"); //11

        for (int i = 0; i < 30; i++) {
            mData.add(i + "---");
        }
    }
}
