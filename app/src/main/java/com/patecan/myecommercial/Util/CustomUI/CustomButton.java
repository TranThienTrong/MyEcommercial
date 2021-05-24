package com.patecan.myecommercial.Util.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.patecan.myecommercial.R;

public class CustomButton extends AppCompatButton {
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyFont();
    }

    private void applyFont(){
        Typeface boldTypeface = getResources().getFont(R.font.monda);
        setTypeface(boldTypeface);
    }
}
