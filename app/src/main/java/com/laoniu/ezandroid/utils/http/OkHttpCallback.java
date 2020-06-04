package com.laoniu.ezandroid.utils.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.WKHandler;
import com.laoniu.ezandroid.view.dialog.WKDialog;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类名：RequestCallback<br>
 * 类描述：HTTP请求回调<br>
 * 创建日期：2016年9月10日 下午3:42:26
 */
public abstract class OkHttpCallback implements Callback {

	private static final String TAG = "RequestCallback";

	private static Handler handler = new Handler(Looper.getMainLooper());

	public void onResponse(Call call, Response response) throws IOException {
		WKDialog.dissmissProgressDialog();
		Log.e(TAG, response.code() + "");
		if (response.code() == 200 && response.isSuccessful()) {
			JSONObject jo=null;
			try {
				jo = JSON.parseObject(response.body().string());
			}catch (Exception e){
				L.e("服务端返回了错误的json格式");
			}
			if(jo!=null){
				sendSuccess(call, jo);
			}
		} else {
			sendFailure(call);
		}
	}

	public void onFailure(Call call, IOException e) {
		sendFailure(call);
	}

	private void sendSuccess(final Call call, final JSONObject obj) {
		handler.post(new Runnable() {
			public void run() {
				Log.e("Response", obj.toString());
				onSuccess(obj);
				call.cancel();
			}

		});
	}

	private void sendFailure(final Call call) {
		handler.post(new Runnable() {
			public void run() {
				onFailure();
				call.cancel();
			}
		});
	}

	public abstract void onSuccess(JSONObject obj);

	public abstract void onFailure();

}
