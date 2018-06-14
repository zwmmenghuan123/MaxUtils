package com.example.weimingzeng.maxutils.views.material.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * Date: 2018/5/25
 * author:Weiming Max Zeng
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "FooterBehaviorDependAppBar";
    //总共滑动的距离
    private int totalDistance;
    //所使用的差之器
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    //如果需要在XML界面配置该属性，需要该两个参数的构造函数，否则会报错
    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes, int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //2.根据滑动的距离显示和隐藏footer view

    /**
     * @param child  这个behavior所关联的corrdinatLayout的子view
     * @param target 正在接受滑动的view
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int visibility = child.getVisibility();
        totalDistance += dy;
        if (totalDistance > child.getHeight() && visibility == View.VISIBLE) {
            hideView(child);
        } else {
            if (totalDistance < 0 && (visibility == View.GONE || visibility == View.INVISIBLE)) {
                showView(child);
            }
        }
    }

    private void hideView(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(250);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                showView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void showView(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).
                setInterpolator(INTERPOLATOR).
                setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hideView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }
}
