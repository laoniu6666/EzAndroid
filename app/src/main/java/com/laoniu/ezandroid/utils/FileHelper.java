package com.laoniu.ezandroid.utils;

import android.os.Environment;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

	//TODO 1代表true 0代表false

	// 通过Environment得到SD的状态和路径  
    private final static String FileDirPath = Environment.getExternalStorageState().equals(  
            Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory()+"/laoniu" : "";

    private final static String configFilePath=FileDirPath+"/laoniu_confing.txt";


	public static void createRootDir(){
		File f = new File(FileDirPath);
		if(!f.exists()){
			f.mkdirs();
			L.e("createRootDir成功");
		}
	}
	public static boolean putString(final String key,String value){
		createFile(configFilePath);
		String text = txt2String();
		JSONObject jo=null;
		if(T.isEmptyObj(text)){
			jo = new JSONObject();
		}else{
			jo = JSONObject.parseObject(text);
		}
		if(T.isEmptyObj(jo)){
			jo = new JSONObject();
		}
		jo.put(key,value);
		L.e("FileHelper-new value:"+value);
		boolean yes = String2txt(jo.toJSONString());
		return yes;
	}

	//获取状态
	public static String getString(final String key) {
		String text = txt2String();
		if(T.isEmptyObj(text)){
			return "";
		}
		L.e("FileHelper-old string:");
		L.e(text);
		JSONObject jo = JSONObject.parseObject(text);
		if(T.isEmptyObj(jo)){
			return "";
		}
		String value = jo.getString(key);
		if(T.isEmptyObj(value)){
			return "";
		}
		L.e("FileHelper-old value:"+value);
		return value;
	}

	private static boolean String2txt(String str) {
		try {
			FileWriter fw = new FileWriter(configFilePath);
			fw.flush();
			fw.write(str);
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String txt2String() {
		InputStreamReader inputStreamReader = null;
		try {
			File f = new File(configFilePath);
			if(!f.exists()){
				return null;
			}
			FileInputStream inputStream = new FileInputStream(f);
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuilder sb = new StringBuilder("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	private static void createFile(String configFilePath){
		File f  = new File(configFilePath);
		if(!f.exists()){
			try {
				L.e("创建配置文件开始..");
				f.createNewFile();
				L.e("创建配置文件成功！");
			} catch (IOException e) {
				L.e("创建配置文件失败，你不会用的模拟器吧？");
				e.printStackTrace();
			}
		}
	}
}
