package com.example.weimingzeng.maxutils.Fragments;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.weimingzeng.maxutils.R;
import com.example.weimingzeng.maxutils.Utils.PictureUtils;

import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private List<Fragment> fragments;
    RadioGroup mRg;
    private String[] mTilte = new String[]{"简介", "兴趣爱好", "三围"};
    private int mCurrentRadio = 0;

    public PersonFragment() {
    }

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
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
        fragments = new ArrayList<>();
        fragments.add(CardViewFragment.newInstance());
        for (int i = 0; i < 2; i++) {
            fragments.add(MRecyclerViewFragment.newInstance("", "fragment" + i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        //监听back必须设置的
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(backlistener);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("MaxUtils");
        Drawable drawable = getContext().getDrawable(R.drawable.arrow_back);
        toolbar.setNavigationIcon(PictureUtils.zoomDrawable(drawable, 100, 100));
        mViewPager = view.findViewById(R.id.vp_content);
        mTabLayout = view.findViewById(R.id.tb_content);
        mRg = view.findViewById(R.id.rg);
        ((RadioButton) mRg.getChildAt(mCurrentRadio)).setChecked(true);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("蒙奇-D-路飞");
        collapsingToolbarLayout.setExpandedTitleColor(Color.YELLOW); // 标题展示时颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE); // 标题收缩时的颜色
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
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
        mTabLayout = view.findViewById(R.id.tb_content);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private View.OnKeyListener backlistener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                //这边判断,如果是back的按键被点击了   就自己拦截实现掉
                if (i == KeyEvent.KEYCODE_BACK) {
                    onBackPress();
                    return true;//表示处理了
                }
            }
            return false;
        }
    };

    private void onBackPress() {
        if (isRgHide()) {
            //如果底部控件被隐藏，则把它显示出来
            ValueAnimator animator = ValueAnimator.ofFloat(mRg.getTranslationY(), 0);
            mRg.setVisibility(View.VISIBLE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRg.setTranslationY((Float) animation.getAnimatedValue());
                }
            });
            animator.setDuration(200);
            animator.start();
        }
    }

    private boolean isRgHide() {
        return mRg.getTranslationY() > 0;
    }
}
