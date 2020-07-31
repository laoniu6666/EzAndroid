package com.laoniu.ezandroid.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.laoniu.ezandroid.R;

public class FloatRoom extends RelativeLayout {
    static FloatRoom instance;

    public FloatRoom(Context context) {
        super(context);
        init(context);
    }

    public FloatRoom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public static FloatRoom getInstance(Context ctx){
        if(null==instance){
            instance=new FloatRoom(ctx);
        }
        return instance;
    }

    public static void init(Context ctx){
        View inflate = View.inflate(ctx, R.layout.layout_floatroom, FloatRoom.getInstance(ctx));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
