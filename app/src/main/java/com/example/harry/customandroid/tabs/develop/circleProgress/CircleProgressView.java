package com.example.harry.customandroid.tabs.develop.circleProgress;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.harry.customandroid.R;

/**
 * @author panqiang
 * @version 1.0
 * @date 2018/8/28 10:54
 * @description
 */
public class CircleProgressView extends View {

    public static final int PROGRESS_FACTOR = -360;
    /**
     * Property Progress of the outer circle.
     * <p/>
     * The progress of the circle. If  is set
     * to FALSE, this property will be used to indicate the completion of the outer circle [0..1f].
     * <p/>
     * If set to TRUE, the drawable will activate the loading mode, where the drawable will
     * show a 90º arc which will be spinning around the outer circle as much as progress goes.
     */
    public static final String PROGRESS_PROPERTY = "progress";
    /**
     * Rectangle where the filling ring will be drawn into.
     */
    protected final RectF arcElements;
    /**
     * Width of the filling ring.
     */
    protected int ringWidth;
    /**
     * Paint object to draw the element.
     */
    protected final Paint paint;
    /**
     * Ring progress.
     */
    protected float progress;
    /**
     * Color for the completed ring.
     */
    protected int ringColor;
    /**
     * Ring progress title.
     */
    protected String progressTitle;
    /**
     * default gradient color for the progress ring.
     */
    private LinearGradient shader;

    private Rect rec;


    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        ringColor = array.getColor(R.styleable.CircleProgressView_ringColor, 0);
        ringWidth = (int) array.getDimension(R.styleable.CircleProgressView_ringWidth, 20);
        progressTitle = array.getString(R.styleable.CircleProgressView_progressTitle);
        array.recycle();

        this.progress = 0;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.arcElements = new RectF();
        rec = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = Math.min(canvas.getHeight(), canvas.getWidth());
        float outerRadius = (size / 2) - (ringWidth / 2);
        float offsetX = (canvas.getWidth() - outerRadius * 2) / 2;
        float offsetY = (canvas.getHeight() - outerRadius * 2) / 2;

        int halfRingWidth = ringWidth / 2;
        float arcX0 = offsetX + halfRingWidth;
        float arcY0 = offsetY + halfRingWidth;
        float arcX = offsetX + outerRadius * 2 - halfRingWidth;
        float arcY = offsetY + outerRadius * 2 - halfRingWidth;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcElements.set(arcX0, arcY0, arcX, arcY);
        paint.setColor(Color.GRAY);
        canvas.drawArc(arcElements, 0, 360, false, paint);
        if (ringColor != 0) {
            paint.setColor(ringColor);
            canvas.drawArc(arcElements, -90, -progress, false, paint);
        } else {
            if (shader == null) {
                shader = new LinearGradient(0, offsetY, 0, offsetY + outerRadius * 2, new int[]{Color.parseColor("#B4ED50"),
                        Color.parseColor("#429321")},
                        null, Shader.TileMode.CLAMP);
            }
            paint.setShader(shader);
            canvas.drawArc(arcElements, -90, -progress, false, paint);
        }

        int progressText = -(int) (progress / 3.6);
        String v = progressText + "%";
        paint.setShader(null);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(spToPx(30));
        paint.getTextBounds(v, 0, v.length(), rec);
        int textwidth = rec.width();
        int textheight = rec.height();
        // draw the center words
        if (progressTitle != null && !progressTitle.isEmpty()) {
            canvas.drawText(v, (canvas.getWidth() - textwidth) / 2, (canvas.getHeight() + textheight) / 2 - dpToPx(20), paint);
            paint.setTextSize(spToPx(16));
            paint.getTextBounds(progressTitle, 0, progressTitle.length(), rec);
            int textwidth1 = rec.width();
            int textheight1 = rec.height();
            canvas.drawText(progressTitle, (canvas.getWidth() - textwidth1) / 2, (canvas.getHeight() + textheight1) / 2 + dpToPx(20), paint);
        } else {
            canvas.drawText(v, (canvas.getWidth() - textwidth) / 2, (canvas.getHeight() + textheight) / 2, paint);
        }
    }

    private float spToPx(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }

    /**
     * Sets the progress [0..1f].
     *
     * @param progress Sets the progress.
     */
    @Keep
    public void setProgress(float progress) {
        this.progress = PROGRESS_FACTOR * progress;
        invalidate();
    }

    public void startAnim(float progress, boolean isAnim) {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(this, CircleProgressView.PROGRESS_PROPERTY,
                0f, progress);
        progressAnimation.setDuration(isAnim ? 1000 : 0);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        //another kind of animation
//        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
//                getResources().getColor(android.R.color.holo_red_dark),
//                getResources().getColor(android.R.color.holo_green_light));
//        colorAnimator.setEvaluator(new ArgbEvaluator());
//        colorAnimator.setDuration(3600);
//        animation.playTogether(progressAnimation, colorAnimator);
        animation.play(progressAnimation);
        animation.start();
    }
}
