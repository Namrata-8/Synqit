package com.example.synqit.customeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewBold extends TextView {

    public TextViewBold(Context context) {
        super(context);
        init();
    }

    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Gilroy-Bold.ttf");
        setTypeface(tf);
    }
}
