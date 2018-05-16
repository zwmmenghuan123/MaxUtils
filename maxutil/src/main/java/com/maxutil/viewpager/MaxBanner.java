package com.maxutil.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.maxutil.R;
import com.zhouwei.mzbanner.transformer.CoverModeTransformer;

import java.util.List;


/**
 * Created by weiming.zeng on 2018/2/27.
 */

public class MaxBanner extends RelativeLayout {
    private LinearLayout mIndicatorContainer;//indicator容器
    private MaxViewPager mViewPager;
    private int mDelayedTime = 3000;// Banner 切换时间间隔
    private boolean mIsAutoPlay = true;// 是否自动播放
    private int mCurrentItem = 0;//当前位置
    private boolean mIsCanLoop = true;// 是否轮播图片
    private boolean isLoop;
    private Handler mHandler = new Handler();
    private ViewPagerAdapter mAdapter;
    private Context mContext;

    public MaxBanner(Context context) {
        super(context);
        init();
    }

    public MaxBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        readAttrs(context,attrs);
        init();
    }

    public void setData(List<Fragment> data) {
        if (mContext instanceof AppCompatActivity) {
            mAdapter = new ViewPagerAdapter(((AppCompatActivity)mContext).getSupportFragmentManager(), data);
        } else {
            return;
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true,new CoverModeTransformer(mViewPager));
        mViewPager.setOffscreenPageLimit(data.size() - 4);
        mViewPager.setCurrentItem(mAdapter.getStartSelectItem());
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_normal_layout,this,true);
        mIndicatorContainer = (LinearLayout) view.findViewById(R.id.banner_indicator_container);
        mViewPager = (MaxViewPager) view.findViewById(R.id.banner_vp);

    }

    private void readAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxBanner);
    }


    /**
     * 设置viewpager自动循环播放
     */
    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsAutoPlay) {
                int nextItem = mViewPager.getCurrentItem() + 1;
                if (nextItem == mAdapter.getCount() - 1) {  //轮播到底
                    nextItem = 0;
                    mViewPager.setCurrentItem(nextItem, true);
                } else {
                    mViewPager.setCurrentItem(nextItem, true);
                }

            }
            mHandler.postDelayed(this,mDelayedTime);
        }
    };

    /******************************************************************************************************/
    /**                             对外API                                                               **/
    /******************************************************************************************************/
    /**
     * 开始轮播
     */
    public void start(){
        // 如果Adapter为null, 说明还没有设置数据，这个时候不应该轮播Banner
        if(mAdapter== null){
            return;
        }
        if(mIsCanLoop){
            mIsAutoPlay = true;
            mHandler.postDelayed(mLoopRunnable,mDelayedTime);
        }
    }

    /**
     * 停止轮播
     */
    public void pause(){
        mIsAutoPlay = false;
        mHandler.removeCallbacks(mLoopRunnable);
    }

    /**
     * 设置BannerView 的切换时间间隔
     * @param delayedTime
     */
    public void setDelayedTime(int delayedTime) {
        mDelayedTime = delayedTime;
    }
}
