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


public abstract class BaseBindingActivity<B extends ViewDataBinding> extends AppCompatActivity implements
        Handler.Callback {

    public static WKHandler handler;
    public B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new WKHandler(this);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void setTitleBar(String title) {
        setTitleBar(title, true, null);
    }

    public void setTitleBar(String title, View.OnClickListener onLeftClick) {
        setTitleBar(title, true, onLeftClick);
    }

    public void setTitleBar(String title, boolean hasLeftButton) {
        setTitleBar(title, true, null);
    }

    public void setTitleBar(String title, boolean hasLeftButton, View.OnClickListener onLeftClick) {
        ActionBarHelper.setTitle(title, this);
        ActionBarHelper.setLeft(this, hasLeftButton);
    }

    public Bundle getBundle(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        return bundle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }
}
