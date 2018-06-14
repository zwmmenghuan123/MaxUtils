package com.example.weimingzeng.maxutils.testFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.RecyclerView.viewholder.CommonViewHolder;

/**
 * Date: 2018/5/24
 * author:Weiming Max Zeng
 */
public class TextViewHolder extends CommonViewHolder {
    TextView test;
    public TextViewHolder(Context context, View view) {
        super(context, view);
        test = view.findViewById(R.id.tv_test);
    }

    @Override
    public void bindView(RecyclerView.Adapter adapter, Object o) {

        test.setText(o.toString());
    }
}
