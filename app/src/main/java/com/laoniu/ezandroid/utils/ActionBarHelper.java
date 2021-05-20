package com.laoniu.ezandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.laoniu.ezandroid.R;


public class ActionBarHelper {


	/**
	 * 函数功能说明 EB1 2014-4-8 修改者名字 修改日期 修改内容
	 * 
	 * @param context
	 */
	public static void initActionBar(AppCompatActivity context) {
//		if(null==context.findViewById(R.id.toolbar)){
//			return;
//		}
//
//		//自定义
//		//整个页面 背景统一白色
//		context.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//		//沉浸式状态栏
//		setStatuBar(context);
//
//		View v_actionbar = LayoutInflater.from(context).inflate(R.layout.layout_actionbar, null);
//		actionBar.setCustomView(v_actionbar);
//
//		Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
//		context.setSupportActionBar();
//		actionBar.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
	}
//	public static void initActionBar(Activity context) {
//
//		//自定义
//		ActionBar actionBar = context.getActionBar();
//		if(null==actionBar){
//			L.e("null==actionBar");
//			return;
//		}
//		//整个页面 背景统一白色
//		context.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//		//沉浸式状态栏
//		setStatuBar(context);
//
//		View v_actionbar = LayoutInflater.from(context).inflate(R.layout.layout_actionbar, null);
//		actionBar.setCustomView(v_actionbar);
//		actionBar.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
//	}

	/**
	 * 设置标题
	 * @param str
	 * @param context
	 */
	public static void setTitle(String str, Activity context) {
		if (null != context.findViewById(R.id.title))
			((TextView) context.findViewById(R.id.title)).setText(str);
	}

	public static void setRight(String str, Activity context) {
		if (null != context.findViewById(R.id.tv_right)){
			TextView tv_right = (TextView) context.findViewById(R.id.tv_right);
			tv_right.setText(str);
			tv_right.setVisibility(View.VISIBLE);
		}
	}
	public static void setRightImage(int resId, Activity context) {
		if (null != context.findViewById(R.id.iv_right)){
			ImageView ivRight = (ImageView) context.findViewById(R.id.iv_right);
			ivRight.setImageResource(resId);
			context.findViewById(R.id.iv_right).setVisibility(View.VISIBLE);
		}
	}


	public static void setLeftClick(Activity context,View.OnClickListener clickListener) {
		if (null != context.findViewById(R.id.v_left))
			((ImageView) context.findViewById(R.id.v_left)).setOnClickListener(
					clickListener!=null?clickListener:new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.finish();
				}
			});
	}


	private static void setStatuBar(Activity context){
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
