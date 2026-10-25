package com.laoniu.ezandroid.ui.act;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.laoniu.ezandroid.BaseBindingActivity;
import com.laoniu.ezandroid.databinding.ActScrollviewtestBinding;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.view.adapter.MyRecycleViewAdapter;
import com.laoniu.ezandroid.view.divider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SrollViewTestActivity extends BaseBindingActivity<ActScrollviewtestBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.act_scrollviewtest;
    }

    @Override
    public void initView() {
        //准备数据源
        binding.fl1.getLayoutParams().width= ScreenUtils.getScreenWidth();
        binding.fl2.getLayoutParams().width=ScreenUtils.getScreenWidth();
        binding.fl3.getLayoutParams().width=ScreenUtils.getScreenWidth();
        setData(binding.recyclerView);
        setData(binding.recyclerView2);
        setData(binding.recyclerView3);
    }
    int tag=0;
    private void setData(RecyclerView recyclerView){
        List lists = new ArrayList<>();
        tag++;
        lists.addAll(getNetWorkData("recyclerView"+tag+",item="));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.divider_ListView)));
        @SuppressLint("ResourceType")
        MyRecycleViewAdapter<String> adapter = new MyRecycleViewAdapter<String>(lists,R.layout.item_rcv_test) {
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