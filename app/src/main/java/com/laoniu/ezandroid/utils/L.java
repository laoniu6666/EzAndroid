package com.laoniu.ezandroid.utils;

import android.util.Log;

/**
 *
 * @Author： laoniu
 * @Time： 2020/5/19 16:08
 * @description：
 */
public class L {
//	L.e(getClass().getName()+"---");

    public static boolean debug=true;
    public static String TAG="laoniu";

    public static void e(String text){
        if (debug){
            Log.e(TAG,text);
        }
    }
    public static void i(String text){
        if (debug){
            Log.i(TAG,text);
        }
    }
    public static void d(String text){
        if (debug){
            Log.d(TAG,text);
        }
    }


}
