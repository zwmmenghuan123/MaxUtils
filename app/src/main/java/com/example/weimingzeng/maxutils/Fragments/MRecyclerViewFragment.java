package com.example.weimingzeng.maxutils.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.RecyclerView.adapter.CommonAdapter;
import com.example.weimingzeng.maxutils.testFiles.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 多种类型item展示的recyclerView
 */
public class MRecyclerViewFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private List<String> data;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MRecyclerViewFragment() {
    }

    public static MRecyclerViewFragment newInstance(String param1, String param2) {
        MRecyclerViewFragment fragment = new MRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        data = new ArrayList();
        for (int i = 0; i < 20; i++) {
            data.add("test" + i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mrecycler_view, container, false);
        mRecyclerView = view.findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContent));

        CommonAdapter adapter = new CommonAdapter(getContext(), data, R.layout.test_layout_text, TextViewHolder.class);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
