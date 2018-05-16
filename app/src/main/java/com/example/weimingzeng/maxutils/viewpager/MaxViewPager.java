package com.example.weimingzeng.maxutils.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.weimingzeng.maxutils.R;

import java.util.List;


public class MaxViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MaxViewPager";
    private Context mContext;
    private ViewPager mViewPager;
    private MaxPagerAdapter mAdapter;
    private List<View> viewsData;
    private LinearLayout mIndicatorLayout;
    private int lastPostion;
    private boolean isCycle = true;
    private int mCurrentPosition = 0; // 轮播当前位置
    private int mDelayedTime = 3000;// Banner 切换时间间隔
    private boolean mIsAutoPlay = true;// 是否自动播放
    //上次滑动时间
    private long lastSlideTime;
    private Handler mHandler = new Handler();

    public MaxViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_max_viewager, this, true);
        mViewPager = findViewById(R.id.vp_max);
        mIndicatorLayout = findViewById(R.id.ll_indicator);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(0);

    }

    private void initIndicator() {
        //排除掉左右两个添加的view，原点数为实际view数-2
        for (int i = 0; i < viewsData.size() - 2; i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.selector_indicator);
            view.setEnabled(false);
            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
            //设置间隔
//            if (pic != mPics[0]) {
//                layoutParams.leftMargin = 10;
//            }
            layoutParams.setMargins(10, 0, 10, 0);
            //添加到LinearLayout
            mIndicatorLayout.addView(view, layoutParams);
        }
        mIndicatorLayout.getChildAt(0).setEnabled(true);
        setIndicator(0);
    }

    public void setData(List<View> data) {
        //支持循环播放则将第一个view和最后一个view添加到列表两端
//        if (isCycle) {
//        }
        viewsData = data;
        mAdapter = new MaxPagerAdapter(viewsData);
        mViewPager.setAdapter(mAdapter);
        initIndicator();
        mViewPager.setCurrentItem(1);
        mHandler.postDelayed(mLoopRunnable,mDelayedTime);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int max = viewsData.size() - 1;
        mCurrentPosition = position;
        if (isCycle) {
            if (position == 0) {
                mCurrentPosition = max - 1;
            } else if (position == max) {
                //滚动到viewsData的最后一个，也就是逻辑上的第一个视图，将mCurrentPosition设置为1,
                mCurrentPosition = 1;
            }
            //mCurrentPosition为重定向后的位置
            position = mCurrentPosition - 1;
        }
        setIndicator(position);
        lastSlideTime = System.currentTimeMillis();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged" + state);
        //        若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) {
            return;
        }
        mViewPager.setCurrentItem(mCurrentPosition, false);
    }

    public void setIndicator(int selectedPosition) {
        if (selectedPosition < 0) {
            return;
        }
        mIndicatorLayout.getChildAt(lastPostion).setEnabled(false);
        mIndicatorLayout.getChildAt(selectedPosition).setEnabled(true);
        lastPostion = selectedPosition;
    }

    /**
     * 设置viewpager自动循环播放
     */
    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            //上次滑动后，必需隔mDelayedTime后才自动播放下一个view
            if (mIsAutoPlay && System.currentTimeMillis() - lastSlideTime > mDelayedTime) {
                int nextItem = mViewPager.getCurrentItem() + 1;
                if (nextItem == mAdapter.getCount() - 2) {  //轮播到底
                    nextItem = 0;
                    mViewPager.setCurrentItem(nextItem, true);
                } else {
                    mViewPager.setCurrentItem(nextItem, true);
                }
            }
            mHandler.postDelayed(this,mDelayedTime);
        }
    };
}
