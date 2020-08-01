package com.laoniu.ezandroid.ui.act;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.model.Now_players_info;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.utils.WKBitmapUtils;
import com.laoniu.ezandroid.utils.WKCallback;
import com.laoniu.ezandroid.view.LuckPan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {


    @BindView(R.id.luckpan)
    LuckPan luckpan;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.rl_pan)
    RelativeLayout rl_pan;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    List<Now_players_info> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        int imgWidth = SizeUtils.dp2px(60);

        playerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Now_players_info bean = new Now_players_info();
            bean.avator="";
            bean.name="name=0"+i;
            playerList.add(bean);
        }
        int mItemAnge=360/playerList.size();
        for (int i = 0; i < playerList.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.caomei);
            bitmap = WKBitmapUtils.toRoundBitmap(bitmap);
            bitmap = WKBitmapUtils.scaleBitmap(bitmap,imgWidth,imgWidth);
            bitmap = WKBitmapUtils.rotateBitmap(bitmap,mItemAnge*i);
            playerList.get(i).bmp=bitmap;
        }

        ll_container.removeAllViews();
        for (int i = 0; i < playerList.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(playerList.get(i).bmp);
            ll_container.addView(iv);
        }


        addPan();
        luckpan.setWKCallback(new WKCallback<Now_players_info>() {
            @Override
            public void onCall(Now_players_info bean) {
                T.toast(""+bean.name);
            }
        });
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start();
//                show(tvStart);
                if(playerList.size()<10){
                    Now_players_info bean = new Now_players_info();
                    bean.avator="";
                    bean.name="name=0"+(playerList.size()+1);
                    bean.name="name=0"+(playerList.size()+1);
                    playerList.add(bean);
                }
                addPan();
            }
        });
        tvStart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                start();
//                show(tvStart);
                return true;
            }

        });
    }

    public void addPan(){
        rl_pan.removeAllViews();
        luckpan = new LuckPan(TestActivity.this);
        luckpan.setLayoutParams(new ViewGroup.LayoutParams(T.dp2px(300f),T.dp2px(300f)));
        rl_pan.addView(luckpan);
        luckpan.setItems(playerList);
    }

    private void start(){
        luckpan.setItems(playerList);
        luckpan.setLuckNumber(0);
        luckpan.startAnim();
    }

    public void show(View parentView){
        TextView tv = new TextView(this);
        String join_coins = 1+"";
        tv.setText(getString(R.string.dialog_superWinner_join,1,"90%"));
//        tv.setText("111111111");
        tv.setTextSize(T.dp2px(20));
        tv.setTextColor(getColor(R.color.color_text_black));
        tv.setLayoutParams(new ViewGroup.MarginLayoutParams(-1,-2));

        PopupWindow popupWindow = new PopupWindow(tv,-1, -1);
        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        popupWindow.showAsDropDown(parentView);

    }


}