package com.laoniu.ezandroid.view.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class animationUtils {

    public static void countDown(TextView tvCountDown){
        int repeatCount=2;//定义重复字数（执行动画1次 + 重复动画4次 = 公共5次）

        // 设置透明度渐变动画
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(910);//动画持续时间(定义900~1000,也就是1秒左右)
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(repeatCount);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        // 设置缩放渐变动画
        final ScaleAnimation scaleAnimation =new ScaleAnimation(0.5f, 1f, 0.5f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(910);//动画持续时间(定义900~1000,也就是1秒左右)
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(repeatCount);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);


        tvCountDown.startAnimation(animationSet);
        //这里 alphAnimation 设置监听，不能用 animationSet 做监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            int count=repeatCount+1;// 加1为第一次要显示的数字 5
            @Override
            public void onAnimationStart(Animation animation) {// 此方法执行1次
                tvCountDown.setVisibility(View.VISIBLE);
                tvCountDown.setText(""+count);//设置显示的数字
                count--;
            }

            @Override
            public void onAnimationEnd(Animation animation) {// 此方法执行1次

                // 动画结束 隐藏控件
                tvCountDown.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {// 此方法执行4次（repeatCount值）
                tvCountDown.setText(""+count);
                count--;
            }
        });
    }
}
