package com.laoniu.ezandroid.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.PermissionChecker;

/**
 * @author $user$
 * @time $date$ $time$
 */
public class WKPermission {

    public static int CODE_REQUEST=321;
    public static int CODE_APPSETTING=222;

    public Activity act;
    public ZWKCallback call;
    public String[] permissions;

    public WKPermission(Activity act, ZWKCallback call){
        this.act=act;
        this.call=call;
    }
    public WKPermission setRequestCode(int CODE_REQUEST,int CODE_APPSETTING){
        this.CODE_REQUEST=CODE_REQUEST;
        this.CODE_APPSETTING=CODE_APPSETTING;
        return this;
    }

    public WKPermission checkPermission(){
        checkPermission(permissions_all);
        return this;
    }

    public void checkPermission(String[] permissions){
        this.permissions=permissions;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //正常显示
            call.onCall(0);
        }else {
            // 检查该权限是否已经获取
//                if (ContextCompat.checkSelfPermission(act, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (!hasPermission(act, permissions)) {
                showDialogTipUserRequestPermission();
            } else {
                call.onCall(0);
            }
        }
    }
    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(act)
                .setTitle("权限不可用")
                .setMessage("系统需要你允许部分授权，否则您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        act.finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    @SuppressLint("NewApi")
    private void startRequestPermission() {
        act.requestPermissions(permissions, CODE_REQUEST);
    }
    // 用户权限 申请 的回调方法
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!hasPermission(act, permissions)) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                            boolean b = act.shouldShowRequestPermissionRationale(permissions[i]);
                            if (!b) {
                                // 用户还是想用我的 APP 的
                                // 提示用户去应用设置界面手动开启权限
                                showDialogTipUserGoToAppSettting();
                            } else {
                                T.toast("退出");
                                act.finish();
                            }
                        } else {
                            L.e("第"+i+"个权限获取成功！");
                        }
                    }
                } else {
                    T.toast("权限获取成功！");
                    call.onCall(0);
                }

//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
//                    boolean b = act.shouldShowRequestPermissionRationale(permissions[0]);
//                    if (!b) {
//                        // 用户还是想用我的 APP 的
//                        // 提示用户去应用设置界面手动开启权限
//                        showDialogTipUserGoToAppSettting();
//                    } else {
//                        T.toast("退出");
//                        act.finish();
//                    }
//                } else {
//                    T.toast("权限获取成功！");
//                    call.onCall(0);
//                }
            }
        }
    }

    private AlertDialog settingDlg;
    private void showDialogTipUserGoToAppSettting() {
        L.e("弹出设置框");
        if(null!=settingDlg){
            if(settingDlg.isShowing()){
                settingDlg.dismiss();
            }
            settingDlg=null;
        }
        settingDlg = new AlertDialog.Builder(act)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许权限授权，否则您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 跳转到应用设置界面
                        goToAppSetting();
                        if(null!=settingDlg){
                            if(settingDlg.isShowing()){
                                settingDlg.dismiss();
                            }
                            settingDlg=null;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        T.toast("退出");
                        act.finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        call.onCall(true);
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", act.getPackageName(), null);
        intent.setData(uri);
        act.startActivityForResult(intent, CODE_APPSETTING);
    }

    public void onActivityResult(int requestCode) {
        if(requestCode==CODE_APPSETTING){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermission(permissions);
            }
        }
    }

    private boolean hasPermission(Context context, String[] permission_array) {
        List<String> permissions = Arrays.asList(permission_array);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        for (String permission : permissions) {
            String op = AppOpsManagerCompat.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) continue;
            int result = AppOpsManagerCompat.noteProxyOp(context, op, context.getPackageName());
            if (result == AppOpsManagerCompat.MODE_IGNORED) return false;
//            result = ContextCompat.checkSelfPermission(context, permission);
            result = PermissionChecker.checkSelfPermission(context, permission);

            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    // 要申请的权限
    private String[] permissions_all = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WAKE_LOCK
    };
}
