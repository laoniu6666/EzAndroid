package com.laoniu.ezandroid.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * 统一管理，方便做数据埋点
 */
public class PageUtils {

    public static void startActivity(Activity from, Class to){
        Intent intent = new Intent();
        intent.setClass(from,to);
        startPage(from,intent);
    }
    public static void startActivity(Activity from, Class to, String data){
        Intent intent = new Intent();
        intent.putExtra("data",data);
        intent.setClass(from,to);
        startPage(from,intent);
    }
    public static void startActivity(Activity from, Class to, int data){
        Intent intent = new Intent();
        intent.putExtra("data",data);
        intent.setClass(from,to);
        startPage(from,intent);
    }
    public static void startActivityForResult(Activity from, Class to, int requestCode){
        Intent intent = new Intent();
        intent.setClass(from,to);
        startPageForResult(from,intent,requestCode);
    }
    public static void startActivityForResult(Activity from, Class to, String data, int requestCode){
        Intent intent = new Intent();
        intent.putExtra("data",data);
        intent.setClass(from,to);
        startPageForResult(from,intent,requestCode);
    }
    public static void startActivityForResult(Activity from, Class to, int data, int requestCode){
        Intent intent = new Intent();
        intent.putExtra("data",data);
        intent.setClass(from,to);
        startPageForResult(from,intent,requestCode);
    }


    //这里做埋点
    private static void startPage(Activity from, Intent intent){
        from.startActivity(intent);
    }
    //这里做埋点
    public static void startPageForResult(Activity from, Intent intent, int requestCode){
        from.startActivityForResult(intent,requestCode);
    }
}
