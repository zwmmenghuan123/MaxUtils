package com.example.weimingzeng.maxutils.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MaxFragmentAdapter <T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> mList;
    
    public MaxFragmentAdapter(FragmentManager fm, List<T> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
