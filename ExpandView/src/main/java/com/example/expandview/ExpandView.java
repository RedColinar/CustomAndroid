package com.example.expandview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Harry on 2018/4/5.
 */

public class ExpandView extends LinearLayout implements View.OnClickListener{

    public ImageView arrowImage;
    public TextView expand;
    public RelativeLayout source;

    private boolean isAnimate = false;
    private boolean isExpand = false;
    private final int DURATION = 200;

    public ExpandView(Context context) {
        this(context, null);
    }

    public ExpandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.expandable_view, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        arrowImage = findViewById(R.id.id_arrow_view);
        expand = findViewById(R.id.id_expand_view);
        source = findViewById(R.id.id_source_view);

        expand.setOnClickListener(this);
        arrowImage.setOnClickListener(this);
    }

    public void setSourceView(View view) {
        List<View> oneView = new ArrayList<>();
        oneView.add(view);
        bindView(oneView);
    }

    public void setSourceView(List<View> views) {
        bindView(views);
    }

    private void bindView(List<View> views) {
        if (views.size() > 0) {
            source.setBackgroundColor(getResources().getColor(R.color.white));
            source.removeAllViews();

            for (int i = 0; i < views.size(); i++) {
                source.addView(views.get(i));
            }
        }

        if (isExpand) {
            expand();
        } else {
            source.setVisibility(GONE);
        }
    }

    public void expand() {
        isExpand = true;
        expand.setText(getContext().getString(R.string.collapse));
        arrowImage.setImageResource(R.drawable.ic_expandview_arrow_up);

        // get View's height while its visibility is GONE
        Activity a = (Activity) getContext();
        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        source.measure(size.x, size.y);
        int height = source.getMeasuredHeight();

        createThenStartAnimation(0, height);
    }

    public void collapse() {
        isExpand = false;
        expand.setText(getContext().getString(R.string.expand));
        arrowImage.setImageResource(R.drawable.ic_expandview_arrow_down);

        createThenStartAnimation(source.getHeight(), 0);
    }

    private void createThenStartAnimation(int start, int end) {
        ExpandAnimation expandAnimation = new ExpandAnimation(start, end);
        expandAnimation.setFillAfter(true);
        expandAnimation.setAnimationListener(new MyAnimationListener());
        startAnimation(expandAnimation);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_expand_view || id == R.id.id_arrow_view) {
            if (isExpand) {
                collapse();
            } else {
                expand();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isAnimate;
    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            isAnimate = true;

            if (isExpand) {
                source.setVisibility(VISIBLE);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isAnimate = false;
            clearAnimation();

            if (!isExpand) {
                source.setVisibility(GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

    private class ExpandAnimation extends Animation {
        private int start;
        private int end;

        ExpandAnimation(int start, int end) {
            setDuration(DURATION);
            this.start = start;
            this.end = end;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            source.getLayoutParams().height = (int) (start + (end - start) * interpolatedTime);
            source.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
