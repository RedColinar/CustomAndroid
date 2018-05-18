package com.example.harry.customandroid.tabs.develop.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CanvasView extends AppCompatTextView {
    private Paint paint;
    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(300, 300, 200, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(300, 300, 150, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(300, 300, 100, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        float[] points = {50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        canvas.drawPoints(points, paint);


    }
}
