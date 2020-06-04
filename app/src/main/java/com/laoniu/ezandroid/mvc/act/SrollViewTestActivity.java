package com.laoniu.ezandroid.mvc.act;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.ScreenUtils;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.HorizontalScrollView;
import com.laoniu.ezandroid.view.adapter.MyRecycleViewAdapter;
import com.laoniu.ezandroid.view.divider.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SrollViewTestActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;

    @BindView(R.id.fl_1)
    FrameLayout fl_1;
    @BindView(R.id.fl_2)
    FrameLayout fl_2;
    @BindView(R.id.fl_3)
    FrameLayout fl_3;

    @BindView(R.id.hsv)
    HorizontalScrollView hsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scrollviewtest);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
//        准备数据源
        fl_1.getLayoutParams().width=ScreenUtils.getScreenWidth();
        fl_2.getLayoutParams().width=ScreenUtils.getScreenWidth();
        fl_3.getLayoutParams().width=ScreenUtils.getScreenWidth();
        setData(recyclerView);
        setData(recyclerView2);
        setData(recyclerView3);
    }
    int tag=0;
    private void setData(RecyclerView recyclerView){
        List lists = new ArrayList<>();
        tag++;
        lists.addAll(getNetWorkData("recyclerView"+tag+",item="));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.gray_div)));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter<String>(lists,R.layout.item_rcv_test) {
            @Override
            public void convert(MyViewHolder holder, int pos) {
                TextView tv = holder.getView(R.id.text1);
//                tv.setGravity(Gravity.CENTER);
                tv.setText(lists.get(pos)+"");
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private List<String> getNetWorkData(String str){
        List<String> data=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(str+ i);
        }
        return data;
    }

}