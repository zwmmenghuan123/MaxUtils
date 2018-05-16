package com.maxutil.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by weiming.zeng on 2018/2/26.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<Fragment> tFragments;
    private int mLooperCountFactor = 1;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
        this.tFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {//必须实现
        int size = tFragments.size();
        Fragment fragment = tFragments.get(position%size);
//        tFragments.remove(fragment);
        Log.d("pageradapter", position+"");
        return fragment;
    }

    @Override
    public int getCount() {
        //设置一个很大的数字来轮播
        return getRealCount() * mLooperCountFactor;
    }

    @Override
    public CharSequence getPageTitle(int position) {//选择性实现
        return mFragments.get(position).getClass().getSimpleName();
    }

    public int getStartSelectItem(){
        // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
        // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
        int currentItem = mLooperCountFactor / 2;
        if(currentItem % getRealCount()  ==0 ){
            return currentItem;
        }
        // 直到找到从0开始的位置
        while (currentItem % getRealCount() != 0){
            currentItem++;
        }
        return currentItem;
    }

    private int getRealCount(){
        return  mFragments == null ? 0:mFragments.size();
    }


}
