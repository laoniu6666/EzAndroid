package com.laoniu.ezandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.laoniu.ezandroid.utils.ActionBarHelper;
import com.laoniu.ezandroid.utils.WKHandler;


public abstract class BaseBindingActivity<B extends ViewDataBinding> extends AppCompatActivity implements Handler.Callback {

    public static WKHandler handler;
    public B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new WKHandler(this);
        binding=DataBindingUtil.setContentView(this,getLayoutId());
        onLeftClick();
        initView();
    }

    public abstract int getLayoutId();
    public abstract void initView();


    public void onLeftClick(){
        ActionBarHelper.setLeftClick(this,null);
    }
    public void onLeftClick(View.OnClickListener clickListener){
        ActionBarHelper.setLeftClick(this,null);
    }

    public void setTitle(String title){
        ActionBarHelper.setTitle(title,this);
    }

    public Bundle getBundle(String data){
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        return bundle;
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
