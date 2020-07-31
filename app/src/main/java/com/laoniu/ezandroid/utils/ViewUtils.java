package com.laoniu.ezandroid.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.laoniu.ezandroid.R;

public class ViewUtils {

    public static int getDividerColor(){
        return ColorUtils.getColor(R.color.divider_ListView);
    }

    public static void setEditText(EditText et, ImageView imageView, ZWKCallback callback){
        setTextChangedListener(et,imageView);
        setKeyBoardCanClick(et,callback);
    }

    /**
     * 监听软键盘Enter
     * @param et
     * @param callback
     */
    public static void setKeyBoardCanClick(EditText et, ZWKCallback callback){
        et.setImeOptions(EditorInfo.IME_ACTION_GO);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((!T.isFastClick() &&actionId== EditorInfo.IME_ACTION_GO)) {
                    callback.onCall(null);
                    return true;
                }
                return false;
            }
        });
    }

    public static void setTextChangedListener(final EditText edittext, final ImageView imageView){
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageView.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (edittext.getText().toString().equals("")){
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.setText("");
            }
        });
    }
    public static void setTextChangedListener_Dot(final EditText edittext){
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String sss = "";
                    String string = s.toString().replace(",", "");
                    int b = string.length() / 3;
                    if (string.length() >= 3 ) {
                        int yushu = string.length() % 3;
                        if (yushu == 0) {
                            b = string.length() / 3 - 1;
                            yushu = 3;
                        }
                        for (int i = 0; i < b; i++) {
                            sss = sss + string.substring(0, yushu) + "," + string.substring(yushu, 3);
                            string = string.substring(3, string.length());
                        }
                        sss = sss + string;
                        edittext.setText(sss);
                    }
                }
                edittext.setSelection(edittext.getText().length());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public static String getText_Dot(final EditText edittext){
        return edittext.getText().toString().replace(",", "");
    }

}
