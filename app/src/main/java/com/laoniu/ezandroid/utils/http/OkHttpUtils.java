package com.laoniu.ezandroid.utils.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.dialog.WKDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 类名：OkHttpUtils
 * 类描述：网络请求
 */
public class OkHttpUtils {


	public static OkHttpClient getClient() {
		return WKOkhttpClient.getInstance();
	}

	public static void get(String url,Map<String, String> params, OkHttpCallback callback){
		if(!T.isNetworkAvailable()){
			if(callback != null){
				callback.onFail(App.getInstance().getString(R.string.network_error));
			}
			return;
		}
		if(callback.hasProgressDialog) {
			WKDialog.showProgressDialog(true, App.getInstance().getString(R.string.loading));
		}
		if (!params.isEmpty()) {
			StringBuffer buffer = new StringBuffer(url);
			buffer.append('?');
			for (Map.Entry<String,String> entry: params.entrySet()) {
				buffer.append(entry.getKey());
				buffer.append('=');
				buffer.append(entry.getValue());
				buffer.append('&');
			}
			buffer.deleteCharAt(buffer.length()-1);
			url = buffer.toString();
		}
		Log.e("请求串", url);
		Request request = new Request.Builder()
//				.header("apiKey", "xxx")  //正式
				.url(url)
				.get().build();
		getClient().newCall(request).enqueue(callback);
	}

	public static void post(String url, Map<String, String> params, OkHttpCallback callback) {
		if(!T.isNetworkAvailable()){
			if(callback != null){
				T.toast("网络未连接！");
			}
			return;
		}
//		params.put("signType", "RSA");
//		if (url.contains("login")) {
//			params.put("ip", "127.0.0.1");
//		}
		String paramsJSON = JSON.toJSONString(params);
		Log.e("请求串", url + "\n" + paramsJSON);
		Request request = new Request.Builder()
//				.header("User-Agent", "Android")
				.url(url)
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramsJSON))
				.build();
		getClient().newCall(request).enqueue(callback);
	}


	/**
	 * @param url        下载链接
	 * @param startIndex 下载起始位置
	 * @param endIndex   结束为止
	 * @param callback   回调
	 * @throws IOException
	 */
	public void downloadFileByRange(String url, long startIndex, long endIndex, OkHttpCallback callback) throws IOException {
		// 创建一个Request
		// 设置分段下载的头信息。 Range:做分段数据请求,断点续传指示下载的区间。格式: Range bytes=0-1024或者bytes:0-1024
		Request request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + endIndex)
				.url(url)
				.build();
		getClient().newCall(request).enqueue(callback);
	}

	public void getContentLength(String url, OkHttpCallback callback) throws IOException {
		// 创建一个Request
		Request request = new Request.Builder()
				.url(url)
				.build();
		getClient().newCall(request).enqueue(callback);
	}
}
