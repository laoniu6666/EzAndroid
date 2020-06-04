package com.laoniu.ezandroid.view.WKTable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.T;

import java.util.ArrayList;
import java.util.List;


public class WKTableView extends LinearLayout {
	
	public static final int viewType_textview=0;
	public static final int viewType_edittext=1;
	public static final int viewType_button=2;
	public static final int viewType_checkbox=3;
	
	public TableLayout tableLayout;//表体
	
	public Drawable divider;
	public boolean d=false;

    public WKTableView(Context context) {
        this(context, null);
    }
    
    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	super.onLayout(changed, l, t, r, b);
	}
 
    public WKTableView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	tableLayout= new TableLayout(context);
    	addView(tableLayout);

    	tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_BEGINNING
    			| TableLayout.SHOW_DIVIDER_MIDDLE
    			| TableLayout.SHOW_DIVIDER_END);
    	tableLayout.setDividerDrawable(getDivider());
    	tableLayout.setStretchAllColumns(true);
    }
    
    public void setDivider_resId(int resId_div){
    	divider=getContext().getResources().getDrawable(resId_div);
    }

	/**
	 * 颜色值必须包含透明度
	 * @param color
	 */
	public void setDivider_colorStr(int color){
		divider=new ColorDrawable(0xffff0000);
    }
    public Drawable getDivider(){
    	return divider==null?getContext().getResources().getDrawable(R.drawable.div_blue):divider;
    }

    private boolean VerifyData(WKTableModel model){

    	
    	return true;
    }
    
    public void setAdapter(WKTableModel model){
    	if(!VerifyData(model)){
    		T.toast("数据不对");
    		return;
    	}
    	L.e("行="+model.list_rowName.size());
		L.e("列="+model.list_columnName.size());
		
		//添加列头
		List<SingleData> list0=new ArrayList<>();
		for (int i = 0; i < model.list_columnName.size()+1; i++) {
			SingleData sd = new SingleData();
			if(i==0){
				sd.str = "*";
			}else{
				sd.str = model.list_columnName.get(i-1);
			}
			sd.viewType = viewType_textview;
			list0.add(i,sd);
		}
		model.list_content.add(0, list0);
		
		//重新组合 list_content
		for (int i = 1; i < model.list_content.size(); i++) {
			//添加行头
			SingleData sd0 = new SingleData();
			sd0.str = model.list_rowName.get(i-1);
			sd0.viewType = viewType_textview;
			model.list_content.get(i).add(0, sd0);
		}
		setAdapter_noHead(model.list_content);
    	
    }
	private void setAdapter_noHead(List<List<SingleData>> list_model){
		L.e("刷新");
		tableLayout.removeAllViews();
		
		L.e("rownum="+list_model.size());
		for (int i = 0; i < list_model.size(); i++) {
			List<SingleData> list = list_model.get(i);
			//创建每行
			L.e("colnum="+list.size());
			TableRow tRow = new TableRow(getContext());
			tRow.setShowDividers(TableLayout.SHOW_DIVIDER_BEGINNING
	    			| TableLayout.SHOW_DIVIDER_MIDDLE
	    			| TableLayout.SHOW_DIVIDER_END);
			tRow.setDividerDrawable(getDivider());
			
			for (int j = 0; j < list.size(); j++) {
				SingleData data = list.get(j);
				//创建每一个格子
				addTableView(data,tRow);
			}
			tableLayout.addView(tRow);
		}
	}
	
    
    private void addTableView(SingleData data, TableRow tr){
    	switch (data.viewType) {
		case viewType_textview:
			addTextView(data,tr);
			break;
		case viewType_edittext:
			addEditText(data,tr);
			break;

		default:
			break;
		}
    }
    
    
    private void addTextView(SingleData data, TableRow tRow){
    	TextView tv = new TextView(getContext());
    	tv.setPadding(T.dp2px(6), T.dp2px(6), T.dp2px(6), T.dp2px(6));
    	tv.setTextColor(Color.BLACK);
    	tv.setText(data.str);
    	tv.setTextSize(T.dp2px(6));
    	tv.setGravity(Gravity.CENTER);
    	tRow.addView(tv);
    }
    private void addEditText(SingleData data, TableRow tRow){
    	EditText et = new EditText(getContext());
    	et.setPadding(T.dp2px(6), T.dp2px(6), T.dp2px(6), T.dp2px(6));
    	et.setTextColor(Color.BLACK);
    	et.setTextSize(T.dp2px(6));
    	et.setText(data.str);
    	et.setBackground(null);
    	et.setBackgroundColor(getCellColor(data.backgroudcolor));
    	et.setGravity(Gravity.CENTER);
    	tRow.addView(et);
    }

    private int getCellColor(String colorstr){
    	int defaultColor= Color.parseColor("#ffffff");
    	int color = TextUtils.isEmpty(colorstr)?defaultColor: Color.parseColor(colorstr);
    	return color;
    }
    

}
