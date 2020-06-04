package com.laoniu.ezandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.ScreenUtils;

/**
 * Time:2020/06/03 14:11
 * Author: laoniu
 * Description: 自定义viewpager
 */
public class HorizontalScrollView extends android.widget.HorizontalScrollView {
//    android.widget.HorizontalScrollView a;
    float lastX=0;
    float lastY=0;
    Scroller mScroller;
    VelocityTracker mVelocityTracker;
    int mChildWidth=0;
    int mChildIndex=0;
    int mChildSize;

    public HorizontalScrollView(Context context) {
        super(context);
        init();
    }
    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mChildWidth= ScreenUtils.getScreenWidth();
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean isIntercept=false;
//        float x = ev.getX();
//        float y = ev.getY();
//        switch(ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                isIntercept=false;
//                //滑动未结束,拦截事件,避免父容器滑动未结束就消费子view的滑动事件
//                if(!mScroller.isFinished()){
//                    mScroller.abortAnimation();
//                    isIntercept=true;
//                }
//            break;
//            case MotionEvent.ACTION_MOVE:
//                float translateX = x-lastX;
//                float translateY = x-lastY;
//                if(Math.abs(translateX)>Math.abs(translateY)){
//                    isIntercept=true;
//                }else{
//                    isIntercept=false;
//                }
//            break;
//            case MotionEvent.ACTION_UP:
//                isIntercept=false;
//            break;
//            default:
//                break;
//        }
//        L.e("isIntercept="+isIntercept);
//        lastX=x;
//        lastY=y;
//
//        return isIntercept;
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mVelocityTracker.addMovement(event);
//        float x = event.getX();
//        float y = event.getY();
//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                //滑动未结束,拦截事件,避免父容器滑动未结束就消费子view的滑动事件
//                if(!mScroller.isFinished()){
//                    mScroller.abortAnimation();
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float translateX = x-lastX;
//                float translateY = x-lastY;
//                if(Math.abs(translateX)>Math.abs(translateY)){
//                    scrollBy(-(int)translateX,0);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                int scrollX = getScrollX();
//                int scrollToChildIndex = scrollX/mChildWidth;
//                mVelocityTracker.computeCurrentVelocity(1000);
//                float xVelocity = mVelocityTracker.getXVelocity();
//                if(Math.abs(xVelocity)>=50){
//                    mChildIndex= xVelocity>0 ? mChildIndex-1 : mChildIndex +1;
//                }else{
//                    mChildIndex= (scrollX + mChildWidth/2) / mChildWidth;
//                }
//                mChildIndex=Math.max(0,Math.min(mChildIndex,mChildSize-1));
//                int dx = mChildIndex*mChildWidth - scrollX;
//                smoothScrollBy2(dx,0);
//                mVelocityTracker.clear();
//                invalidate();
//                break;
//            default:
//                break;
//        }
//        lastX=x;
//        lastY=y;
//        return true;
//    }
//
//    public void smoothScrollBy2(int dx, int dy){
//        mScroller.startScroll(getScrollX(),0,dx,0,500);
//        invalidate();
//    }
//
//    @Override
//    public void computeScroll() {
////        super.computeScroll();
//        if(mScroller.computeScrollOffset()){
//            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
//            postInvalidate();
//        }
//    }
}
