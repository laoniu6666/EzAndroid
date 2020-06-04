package com.laoniu.ezandroid.mvc.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.ScreenUtils;
import com.laoniu.ezandroid.utils.http.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    @OnClick(R.id.tv)
    public void onViewClicked() {
        startActivity(new Intent(this,SrollViewTestActivity.class));
    }

}