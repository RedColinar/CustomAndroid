package com.example.harry.customandroid.tabs.develop.circleImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.harry.customandroid.R;

/**
 * CustomAndroid
 * Created by Harry on 2018/6/6.
 */

public class CircleImage extends androidx.appcompat.widget.AppCompatImageView {

    private Paint mPaint = new Paint();
    private Paint mBackGroundpaint = new Paint();
    private int mRadius;
    private float mScale;
    Matrix matrix = new Matrix();
    Bitmap icon;
    Bitmap background;

    public CircleImage(Context context) {
        this(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        icon =  drawableToBitmap(getDrawable());
        Shader shader1 = new BitmapShader(icon, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //计算缩放比例
        mScale = (mRadius * 2.0f) / Math.min(icon.getHeight(), icon.getWidth());

        matrix.setScale(mScale, mScale);
        shader1.setLocalMatrix(matrix);

        mPaint.setShader(shader1);
        mBackGroundpaint.setColor(context.getColor(R.color.black));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 两种方式，一种组合着色器，一种画两个bitmap


        //画圆形，指定好中心点坐标、半径、画笔
        canvas.drawCircle(mRadius, mRadius, mRadius, mBackGroundpaint);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void drawSelected() {

    }

    public void drawUnselected() {

    }
}
