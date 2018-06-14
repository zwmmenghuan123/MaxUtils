package com.example.weimingzeng.maxutils.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weimingzeng.maxutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabLayoutFragment extends Fragment {
    private static final String TITLE = "param1";
    private static final String CONTENT = "param2";
    private String title;
    private String content;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] mTilte = new String[] {"新闻", "电影", "音乐", "旅行", "新闻", "电影", "音乐", "旅行"};

    public TabLayoutFragment() {
    }

    public static TabLayoutFragment newInstance(String title, String content) {
        TabLayoutFragment fragment = new TabLayoutFragment();
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
        initData();
    }


    private void initData() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            fragments.add(itemFragment.newInstance("", "fragment" + i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTilte[position];
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
