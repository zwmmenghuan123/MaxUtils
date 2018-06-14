package com.example.weimingzeng.maxutils.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.views.LockScreenViewGroup;

public class itemFragment extends BaseFragment {
    private static final String TITLE = "param1";
    private static final String CONTENT = "param2";
    private String title;
    private String content;

    public itemFragment() {
        // Required empty public constructor
    }

    public static itemFragment newInstance(String title, String content) {
        itemFragment fragment = new itemFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            content = getArguments().getString(CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        LockScreenViewGroup lockScreenViewGroup = (LockScreenViewGroup) view.findViewById(R.id.lockScreenViewGroup);
        int[] password = {1,2,3,6};
        lockScreenViewGroup.setPassword(password);
        return view;
    }

    @Override
    protected void loadData() {

    }


}
