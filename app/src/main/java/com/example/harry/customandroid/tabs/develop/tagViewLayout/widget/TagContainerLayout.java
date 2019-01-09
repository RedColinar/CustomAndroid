package com.example.harry.customandroid.tabs.develop.tagViewLayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.harry.customandroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.harry.customandroid.utils.Utils.dp2px;

public class TagContainerLayout extends ViewGroup {

    /**
     * Vertical interval, default 5(dp)
     */
    private int mVerticalInterval;

    /**
     * Horizontal interval, default 5(dp)
     */
    private int mHorizontalInterval;

    /**
     * TagContainerLayout border width(default 0.0dp)
     */
    private float mBorderWidth = 0.0f;

    /**
     * TagContainerLayout border radius(default 0.0dp)
     */
    private float mBorderRadius = 0.0f;

    /**
     * The sensitive of the ViewDragHelper(default 1.0f, normal)
     */
    private float mSensitivity = 1.0f;

    /**
     * TagView average height
     */
    private int mChildHeight;

    /**
     * TagContainerLayout border color(default #FFFFFF)
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");

    /**
     * TagContainerLayout background color(default #FFFFFF)
     */
    private int mBackgroundColor = Color.parseColor("#FFFFFF");

    /**
     * The container layout gravity(default left)
     */
    private int mGravity = Gravity.LEFT;

    /**
     * The max line count of TagContainerLayout
     */
    private int mMaxLines = 0;

    /**
     * The max length for TagView(default max length 23)
     */
    private int mTagMaxLength = 23;

    /**
     * Text direction(support:TEXT_DIRECTION_RTL & TEXT_DIRECTION_LTR, default TEXT_DIRECTION_LTR)
     */
    private int mTagTextDirection = View.TEXT_DIRECTION_LTR;

    /**
     * Horizontal padding for TagView, include left & right padding(left & right padding are equal, default 10dp)
     */
    private int mTagHorizontalPadding = 10;

    /**
     * Vertical padding for TagView, include top & bottom padding(top & bottom padding are equal, default 8dp)
     */
    private int mTagVerticalPadding = 8;

    /**
     * Whether TagView can clickable(default unclickable)
     */
    private boolean isTagViewClickable;

    /**
     * Tags
     */
    private List<String> mTags;

    /**
     * Can drag TagView(default false)
     */
    private boolean mDragEnable;

    /**
     * TagView drag state(default STATE_IDLE)
     */
    private int mTagViewState = ViewDragHelper.STATE_IDLE;

    /**
     * OnTagClickListener for TagView
     */
    private TagView.OnTagClickListener mOnTagClickListener;

    /**
     * Whether to support 'letters show with RTL(eg: Android to diordnA)' style(default false)
     */
    private boolean mTagSupportLettersRTL = false;

    private Paint mPaint;

    private RectF mRectF;

    private ViewDragHelper mViewDragHelper;

    private List<TagView> mChildViews;

    private int[] mViewPos;

    /**
     * Default interval(dp)
     */
    private static final float DEFAULT_INTERVAL = 5;

    /**
     * Default tag min length
     */
    private static final int TAG_MIN_LENGTH = 3;

    /**
     * Enable draw cross icon(default false)
     */
    private boolean mEnableCross = false;

    /**
     * The cross area width(your cross click area, default equal to the TagView's height)
     */
    private float mCrossAreaWidth = 0.0f;

    /**
     * The padding of the cross area(default 10dp)
     */
    private float mCrossAreaPadding = 10.0f;

    public TagContainerLayout(Context context) {
        this(context, null);
    }

