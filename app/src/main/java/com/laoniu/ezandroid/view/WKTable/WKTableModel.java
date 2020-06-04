package com.laoniu.ezandroid.view.WKTable;

import java.io.Serializable;
import java.util.List;

public class WKTableModel implements Serializable {
	
	public List<String> list_rowName;//行名
	public List<String> list_columnName;//列名
	public List<List<SingleData>> list_content;//内容，每个子list代表每一行
	public String title;//标题
	
}
