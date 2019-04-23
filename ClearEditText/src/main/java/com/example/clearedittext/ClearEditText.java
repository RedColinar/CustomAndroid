package com.example.clearedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ClearEditText extends AppCompatEditText {

    private Paint mPaint;
    private Drawable ic_delete;
    private Drawable ic_left;

    private int lineColorClick;
    private int lineColorUnclick;
    private int textAndLineColor;
    private int linePaddingBottom;
    private boolean hasBottomLine;
    private boolean isDeleteVisiable = false;

    private static final int LINE_PADDING_BOTTOM = 16;
    private static final float PAINT_WIDTH = 2.0f;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);

        int ic_left_resID = typedArray.getResourceId(R.styleable.ClearEditText_ic_left, -1);
        ic_left =  ic_left_resID > 0 ? getResources().getDrawable(ic_left_resID) : null;

        int ic_deleteResID = typedArray.getResourceId(R.styleable.ClearEditText_ic_delete, R.drawable.ic_delete_text_gray_no_padding);
        ic_delete =  getResources().getDrawable(ic_deleteResID);

        setCompoundDrawablesWithIntrinsicBounds(ic_left, null, null, null);

        mPaint = new Paint();
        mPaint.setStrokeWidth(PAINT_WIDTH);

        int defaultLineColor = context.getResources().getColor(R.color.gray_1a, null);
        int defaultLineColorClick = context.getResources().getColor(R.color.gray_db, null);
        lineColorClick = typedArray.getColor(R.styleable.ClearEditText_line_color_click, defaultLineColor);
        lineColorUnclick = typedArray.getColor(R.styleable.ClearEditText_line_color_unclick, defaultLineColorClick);
        textAndLineColor = lineColorClick;

        mPaint.setColor(lineColorUnclick);
        setTextColor(textAndLineColor);

        linePaddingBottom = typedArray.getInteger(R.styleable.ClearEditText_line_padding_bottom, LINE_PADDING_BOTTOM);

        hasBottomLine = typedArray.getBoolean(R.styleable.ClearEditText_has_bottom_line, true);
        if (hasBottomLine) {
            setBackground(null);
        }

        typedArray.recycle();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0, hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(focused && length() > 0, focused);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable =  ic_delete;
                boolean isClickDeleteArea = event.getX() < (getWidth() - getPaddingRight())
                        && event.getX() > (getWidth() - getPaddingRight() - drawable.getBounds().width());

                if (drawable != null && isClickDeleteArea && isDeleteVisiable) {
                    setText("");
                    return true;
                }
                break;
        }

        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void setDeleteIconVisible(boolean deleteVisible, boolean isFocus) {
        setCompoundDrawablesWithIntrinsicBounds(ic_left, null, deleteVisible ? ic_delete: null, null);
        textAndLineColor = isFocus ? lineColorClick : lineColorUnclick;
        isDeleteVisiable = deleteVisible;
        invalidate();
    }

    // draw bottom line
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasBottomLine) {
            mPaint.setColor(textAndLineColor);

            int scrollX = this.getScrollX();
            int measuredWidth = this.getMeasuredWidth();

            canvas.drawLine(0, this.getMeasuredHeight() - linePaddingBottom,
                    measuredWidth + scrollX, this.getMeasuredHeight() - linePaddingBottom, mPaint);
        }
    }

    public void setHasBottomLine(boolean hasBottomLine) {
        this.hasBottomLine = hasBottomLine;
        invalidate();
    }
}
