package com.laoniu.ezandroid.view.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MyRecycleViewAdapter<T> extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder>{
    List<T> list;
    int ItemViewId=-1;
    View convertView;

    public MyRecycleViewAdapter(List<T> list, @IdRes int ItemViewId){
        this.list=list;
        this.ItemViewId=ItemViewId;
    }
    public MyRecycleViewAdapter(List<T> list, View itemView){
        this.list=list;
        this.convertView=itemView;
    }

    @NonNull
    @Override
    public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        convertView = ItemViewId==-1 ? convertView : View.inflate(parent.getContext(),ItemViewId,null);
        //推荐使用layoutInflater,防止holder中view获取不到LayoutParam问题,使用上面的方法需要xml布局中childView必须有父类ViewGroup
        convertView = ItemViewId==-1 ? convertView : LayoutInflater.from(parent.getContext()).inflate(ItemViewId,parent,false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter.MyViewHolder holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * viewholder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        public MyViewHolder(View v){
            super(v);
            mConvertView = itemView;
            mViews = new SparseArray<View>();
        }

        public <T extends View> T getView(int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    //****************************  out method  *********************************

    /**
     * bind data to view
     * @param holder
     * @param pos
     */
    public abstract void convert(MyViewHolder holder, int pos);

}