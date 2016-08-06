package com.vosaye.xagon.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Stats extends ImageView{
	
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	float width, height;
	public Stats(Context context) {
		super(context);
		
	}

	
	
	public Stats(Context context, AttributeSet attrs){
		super(context,attrs);
		
	}
	
	public Stats(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		
	}
	float boxLeft, boxRight, boxTop, boxBottom;
	protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = this.getWidth();
        height = this.getHeight();
        
        if(width<height){
        	boxLeft = 0;
        	boxRight = width;
        	boxTop = (height - width)/2;
        	boxBottom = boxTop+width;
        	height = width;
        	
        }
        else{
        	boxTop = 0;
        	boxBottom = height;
        	boxLeft = (width - height)/2;
        	
        	boxRight = boxLeft+height;
	        width = height;
	        
        }
        
	}
}
