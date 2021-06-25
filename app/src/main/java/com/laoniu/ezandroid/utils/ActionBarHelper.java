package com.laoniu.ezandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoniu.ezandroid.R;


public class ActionBarHelper {

    /**
     * 设置标题
     *
     * @param str
     * @param context
     */
    public static void setTitle(String str, Activity context) {
        if (null != context.findViewById(R.id.title)) {
            ((TextView) context.findViewById(R.id.title)).setText(str);
        }
    }

    public static void setRight(String str, Activity context) {
        if (null != context.findViewById(R.id.tv_right)) {
            TextView tv_right = (TextView) context.findViewById(R.id.tv_right);
            tv_right.setText(str);
            tv_right.setVisibility(View.VISIBLE);
        }
    }

    public static void setRightImage(int resId, Activity context) {
        if (null != context.findViewById(R.id.iv_right)) {
            ImageView ivRight = (ImageView) context.findViewById(R.id.iv_right);
            ivRight.setImageResource(resId);
            context.findViewById(R.id.iv_right).setVisibility(View.VISIBLE);
        }
    }

    public static void setLeft(Activity context, boolean hasLeftButton) {
        if (null != context.findViewById(R.id.v_left)) {
            context.findViewById(R.id.v_left).setVisibility(hasLeftButton ? View.VISIBLE : View.GONE);
        }
    }

    public static void setLeftClick(Activity context, View.OnClickListener clickListener) {
        if (null != context.findViewById(R.id.v_left)) {
            context.findViewById(R.id.v_left).setOnClickListener(
                    clickListener != null ? clickListener : (OnClickListener) v -> context.finish());
        }
    }


    private static void setStatuBar(Activity context) {
        View decorView = context.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE            //主体内容占用系统状态栏位置
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //layout隐藏导航栏（会覆盖主体）
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);    //layout全屏，包含状态栏
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     //完全隐藏导航栏，哪儿都没有
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN          //完全全屏
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setNavigationBarColor(Color.TRANSPARENT);   //导航栏透明色
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);       //状态栏透明色
        }
    }


}
