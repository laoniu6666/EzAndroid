package com.laoniu.ezandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.laoniu.ezandroid.App;


public class SharedPreferencesUtils {

	/**
	 * 存储布尔型
	 */
	public static void put(String key, boolean value) {
		Editor editor = getSharedPreferences().edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 存储整型
	 */
	public static void put(String key, int value) {
		Editor editor = getSharedPreferences().edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 存储字符串
	 */
	public static void put(String key, String value) {
		Editor editor = getSharedPreferences().edit();
		if (value == null) {
			value = "";
		}
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 存储浮点型
	 */
	public static void put(String key, float value) {
		Editor editor = getSharedPreferences().edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	/**
	 * 获取浮点型数据
	 */
	public static float getFloat(String key) {
		return getSharedPreferences().getFloat(key, 0.0f);
	}

	/**
	 * 获取布尔型数据，默认true
	 */
	public static boolean getBooleanTrue(String key) {
		return getSharedPreferences().getBoolean(key, true);
	}

	/**
	 * 获取布尔型数据，默认false
	 */
	public static boolean getBooleanFalse(String key) {
		return getSharedPreferences().getBoolean(key, false);
	}

	/**
	 * 获取整型数据，默认0
	 */
	public static int getInt(String key) {
		return getSharedPreferences().getInt(key, 0);
	}

	/**
	 * 获取整型数据，可设置默认值
	 */
	public static int getInt(String key, int defValue) {
		return getSharedPreferences().getInt(key, defValue);
	}

	/**
	 * 获取字符串数据，默认空
	 */
	public static String getString(String key) {
		return getSharedPreferences().getString(key, "");
	}

	/**
	 * 获取字符串数据，可设置默认值
	 */
	public static String getString(String key, String defValue) {
		return getSharedPreferences().getString(key, defValue);
	}

	/**
	 * 移除数据
	 */
	public static void remove(String key) {
		Editor editor = getSharedPreferences().edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 清除数据
	 */
	public static void clear() {
		Editor editor = getSharedPreferences().edit();
		editor.clear();
		editor.commit();
	}

	private static SharedPreferences getSharedPreferences() {
		if(sp==null){
			sp= App.instance.getSharedPreferences(App.instance.getPackageName(), Context.MODE_MULTI_PROCESS);
		}
		return sp;
	}
	public static SharedPreferences sp;
}