package com.example.weimingzeng.maxutils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.weimingzeng.maxutils.R;

/**
 * 设置阴影的自定义V
 * Created by weiming.zeng on 2018/3/21.
 */

public class MaxShadowLayout extends FrameLayout {

    public static final int ALL = 0x1111;
    public static final int LEFT = 0x0001;
    public static final int TOP = 0x0010;
    public static final int RIGHT = 0x0100;
    public static final int BOTTOM = 0x1000;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRectf = new RectF();

    //阴影颜色
    private int shadowColor = Color.TRANSPARENT;
    //阴影范围
    private float shadowRadius = 0;
    //阴影X,Y轴偏移量
    private float dx,dy;
    //阴影显示的边界
    private int mShadowSide = ALL;

    public MaxShadowLayout(@NonNull Context context) {
        this(context, null);
    }

    public MaxShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 调用此方法后，才会执行 onDraw(Canvas) 方法
        this.setWillNotDraw(false);
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);//自定义的属性
        if (t != null) {
            shadowColor = t.getColor(R.styleable.ShadowLayout_shadowColor, ContextCompat.getColor(getContext(), android.R.color.black));
            shadowRadius = t.getDimension(R.styleable.ShadowLayout_shadowRadius, dp2px(0));
            dx = t.getDimension(R.styleable.ShadowLayout_shadowDx, dp2px(0));
            dy = t.getDimension(R.styleable.ShadowLayout_shadowDy, dp2px(0));
            mShadowSide = t.getInt(R.styleable.ShadowLayout_shadowSide, ALL);
            t.recycle();
        }
        setUpShadowPaint();
    }

    /**
     * 获取绘制阴影的位置，并设置 Padding 以为显示阴影留出空间
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float effect = shadowRadius + dp2px(5);
//        float effect = 0;
        //阴影矩形的参数
        float rectLeft  = 0;
        float rectTop = 0;
        float rectRight = getWidth();
        float rectBottom = getHeight();

        int paddingLeft = 0;
        int paddingRight = 0;
        int paddingTop = 0;
        int paddingBottom = 0;
        //相同的数和自己&运算等于自身，与1111&运算也等于自身
        if ((mShadowSide & LEFT) == LEFT) {
            rectLeft = effect;
            paddingLeft = (int) effect;
        }
        if ((mShadowSide & RIGHT) == RIGHT) {
            rectRight = getWidth() - effect;
            paddingRight = (int) effect;
        }
        if ((mShadowSide & TOP) == TOP) {
            rectTop = effect;
            paddingTop = (int) effect;
        }
        if ((mShadowSide & BOTTOM) == BOTTOM) {
            rectBottom = getHeight() - effect;
            paddingBottom = (int) effect;
        }

        if (dy != 0.0f) {
            rectBottom = rectBottom - dy;
            paddingBottom = paddingBottom + (int) dy;
        }
        if (dx != 0.0f) {
            rectRight = rectRight - dx;
            paddingRight = paddingRight + (int) dx;
        }
        mRectf.left = rectLeft;
        mRectf.top = rectTop;
        mRectf.right = rectRight;
        mRectf.bottom = rectBottom;
        this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setUpShadowPaint();
        canvas.drawRect(mRectf, mPaint);
    }

    private void setUpShadowPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.TRANSPARENT);   //加了这里会导致阴影不生效，不知道为什么
        mPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
    }

    private float dp2px(float dpValue) {
        return (dpValue * getContext().getResources().getDisplayMetrics().density + 0.5F);
    }
}