    public TagContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AndroidTagView,
                defStyleAttr, 0);
        mVerticalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_vertical_interval,
                dp2px(context, DEFAULT_INTERVAL));
        mHorizontalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_horizontal_interval,
                dp2px(context, DEFAULT_INTERVAL));
        mBorderWidth = attributes.getDimension(R.styleable.AndroidTagView_container_border_width,
                dp2px(context, mBorderWidth));
        mBorderRadius = attributes.getDimension(R.styleable.AndroidTagView_container_border_radius,
                dp2px(context, mBorderRadius));
        mBorderColor = attributes.getColor(R.styleable.AndroidTagView_container_border_color,
                mBorderColor);
        mBackgroundColor = attributes.getColor(R.styleable.AndroidTagView_container_background_color,
                mBackgroundColor);
        mDragEnable = attributes.getBoolean(R.styleable.AndroidTagView_container_enable_drag, false);
        mSensitivity = attributes.getFloat(R.styleable.AndroidTagView_container_drag_sensitivity,
                mSensitivity);
        mGravity = attributes.getInt(R.styleable.AndroidTagView_container_gravity, mGravity);
        mMaxLines = attributes.getInt(R.styleable.AndroidTagView_container_max_lines, mMaxLines);
        mTagMaxLength = attributes.getInt(R.styleable.AndroidTagView_tag_max_length, mTagMaxLength);
        mTagHorizontalPadding = (int) attributes.getDimension(
                R.styleable.AndroidTagView_tag_horizontal_padding,
                dp2px(context, mTagHorizontalPadding));
        mTagVerticalPadding = (int) attributes.getDimension(
                R.styleable.AndroidTagView_tag_vertical_padding, dp2px(context, mTagVerticalPadding));
        mTagTextDirection = attributes.getInt(R.styleable.AndroidTagView_tag_text_direction, mTagTextDirection);
        isTagViewClickable = attributes.getBoolean(R.styleable.AndroidTagView_tag_clickable, false);
        mEnableCross = attributes.getBoolean(R.styleable.AndroidTagView_tag_enable_cross, mEnableCross);
        mCrossAreaWidth = attributes.getDimension(R.styleable.AndroidTagView_tag_cross_width,
                dp2px(context, mCrossAreaWidth));
        mCrossAreaPadding = attributes.getDimension(R.styleable.AndroidTagView_tag_cross_area_padding,
                dp2px(context, mCrossAreaPadding));
        mTagSupportLettersRTL = attributes.getBoolean(R.styleable.AndroidTagView_tag_support_letters_rlt,
                mTagSupportLettersRTL);

        attributes.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mChildViews = new ArrayList<>();
        mViewDragHelper = ViewDragHelper.create(this, mSensitivity, new DragHelperCallBack());
        setWillNotDraw(false);
        setTagMaxLength(mTagMaxLength);
        setTagHorizontalPadding(mTagHorizontalPadding);
        setTagVerticalPadding(mTagVerticalPadding);

        if (isInEditMode()) {
            addTag("sample tag");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();
        int lines = childCount == 0 ? 0 : getChildLines(childCount);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSpecSize, (mVerticalInterval + mChildHeight) * lines
                    - mVerticalInterval + getPaddingTop() + getPaddingBottom());
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount;
        if ((childCount = getChildCount()) <= 0) {
            return;
        }
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int curRight = getMeasuredWidth() - getPaddingRight();
        int curTop = getPaddingTop();
        int curLeft = getPaddingLeft();
        int sPos = 0;
        mViewPos = new int[childCount * 2];

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                int width = childView.getMeasuredWidth();
                if (mGravity == Gravity.RIGHT) {
                    if (curRight - width < getPaddingLeft()) {
                        curRight = getMeasuredWidth() - getPaddingRight();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curRight - width;
                    mViewPos[i * 2 + 1] = curTop;
                    curRight -= width + mHorizontalInterval;
                } else if (mGravity == Gravity.CENTER) {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        int leftW = getMeasuredWidth() - mViewPos[(i - 1) * 2]
                                - getChildAt(i - 1).getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < i; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                        sPos = i;
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;

                    if (i == childCount - 1) {
                        int leftW = getMeasuredWidth() - mViewPos[i * 2]
                                - childView.getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < childCount; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                    }
                } else {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;
                }
            }
        }

        // layout all child views
        for (int i = 0; i < mViewPos.length / 2; i++) {
            View childView = getChildAt(i);
            childView.layout(mViewPos[i * 2], mViewPos[i * 2 + 1],
                    mViewPos[i * 2] + childView.getMeasuredWidth(),
                    mViewPos[i * 2 + 1] + mChildHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            requestLayout();
        }
    }

    private int getChildLines(int childCount) {
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int lines = 1;
        for (int i = 0, curLineW = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int dis = childView.getMeasuredWidth() + mHorizontalInterval;
            int height = childView.getMeasuredHeight();
            mChildHeight = i == 0 ? height : Math.min(mChildHeight, height);
            curLineW += dis;
            if (curLineW - mHorizontalInterval > availableW) {
                lines++;
                curLineW = dis;
            }
        }

        return mMaxLines <= 0 ? lines : mMaxLines;
    }

    private void onSetTag() {
        removeAllTags();
        if (mTags.size() == 0) {
            return;
        }
        for (int i = 0; i < mTags.size(); i++) {
            onAddTag(mTags.get(i), mChildViews.size());
        }
        postInvalidate();
    }

    private void onAddTag(String text, int position) {
        if (position < 0 || position > mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        TagView tagView;

        tagView = new TagView(getContext(), text);
        initTagView(tagView, position);
        mChildViews.add(position, tagView);
        if (position < mChildViews.size()) {
            for (int i = position; i < mChildViews.size(); i++) {
                mChildViews.get(i).setTag(i);
            }
        } else {
            tagView.setTag(position);
        }
        addView(tagView, position);
    }

    private void initTagView(TagView tagView, int position) {
        tagView.setIsViewClickable(isTagViewClickable);
        tagView.setOnTagClickListener(mOnTagClickListener);
        tagView.setEnableCross(mEnableCross);
        tagView.setCrossAreaPadding(mCrossAreaPadding);
    }

    private void invalidateTags() {
        for (View view : mChildViews) {
            final TagView tagView = (TagView) view;
            tagView.setOnTagClickListener(mOnTagClickListener);
        }
    }

    private void onRemoveTag(int position) {
        if (position < 0 || position >= mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        mChildViews.remove(position);
        removeViewAt(position);
        for (int i = position; i < mChildViews.size(); i++) {
            mChildViews.get(i).setTag(i);
        }
        // TODO, make removed view null?
    }

    private int[] onGetNewPosition(View view) {
        int left = view.getLeft();
        int top = view.getTop();
        int bestMatchLeft = mViewPos[(int) view.getTag() * 2];
        int bestMatchTop = mViewPos[(int) view.getTag() * 2 + 1];
        int tmpTopDis = Math.abs(top - bestMatchTop);
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (Math.abs(top - mViewPos[i * 2 + 1]) < tmpTopDis) {
                bestMatchTop = mViewPos[i * 2 + 1];
                tmpTopDis = Math.abs(top - mViewPos[i * 2 + 1]);
            }
        }
        int rowChildCount = 0;
        int tmpLeftDis = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (mViewPos[i * 2 + 1] == bestMatchTop) {
                if (rowChildCount == 0) {
                    bestMatchLeft = mViewPos[i * 2];
                    tmpLeftDis = Math.abs(left - bestMatchLeft);
                } else {
                    if (Math.abs(left - mViewPos[i * 2]) < tmpLeftDis) {
                        bestMatchLeft = mViewPos[i * 2];
                        tmpLeftDis = Math.abs(left - bestMatchLeft);
                    }
                }
                rowChildCount++;
            }
        }
        return new int[]{bestMatchLeft, bestMatchTop};
    }

    private int onGetCoordinateReferPos(int left, int top) {
        int pos = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (left == mViewPos[i * 2] && top == mViewPos[i * 2 + 1]) {
                pos = i;
            }
        }
        return pos;
    }

    private void onChangeView(View view, int newPos, int originPos) {
        mChildViews.remove(originPos);
        mChildViews.add(newPos, (TagView) view);
        for (View child : mChildViews) {
            child.setTag(mChildViews.indexOf(child));
        }

        removeViewAt(originPos);
        addView(view, newPos);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            mTagViewState = state;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            requestDisallowInterceptTouchEvent(true);
            return mDragEnable;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftX = getPaddingLeft();
            final int rightX = getWidth() - child.getWidth() - getPaddingRight();
            return Math.min(Math.max(left, leftX), rightX);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topY = getPaddingTop();
            final int bottomY = getHeight() - child.getHeight() - getPaddingBottom();
            return Math.min(Math.max(top, topY), bottomY);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            requestDisallowInterceptTouchEvent(false);
            int[] pos = onGetNewPosition(releasedChild);
            int posRefer = onGetCoordinateReferPos(pos[0], pos[1]);
            onChangeView(releasedChild, posRefer, (int) releasedChild.getTag());
            mViewDragHelper.settleCapturedViewAt(pos[0], pos[1]);
            invalidate();
        }
    }

    /**
     * Get current drag view state.
     *
     * @return
     */
    public int getTagViewState() {
        return mTagViewState;
    }

    /**
     * Set tags
     *
     * @param tags
     */
    public void setTags(List<String> tags) {
        mTags = tags;
        onSetTag();
    }

    /**
     * Set tags
     *
     * @param tags
     */
    public void setTags(String... tags) {
        mTags = Arrays.asList(tags);
        onSetTag();
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the end.
     *
     * @param text
     */
    public void addTag(String text) {
        addTag(text, mChildViews.size());
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the specified location.
     * The TagView is inserted before the current element at the specified location.
     *
     * @param text
     * @param position
     */
    public void addTag(String text, int position) {
        onAddTag(text, position);
        postInvalidate();
    }

    /**
     * Remove a TagView in specified position.
     *
     * @param position
     */
    public void removeTag(int position) {
        onRemoveTag(position);
        postInvalidate();
    }

    /**
     * Remove all TagViews.
     */
    public void removeAllTags() {
        mChildViews.clear();
        removeAllViews();
        postInvalidate();
    }

    /**
     * Set OnTagClickListener for TagView.
     *
     * @param listener
     */
    public void setOnTagClickListener(TagView.OnTagClickListener listener) {
        mOnTagClickListener = listener;
        invalidateTags();
    }

    /**
     * Get TagView text.
     *
     * @param position
     * @return
     */
    public String getTagText(int position) {
        return (mChildViews.get(position)).getText();
    }

    /**
     * Get a string list for all tags in TagContainerLayout.
     *
     * @return
     */
    public List<String> getTags() {
        List<String> tmpList = new ArrayList<String>();
        for (View view : mChildViews) {
            if (view instanceof TagView) {
                tmpList.add(((TagView) view).getText());
            }
        }
        return tmpList;
    }

    /**
     * Set whether the child view can be dragged.
     *
     * @param enable
     */
    public void setDragEnable(boolean enable) {
        this.mDragEnable = enable;
    }

    /**
     * Get current view is drag enable attribute.
     *
     * @return
     */
    public boolean getDragEnable() {
        return mDragEnable;
    }

    /**
     * Set vertical interval
     *
     * @param interval
     */
    public void setVerticalInterval(float interval) {
        mVerticalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
    }

    /**
     * Get vertical interval in this view.
     *
     * @return
     */
    public int getVerticalInterval() {
        return mVerticalInterval;
    }

    /**
     * Set horizontal interval.
     *
     * @param interval
     */
    public void setHorizontalInterval(float interval) {
        mHorizontalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
    }

    /**
     * Get horizontal interval in this view.
     *
     * @return
     */
    public int getHorizontalInterval() {
        return mHorizontalInterval;
    }

    /**
     * Get TagContainerLayout border width.
     *
     * @return
     */
    public float getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Set TagContainerLayout border width.
     *
     * @param width
     */
    public void setBorderWidth(float width) {
        this.mBorderWidth = width;
    }

    /**
     * Get TagContainerLayout border radius.
     *
     * @return
     */
    public float getBorderRadius() {
        return mBorderRadius;
    }

    /**
     * Set TagContainerLayout border radius.
     *
     * @param radius
     */
    public void setBorderRadius(float radius) {
        this.mBorderRadius = radius;
    }

    /**
     * Get TagContainerLayout border color.
     *
     * @return
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * Set TagContainerLayout border color.
     *
     * @param color
     */
    public void setBorderColor(int color) {
        this.mBorderColor = color;
    }

    /**
     * Get TagContainerLayout background color.
     *
     * @return
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Set TagContainerLayout background color.
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
    }

    /**
     * Get container layout gravity.
     *
     * @return
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Set container layout gravity.
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    /**
     * Get TagContainerLayout ViewDragHelper sensitivity.
     *
     * @return
     */
    public float getSensitivity() {
        return mSensitivity;
    }

    /**
     * Set TagContainerLayout ViewDragHelper sensitivity.
     *
     * @param sensitivity
     */
    public void setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
    }

    /**
     * Set max line count for TagContainerLayout
     *
     * @param maxLines max line count
     */
    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
        postInvalidate();
    }

    /**
     * Get TagContainerLayout's max lines
     *
     * @return maxLines
     */
    public int getMaxLines() {
        return mMaxLines;
    }

    /**
     * Set the TagView text max length(must greater or equal to 3).
     *
     * @param maxLength
     */
    public void setTagMaxLength(int maxLength) {
        mTagMaxLength = maxLength < TAG_MIN_LENGTH ? TAG_MIN_LENGTH : maxLength;
    }

    /**
     * Get TagView max length.
     *
     * @return
     */
    public int getTagMaxLength() {
        return mTagMaxLength;
    }

    /**
     * Get TagView is clickable.
     *
     * @return
     */
    public boolean getIsTagViewClickable() {
        return isTagViewClickable;
    }

    /**
     * Set TagView is clickable
     *
     * @param clickable
     */
    public void setIsTagViewClickable(boolean clickable) {
        this.isTagViewClickable = clickable;
    }

    /**
     * Get TagView horizontal padding.
     *
     * @return
     */
    public int getTagHorizontalPadding() {
        return mTagHorizontalPadding;
    }

    /**
     * Set TagView horizontal padding.
     *
     * @param padding
     */
    public void setTagHorizontalPadding(int padding) {
        this.mTagHorizontalPadding = padding;
    }

    /**
     * Get TagView vertical padding.
     *
     * @return
     */
    public int getTagVerticalPadding() {
        return mTagVerticalPadding;
    }

    /**
     * Set TagView vertical padding.
     *
     * @param padding
     */
    public void setTagVerticalPadding(int padding) {
        this.mTagVerticalPadding = padding;
    }

    /**
     * Get agView cross area's padding.
     *
     * @return
     */
    public float getCrossAreaPadding() {
        return mCrossAreaPadding;
    }

    /**
     * Set TagView cross area padding, default 10dp.
     *
     * @param mCrossAreaPadding
     */
    public void setCrossAreaPadding(float mCrossAreaPadding) {
        this.mCrossAreaPadding = mCrossAreaPadding;
    }

    /**
     * Get is the TagView's cross enable, default false.
     *
     * @return
     */
    public boolean isEnableCross() {
        return mEnableCross;
    }

    /**
     * Enable or disable the TagView's cross.
     *
     * @param mEnableCross
     */
    public void setEnableCross(boolean mEnableCross) {
        this.mEnableCross = mEnableCross;
        for (int i = 0; i < mChildViews.size(); i++) {
            mChildViews.get(i).setEnableCross(mEnableCross);
        }
    }

    /**
     * Get TagView cross area width.
     *
     * @return
     */
    public float getCrossAreaWidth() {
        return mCrossAreaWidth;
    }

    /**
     * Set TagView area width.
     *
     * @param mCrossAreaWidth
     */
    public void setCrossAreaWidth(float mCrossAreaWidth) {
        this.mCrossAreaWidth = mCrossAreaWidth;
    }

    /**
     * Get TagView in specified position.
     *
     * @param position the position of the TagView
     * @return
     */
    public TagView getTagView(int position) {
        if (position < 0 || position >= mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        return mChildViews.get(position);
    }
}
