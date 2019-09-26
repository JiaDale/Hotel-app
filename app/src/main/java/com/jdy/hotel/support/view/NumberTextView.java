package com.jdy.hotel.support.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class NumberTextView extends AppCompatTextView {
    public NumberTextView(Context context) {
        this(context, null);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取内容
        String mLoadContent = getText().toString().trim();
        if (TextUtils.isEmpty(mLoadContent)) return;

        mLoadContent.indexOf("日");


        @SuppressLint("DrawAllocation") SpannableString msp =   new SpannableString(mLoadContent);

//        msp.setSpan(new RelativeSizeSpan(0.5f));




    }
}
