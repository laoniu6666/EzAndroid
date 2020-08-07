package com.laoniu.ezandroid.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.laoniu.ezandroid.utils.ActionBarHelper;
import com.laoniu.ezandroid.utils.WKHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity implements Handler.Callback {

    public static WKHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new WKHandler(this);
    }


    public void onLeftClick(){
        ActionBarHelper.setLeftClick(this,null);
    }
    public void onLeftClick(View.OnClickListener clickListener){
        ActionBarHelper.setLeftClick(this,null);
    }

    public void setTitle(String title){
        ActionBarHelper.setTitle(title,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=handler){
            handler.removeCallbacksAndMessages(null);
            handler=null;
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }
}
