package com.laoniu.ezandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.laoniu.ezandroid.App;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class T {
    public static Toast toast;
    public static long lastClickTime=0l;
    public static boolean sdCardExists = Environment
            .getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);

    /**
     * toast不多次弹出
     * @param text
     */
    public static void toast(String text){
        if(TextUtils.isEmpty(text)){
            return;
        }
        if(null==toast){
            toast=Toast.makeText(App.getInstance(),text,Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 0.5防连点
     * @return
     */
    public static boolean isFastClick(){
        return isFastClick(500l);
    }
    public static boolean isFastClick(long time){
        if(System.currentTimeMillis()-lastClickTime<time){
            return true;
        }
        lastClickTime= System.currentTimeMillis();
        return false;
    }
    /**
     * 判断json解析的对象是否为空
     * @return
     */
    public static boolean isEmptyObj(Object obj){
        if(null==obj|| String.valueOf(obj).equals("null")|| String.valueOf(obj).equals("")){
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null!=connectivity) {
            try {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (null !=info && info.isConnected())
                {
                    // 当前网络是连接的
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        // 当前所连接的网络可用
                        return true;
                    }
                }
            }catch (Exception e){

            }
        }
        return false;
    }

    /**
     * 毫秒值转时间
     * @return
     */
    public static String translateMillis2Time(long millis, String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date curDate = new Date(millis);
        return formatter.format(curDate);
    }
    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }
    public static String getTime2(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }
    /**
     * dp转换成px
     */
    public static int dp2px(float dpValue){
        float scale=App.getInstance().getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue){
        float scale=App.getInstance().getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 判断应用是否安装
     * @param packageName
     * @return
     */
    public static boolean isAppInstall(String packageName) {
        final PackageManager packageManager = App.getInstance().getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pn = packageInfos.get(i).packageName;
                if(packageName.equalsIgnoreCase(pn)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 跳转Appstore
     * @param ctx
     * @param packageName
     */
    public static void goAppStore(Context ctx, String packageName) {
        Uri uri = Uri.parse("market://details?id="+packageName);
        Intent BIntent = new Intent(Intent.ACTION_VIEW, uri);
        ctx.startActivity(BIntent);
    }

    /**
     * 使用反射给私有属性赋值
     * @param clazz
     * @param filedName
     * @param value
     */
    public static void setPrivateValue(Class<?> clazz, String filedName, Object value) {
        try {
            Object obj = clazz.newInstance();// 创建一个实例
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                if(fs[i].getName().equalsIgnoreCase(filedName)){
                    fs[i].set(obj, value);//将属性值重新赋值
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义msg
     * @param what
     * @param obj
     * @return
     */
    public static Message getMessage(int what, Object obj){
        Message msg = new Message();
        msg.what=what;
        msg.obj=obj;
        return msg;
    }

}
