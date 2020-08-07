package com.laoniu.ezandroid.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.adapter.MyRecycleViewAdapter;
import com.laoniu.ezandroid.view.divider.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseRecycleViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<String> lists;
    MyRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
//        准备数据源
        lists = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.divider_ListView)));
        adapter = new MyRecycleViewAdapter<String>(lists,R.layout.item_rcv_test) {
            @Override
            public void convert(MyViewHolder holder, int pos) {
                TextView tv = holder.getView(R.id.text1);
                tv.setGravity(Gravity.CENTER);
                tv.setText(lists.get(pos)+"");
            }
        };
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        loadData(true);
    }

    private void loadData(final boolean isLoadMore){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(T.getMessage(isLoadMore?1:0,getNetWorkData()));
            }
        }).start();
    }

    private List<String> getNetWorkData(){
        List<String> data=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("list" + i);
        }
        return data;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what){
            case 0://刷新
                refreshUI(false,(List<String>)msg.obj);
                break;
            case 1://加载更多
                refreshUI(true,(List<String>)msg.obj);
                break;
        }
        return false;
    }

    private void refreshUI(boolean isLoadMore,List<String> moreList){
        if(!isLoadMore){
            lists.clear();
            refreshLayout.finishRefresh(true);
        }else{
            refreshLayout.finishLoadMore(true);
        }
        int startPos = lists.size();
        lists.addAll(moreList);
        int endPos = lists.size();

        if(!isLoadMore){
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeInserted(startPos,endPos);
        }
    }
}