package com.laoniu.ezandroid.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.T;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 类名：AsyncHttpRequest<br>
 * 类描述：网络请求<br>
 * 创建日期：2016年9月1日 下午3:54:07
 */
public class OkHttpUtils {

	public static OkHttpClient client;

	public static OkHttpClient getClient() {
		if(null==client){
			synchronized (client) {
				if (null == client) {
					client = new OkHttpClient();
					OkHttpClient.Builder builder = client.newBuilder()
							.connectTimeout(60, TimeUnit.SECONDS)
							.readTimeout(60, TimeUnit.SECONDS)
							.writeTimeout(60, TimeUnit.SECONDS);
					client = builder.build();
				}
			}
		}
		return client;
	}

	public static Handler handler = new Handler(Looper.getMainLooper());

	public static void get(String url, OkHttpCallback callback){
		if(!T.isNetworkAvailable()){
			Toast.makeText(App.getInstance(), "网络连接失败！", Toast.LENGTH_SHORT).show();
			return;
		}
		Request request = new Request.Builder()
//				.header("apiKey", "xxx")  //正式
				.url(url)
				.get().build();
		getClient().newCall(request).enqueue(callback);
	}

	public static void post(String url, Map<String, Object> params, OkHttpCallback callback) {
		if(!T.isNetworkAvailable()){
			if(callback != null){
				T.toast("网络未连接！");
			}
			return;
		}
		params.put("signType", "RSA");
//		if (url.contains("login")) {
//			params.put("ip", "127.0.0.1");
//		}
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String str = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = "" + params.get(key);
			str += key + "=" + value + "&";
		}
		L.e(str.substring(0, str.length() - 1));

		String paramsJSON = JSON.toJSONString(params);
		Log.e("调用APP服务请求地址_参数", url + "\n" + paramsJSON);
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
