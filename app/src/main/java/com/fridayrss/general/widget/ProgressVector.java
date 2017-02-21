package com.fridayrss.general.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.fridayrss.R;

/**
 * Created by voltazor on 22/03/16.
 */
public class ProgressVector extends AppCompatImageView {

    public ProgressVector(Context context) {
        this(context, null);
    }

    public ProgressVector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressVector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int drawableResId;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressVector, defStyleAttr, 0);
            try {
                switch (ta.getInt(R.styleable.ProgressVector_colorTheme, 0)) {
                    case 0:
                    default:
                        drawableResId = R.drawable.vector_loader_animated;
                        break;
                    case 1:
                        drawableResId = R.drawable.vector_loader_animated_dark;
                        break;
                }
            } finally {
                ta.recycle();
            }
        } else {
            drawableResId = R.drawable.vector_loader_animated;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setImageResource(drawableResId);
        } else {
            setImageDrawable(AnimatedVectorDrawableCompat.create(getContext(), drawableResId));
        }
    }

    public void start() {
        setVisibility(VISIBLE);
        ((Animatable) getDrawable()).start();
    }

    public void stop() {
        setVisibility(GONE);
        ((Animatable) getDrawable()).stop();
    }

}
