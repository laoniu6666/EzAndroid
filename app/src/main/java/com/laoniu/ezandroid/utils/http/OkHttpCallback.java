package com.laoniu.ezandroid.utils.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.dialog.WKDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类名：RequestCallback<br>
 * 类描述：HTTP请求回调<br>
 * 创建日期：2016年9月10日 下午3:42:26
 */
public abstract class OkHttpCallback implements Callback {

	private static Handler handler = new Handler(Looper.getMainLooper());
	public boolean hasProgressDialog=true;

	private String error_json="server jsonStr error";

	public OkHttpCallback(){
	}
	public OkHttpCallback(boolean hasProgressDialog){
		this.hasProgressDialog=hasProgressDialog;
	}

	public void onResponse(Call call, Response response) throws IOException {
		if(hasProgressDialog){
			handler.post(new Runnable() {
				public void run() {
					WKDialog.dissmissProgressDialog();
				}
			});
		}
		L.e("responseCode:\n"+ response.code() + "");
		if (response.isSuccessful()) {
			String string = response.body().string();
			L.e("返回参数:\n"+ string);

			JSONObject jo=null;
			try {
				jo = JSON.parseObject(string);
			}catch (Exception e){
				L.e(error_json);
			}
			if(jo==null){
				sendFailure(call,error_json);
			}else{
				sendSuccess(call, jo);
			}
		} else {
			sendFailure(call,"code="+response.code());
		}
	}

	public final void onFailure(Call call, IOException e) {
		L.e("net work error:\n"+e.getLocalizedMessage().toString());
		sendFailure(call,"network error");
	}

	private void sendSuccess(final Call call, final JSONObject obj) {
		handler.post(new Runnable() {
			public void run() {
				onSuccess(obj);
				call.cancel();
			}
		});
	}

	private void sendFailure(final Call call,final String errMsg) {
		handler.post(new Runnable() {
			public void run() {
				onFail(errMsg);
				call.cancel();
			}
		});
	}

	public abstract void onSuccess(JSONObject obj);

	public void onFail(String errMsg){
		ToastUtils.showShort(errMsg);
	};
}
