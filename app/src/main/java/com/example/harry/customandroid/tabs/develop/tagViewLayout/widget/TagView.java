package com.example.harry.customandroid.tabs.develop.tagViewLayout.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import androidx.customview.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.harry.customandroid.R;

import static com.example.harry.customandroid.utils.Utils.dp2px;

public class TagView extends FrameLayout {

    /** Border width*/
    private float mBorderWidth;

    /** Border radius*/
    private float mBorderRadius;

    /** Whether this view clickable*/
    private boolean isViewClickable;

    /** OnTagClickListener for click action*/
    private OnTagClickListener mOnTagClickListener;

    /** Move slop(default 5dp)*/
    private int mMoveSlop = 5;

    /** Scroll slop threshold 4dp*/
    private int mSlopThreshold = 4;

    /** How long trigger long click callback(default 500ms)*/
    private int mLongPressTime = 500;

    private Paint mRipplePaint;

    private RectF mRectF;

    private String mOriginText;

    private boolean isUp, isMoved, isExecLongClick;

    private int mLastX, mLastY;

    private float mTouchX, mTouchY;

    /** The ripple effect duration(default 1000ms)*/
    private int mRippleDuration = 1000;

    private float mRippleRadius;

    private int mRippleColor;

    private int mRippleAlpha;

    private Path mPath;

    private ValueAnimator mRippleValueAnimator;

    private boolean mEnableCross;

    private float mCrossAreaWidth;

    private float mCrossAreaPadding;

    private boolean unSupportedClipPath = false;

    private Runnable mLongClickHandle = new Runnable() {
        @Override
        public void run() {
            if (!isMoved && !isUp){
                int state = ((TagContainerLayout)getParent()).getTagViewState();
                if (state == ViewDragHelper.STATE_IDLE) {
                    isExecLongClick = true;
                    mOnTagClickListener.onTagLongClick((int) getTag(), getText());
                }
            }
        }
    };

    public TagView(Context context, String text){
        super(context);
        init(context, text);
    }

    private void init(Context context, String text){
        LayoutInflater.from(context).inflate(R.layout.layout_tag, this);
        ((TextView) findViewById(R.id.tv_tag_text)).setText(text);
        mCrossAreaWidth = dp2px(context, 12);

        mRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRipplePaint.setStyle(Paint.Style.FILL);
        mRectF = new RectF();
        mPath = new Path();
        mOriginText = text == null ? "" : text;
        mMoveSlop = (int) dp2px(context, mMoveSlop);
        mSlopThreshold = (int) dp2px(context, mSlopThreshold);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(mBorderWidth, mBorderWidth, w - mBorderWidth, h - mBorderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRipple(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isViewClickable){
            int y = (int) event.getY();
            int x = (int) event.getX();
            int action = event.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    mLastY = y;
                    mLastX = x;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(mLastY - y) > mSlopThreshold
                            || Math.abs(mLastX - x) > mSlopThreshold){
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        isMoved = true;
                        return false;
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mRippleRadius = 0.0f;
            mTouchX = event.getX();
            mTouchY = event.getY();
            splashRipple();
        }
        if (isEnableCross() && isClickCrossArea(event) && mOnTagClickListener != null){
            if (action == MotionEvent.ACTION_UP) {
                mOnTagClickListener.onTagCrossClick((int) getTag());
            }
            return true;
        }else if (isViewClickable && mOnTagClickListener != null){
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    mLastY = y;
                    mLastX = x;
                    isMoved = false;
                    isUp = false;
                    isExecLongClick = false;
                    postDelayed(mLongClickHandle, mLongPressTime);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (isMoved){
                        break;
                    }
                    if (Math.abs(mLastX - x) > mMoveSlop || Math.abs(mLastY - y) > mMoveSlop){
                        isMoved = true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    isUp = true;
                    if (!isExecLongClick && !isMoved) {
                        mOnTagClickListener.onTagClick((int) getTag(), getText());
                    }
                    break;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean isClickCrossArea(MotionEvent event){
        return event.getX() >= getWidth() - mCrossAreaWidth;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void drawRipple(Canvas canvas) {
        if (isViewClickable && canvas != null && !unSupportedClipPath){

            // Disable hardware acceleration for 'Canvas.clipPath()' when running on API from 11 to 17
            try {
                canvas.save();
                mPath.reset();

                canvas.clipPath(mPath);
                mPath.addRoundRect(mRectF, mBorderRadius, mBorderRadius, Path.Direction.CCW);

                canvas.clipPath(mPath, Region.Op.REPLACE);
                canvas.drawCircle(mTouchX, mTouchY, mRippleRadius, mRipplePaint);
                canvas.restore();
            }catch (UnsupportedOperationException e){
                unSupportedClipPath = true;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void splashRipple(){
        if (mTouchX > 0 && mTouchY > 0) {
            mRipplePaint.setColor(mRippleColor);
            mRipplePaint.setAlpha(mRippleAlpha);
            final float maxDis = Math.max(Math.max(Math.max(mTouchX, mTouchY),
                    Math.abs(getMeasuredWidth() - mTouchX)), Math.abs(getMeasuredHeight() - mTouchY));

            mRippleValueAnimator = ValueAnimator.ofFloat(0.0f, maxDis).setDuration(mRippleDuration);
            mRippleValueAnimator.addUpdateListener(animation -> {
                float animValue = (float) animation.getAnimatedValue();
                mRippleRadius = animValue >= maxDis ? 0 : animValue;
                postInvalidate();
            });
            mRippleValueAnimator.start();
        }
    }

    public String getText(){
        return mOriginText;
    }

    public boolean getIsViewClickable(){
        return isViewClickable;
    }

    public void setOnTagClickListener(OnTagClickListener listener){
        this.mOnTagClickListener = listener;
    }

    public void setBorderWidth(float width) {
        this.mBorderWidth = width;
    }

    public void setBorderRadius(float radius) {
        this.mBorderRadius = radius;
    }

    public void setIsViewClickable(boolean clickable) {
        this.isViewClickable = clickable;
    }

    public interface OnTagClickListener{
        void onTagClick(int position, String text);
        void onTagLongClick(int position, String text);
        void onTagCrossClick(int position);
    }

    public void setRippleAlpha(int mRippleAlpha) {
        this.mRippleAlpha = mRippleAlpha;
    }

    public void setRippleColor(int mRippleColor) {
        this.mRippleColor = mRippleColor;
    }

    public void setRippleDuration(int mRippleDuration) {
        this.mRippleDuration = mRippleDuration;
    }

    public boolean isEnableCross() {
        return mEnableCross;
    }

    public void setEnableCross(boolean mEnableCross) {
        this.mEnableCross = mEnableCross;
        View v = findViewById(R.id.iv_tag_delete);
        if (v != null) {
            v.setVisibility(mEnableCross ? VISIBLE : GONE);
        }
    }

    public float getCrossAreaWidth() {
        return mCrossAreaWidth;
    }

    public void setCrossAreaWidth(float mCrossAreaWidth) {
        this.mCrossAreaWidth = mCrossAreaWidth;
    }

    public float getCrossAreaPadding() {
        return mCrossAreaPadding;
    }

    public void setCrossAreaPadding(float mCrossAreaPadding) {
        this.mCrossAreaPadding = mCrossAreaPadding;
    }

}
