package com.laoniu.ezandroid.view;

import android.view.View;

import com.sothree.slidinguppanel.ScrollableViewHelper;

import androidx.core.widget.NestedScrollView;

/**
 * Time:2020/05/23 0:20
 * Author: laoniu
 * Description:
 */
public class NestedScrollableViewHelper extends ScrollableViewHelper {

    public int getScrollableViewScrollPosition(View mScrollableView, boolean isSlidingUp) {
        if (mScrollableView instanceof NestedScrollView) {
            if(isSlidingUp){
                return mScrollableView.getScrollY();
            } else {
                NestedScrollView nsv = ((NestedScrollView) mScrollableView);
                View child = nsv.getChildAt(0);
                return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
            }
        } else {
            return 0;
        }
    }
}
