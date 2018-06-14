package com.example.weimingzeng.maxutils.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Date: 2018/5/18
 * author:Weiming Max Zeng
 */
public class LockScreenView extends View {

    private int smallRadius = 30;
    private int bigRadius = 50;
    private int normalColor = getColor("#66CCCC");
    private int rightColor = getColor("#197DBF");
    private int wrongColor = getColor("#FF3333");
    ;
    private State mCurrentState = State.STATE_NORMAL;
    private Paint mPaint;
    private boolean needZoomIn;

    public enum State { // 三种状态，分别是正常状态、选中状态、结果正确状态、结果错误状态
        STATE_NORMAL, STATE_CHOOSED, STATE_RESULT_RIGHT, STATE_RESULT_WRONG
    }

    public LockScreenView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public LockScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private int getColor(String colorCode) {
        return Color.parseColor(colorCode);
    }

    public void setmCurrentState(State state) {
        this.mCurrentState = state;
        invalidate();
    }

    /**
     * 放大
     */
    private void zoomIn() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1, 1f);
        animatorX.setDuration(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1, 1f);
        animatorY.setDuration(0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.start();
        needZoomIn = false;
    }

    /**
     * 缩小
     */
    private void zoomOut() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1, 1.2f);
        animatorX.setDuration(50);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1, 1.2f);
        animatorY.setDuration(50);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.start();
        needZoomIn = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //针对wrap_content处理
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = Math.round(bigRadius * 2);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) Math.round(bigRadius * 2);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCurrentState) {
            case STATE_NORMAL:
                mPaint.setColor(normalColor);
                // 透明度为0到255，255为不透明，0为全透明
                mPaint.setAlpha(255);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, smallRadius, mPaint);
                // 放大以后，在下次恢复正常时要缩小回去
                if (needZoomIn) {
                    zoomIn();
                }
                break;
            case STATE_CHOOSED:
                mPaint.setColor(normalColor);
                mPaint.setAlpha(255);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, smallRadius, mPaint);
                mPaint.setAlpha(50);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, bigRadius, mPaint);
                zoomOut();
                break;
            case STATE_RESULT_RIGHT:
                mPaint.setColor(rightColor);
                mPaint.setAlpha(50);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, bigRadius, mPaint);
                mPaint.setAlpha(255);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, smallRadius, mPaint);
                break;
            case STATE_RESULT_WRONG:
                mPaint.setColor(wrongColor);
                mPaint.setAlpha(50);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, bigRadius, mPaint);
                mPaint.setAlpha(255);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, smallRadius, mPaint);
                break;
        }
    }

    public int getBigRadius() {
        return bigRadius;
    }

    public int getNormalColor() {
        return normalColor;
    }
}
