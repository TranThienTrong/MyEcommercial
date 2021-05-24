package com.patecan.myecommercial.Util.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.patecan.myecommercial.R;

public class CustomRadioButton extends AppCompatRadioButton {
    public CustomRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyFont();
    }


    private void applyFont(){
        Typeface boldTypeface = getResources().getFont(R.font.monda);
        setTypeface(boldTypeface);
    }
}
