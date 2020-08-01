package com.laoniu.ezandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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
