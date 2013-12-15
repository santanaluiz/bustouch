package com.arctouch.bustouch.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewPlus extends TextView {
	
    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
    	if (!isInEditMode()) {
	    	Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/champagne.ttf");
	        setTypeface(tf);
    	}
    }
    
}
