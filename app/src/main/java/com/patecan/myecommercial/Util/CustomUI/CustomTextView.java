package com.patecan.myecommercial.Util.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.patecan.myecommercial.R;

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyFont();
    }


    private void applyFont(){
        Typeface boldTypeface = getResources().getFont(R.font.monda);
        setTypeface(boldTypeface);
    }

}

