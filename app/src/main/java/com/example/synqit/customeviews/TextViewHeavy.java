package com.example.synqit.customeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewHeavy extends TextView {

    public TextViewHeavy(Context context) {
        super(context);
        init();
    }

    public TextViewHeavy(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewHeavy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Gilroy-Heavy.ttf");
        setTypeface(tf);
    }
}
