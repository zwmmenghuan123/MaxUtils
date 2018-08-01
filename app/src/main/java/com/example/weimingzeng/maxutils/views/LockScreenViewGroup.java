package com.example.weimingzeng.maxutils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Date: 2018/5/21
 * author:Weiming Max Zeng
 */
public class LockScreenViewGroup extends RelativeLayout {
    private static final String TAG = "LockScreenViewGroup";
    // 一排有几个LockScreenView
    private int itemCount = 3;
    // 悬空线段起点的x、y，即上一个选中的LockScreenView的中心
    private int skyStartX = -1;
    private int skyStartY = -1;
    // 悬空线段终点的x、y
    private int mTempX;
    private int mTempY;
    private int smallRadius = 10;
    private int bigRadius = 20;
    private int normalColor = getColor("#66CCCC");
    private int rightColor = getColor("#197DBF");
    private int wrongColor = getColor("#FF3333");

    // 界面中所有的LockScreenView
    private LockScreenView[] lockScreenViews;

    // 选中的LockScreenView的列表,用id来记录
    private ArrayList<Integer> mCurrentViews;
    // 当前图案的路径
    private Path mCurrentPath;
    private Paint mPaint;
    //答案数组
    private int[] password;


    public LockScreenViewGroup(Context context) {
        this(context, null);
    }

    public LockScreenViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockScreenViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mCurrentViews = new ArrayList<>();
        mCurrentPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画path时，paint要设置为stroke模式，path会化成一个填充区域
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10 * 2 * 0.7f);
        //设置我们画笔的 笔触风格
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置接合处的形态
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setAlpha(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widSise = MeasureSpec.getSize(widthMeasureSpec);
        // 验证码框是正方形所以将高度设置成和宽度一样
        setMeasuredDimension(widSise, widSise);
        //动态添加LLockScreenView
        int totalItem = itemCount * itemCount;
        if (lockScreenViews == null) {
            lockScreenViews = new LockScreenView[totalItem];
            for (int i = 0; i < totalItem; i++) {
                lockScreenViews[i] = new LockScreenView(getContext());
                lockScreenViews[i].setId(i + 1);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                // 这里不能通过lockScreenViews[i].getMeasuredWidth()来获取宽高，因为这时它的宽高还没有测量出来
                //用lockScreenViews的最大半径计算lockScreenViews之间的距离
                int marginWidth = (getMeasuredWidth() - lockScreenViews[i].getBigRadius() * 2 * itemCount) / (itemCount + 1);
                // 除了第一行以外，其它的View都在在某个LockScreenView的下面
                if (i >= itemCount) {
                    params.addRule(BELOW, lockScreenViews[i - itemCount].getId());
                }
                // 除了第一列以外，其它的View都在某个LockScreenView的右边
                if (i % itemCount != 0) {
                    params.addRule(RIGHT_OF, lockScreenViews[i - 1].getId());
                }
                // 为LockScreenView设置margin
                int left = marginWidth;
                int top = marginWidth;
                int bottom = 0;
                int right = 0;
                params.setMargins(left, top, right, bottom);
                lockScreenViews[i].setmCurrentState(LockScreenView.State.STATE_NORMAL);
                addView(lockScreenViews[i], params);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //每次按下都会重置状态
                resetView();
                //不让父View拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                LockScreenView view = findLockScreenView(x, y);
                if (view != null) {
                    // 当前LockScreenView不在选中列表中时，将其添加到列表中，并设置其状态为选中
                    if (!mCurrentViews.contains(view.getId())) {
                        mCurrentViews.add(view.getId());
                        view.setmCurrentState(LockScreenView.State.STATE_CHOOSED);
                        skyStartX = (view.getLeft() + view.getRight()) / 2;
                        skyStartY = (view.getTop() + view.getBottom()) / 2;
                        // path中线段的添加
                        if (mCurrentViews.size() == 1) {//说明这是第一个点
                            Log.d(TAG, "moveTo:" + x + "," + y);
                            mCurrentPath.moveTo(skyStartX, skyStartY);
                        } else {
                            Log.d(TAG, "lineTo:" + x + "," + y);
                            mCurrentPath.lineTo(skyStartX, skyStartY);
                        }
                    }
                }
                if (mCurrentViews.size() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                mTempX = x;
                mTempY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (checkPassword()) {
                    setCurrentViewState(LockScreenView.State.STATE_RESULT_RIGHT);
                    mPaint.setColor(rightColor);
                } else {
                    setCurrentViewState(LockScreenView.State.STATE_RESULT_WRONG);
                    mPaint.setColor(wrongColor);
                }
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetView();
                        postInvalidate();
                    }
                }, 3000);
                // 抬起手指后对悬空线段的起始点进行重置
                skyStartX = -1;
                skyStartY = -1;
                getParent().requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        return true;
    }

    private void resetView() {
        mPaint.setColor(normalColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mCurrentViews.clear();
        mCurrentPath.reset();
        for (int i = 0; i < itemCount * itemCount; i++) {
            lockScreenViews[i].setmCurrentState(LockScreenView.State.STATE_NORMAL);
        }

        skyStartX = -1;
        skyStartY = -1;
    }

    private boolean checkPassword() {
        if (password.length != mCurrentViews.size()) {
            return false;
        }
        for (int i = 0; i < password.length; i++) {
            if (mCurrentViews.get(i) != password[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 自定义一个ViewGroup,onDraw可能不会被调用，原因是需要先设置一个背景表示这个group有东西需要绘制了，才会触发draw，之后是onDraw。
     * 一般直接重写dispatchDraw来绘制viewGroup自己的孩子
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        //在dispatchDraw方法中进行图像点间的线段路径以及悬空线段的绘制：
        super.dispatchDraw(canvas);
        //path线段路径的绘制
        if (!mCurrentPath.isEmpty()) {
            canvas.drawPath(mCurrentPath, mPaint);
        }
        //悬空线段的绘制,就是还没连接到下一个点，还没draw出path时候的线段绘制
        if (skyStartX != -1 && skyStartY != -1) {
            canvas.drawLine(skyStartX, skyStartY, mTempX, mTempY, mPaint);
        }
    }


    /*
     * 寻找判断当前坐标是否属于某个LockScreenView，是则返回这个View，否则返回null
     * */
    private LockScreenView findLockScreenView(int x, int y) {
        for (int i = 0; i < itemCount * itemCount; i++) {
            //判断该坐标是否在当前LockScreenView区域内
            if (isInLockViewArea(x, y, lockScreenViews[i])) {
                return lockScreenViews[i];
            }
        }
        return null;
    }

    //判断传入的x，y坐标是否在该view区域内(为了便于选中，view的区域判定为实际坐标外+5)
    private boolean isInLockViewArea(int x, int y, LockScreenView view) {
        if (x > view.getLeft() - 5 && x < view.getRight() + 5 && y > view.getTop() - 5 && y < view.getBottom() + 5) {
            return true;
        }
        return false;
    }

    //改变被选中view的状态
    private void setCurrentViewState(LockScreenView.State state) {
        for (int i = 0; i < mCurrentViews.size(); i++) {
            LockScreenView view = findViewById(mCurrentViews.get(i));
            view.setmCurrentState(state);
        }
    }

    public void setPassword(int[] password) {
        this.password = password;
    }

    private int getColor(String colorCode) {
        return Color.parseColor(colorCode);
    }

}
