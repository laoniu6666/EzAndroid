package com.laoniu.ezandroid.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;
    String testUrl="http://dl2ht3xgfsud3.cloudfront.net/5eccee8c0381977c2b854f89/1000012/h_1591840436.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        L.e("ANDROID_ID="+ANDROID_ID);

//        Glide.with(this)
//                .load(testUrl)
//                .into(iv);
        String url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";
        Glide.with(this)
                .load(testUrl)
                .into(iv);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    @OnClick(R.id.tv)
    public void onViewClicked() {
        startActivity(new Intent(this,TestActivity.class));
    }

}