package com.laoniu.ezandroid.view.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.utils.T;

import androidx.appcompat.app.AlertDialog;

/**
 * Time:2020/05/27 17:12
 * Author: laoniu
 * Description:
 */
public class WKDialog {

    public static ProgressDialog showProgressDialog(String msg) {
        return showProgressDialog(false, "加载中..");//默认不可手动取消
    }

    static ProgressDialog waitingDialog;

    public static ProgressDialog showProgressDialog(boolean cancelable, String msg) {
        dissmissProgressDialog();
        waitingDialog = new ProgressDialog(App.getInstance());
        waitingDialog.setTitle("系统提示");
        waitingDialog.setMessage(msg);
        waitingDialog.setIndeterminate(true);
//        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.setCancelable(cancelable);
        waitingDialog.show();
        return waitingDialog;
    }

    public static void dissmissProgressDialog() {
        if (null != waitingDialog && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
            waitingDialog = null;
        }
    }


    /*************************************************************/

    public static void showSureDialog(String content) {
        showSureDialog(content, null);
    }

    public static void showSureDialog(String content, final WKDialogCallback callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(App.getInstance());
        builder.setTitle("系统提示");
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != callback) {
                    callback.onCall();
                }
            }
        });
        builder.show();
    }


    /*************************************************************/
    static String password = "123";

    public static void showKeyDialog(Context act, final WKDialogCallback call) {
        Dialog d = new Dialog(act);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll = new LinearLayout(act);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp);

        final EditText et = new EditText(act);
        et.setLayoutParams(lp);
        et.setLayoutParams(lp);

        Button btn = new Button(act);
        btn.setLayoutParams(lp);
//        btn.setBackgroundColor(Color.parseColor("#00a5e0"));
//        btn.setTextColor(Color.parseColor("#ffffff"));
        btn.setText("确定");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et.getText().toString();
                if (str.equals(password)) {
                    d.dismiss();
                    call.onCall();
                } else {
                    T.toast("密码输入错误!");
                }
            }
        });
        ll.addView(et);
        ll.addView(btn);

        d.setContentView(ll);
        d.setCancelable(true);
        d.setTitle("请输入密码进入");
        d.show();
    }

    public interface WKDialogCallback {

        void onCall();
    }
}
