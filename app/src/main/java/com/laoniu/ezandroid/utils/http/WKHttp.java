package com.laoniu.ezandroid.utils.http;

import android.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.dialog.WKDialog;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 类名：WKHttp
 * 类描述：网络请求
 */
public class WKHttp {

	private static OkHttpClient getClient() {
		return WKOkhttpClient.getInstance();
	}

	public static WKHttpBuilder get(String url) {
		return new WKHttpBuilder(url,"GET");
	}
	public static WKHttpBuilder post(String url) {
		return new WKHttpBuilder(url,"POST");
	}

	public static class WKHttpBuilder{
		public String url;
		public String httpMethod;
		Map<String, String> params;
		Map<String, String> header;

		public WKHttpBuilder(String url,String httpMethod){
			this.url=url;
			this.params=new ArrayMap<>();
		}

		public WKHttpBuilder header(String key,String value){
			header.put(key,value);
			return this;
		}

		public WKHttpBuilder param(String key,String value){
			params.put(key,value);
			return this;
		}


		public void call(OkHttpCallback callback){
			if(!T.isNetworkAvailable()){
				if(callback != null){
					T.toast("网络未连接！");
				}
				return;
			}
			if(callback.hasProgressDialog) {
				WKDialog.showProgressDialog(true, App.getInstance().getString(R.string.loading));
			}
			//设置请求头
			Request.Builder buider = new Request.Builder();
			buider = setHeader(buider);
			//设置请求体
			Request request= httpMethod.equalsIgnoreCase("GET") ? getRequest(buider) : postRequest(buider);
			getClient().newCall(request).enqueue(callback);
		}

		private Request.Builder setHeader(Request.Builder buider){
//			buider.header("User-Agent", "Android");
			if(header.size()>0){
				Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
				while (iterator.hasNext()){
					Map.Entry<String, String> next = iterator.next();
					buider.addHeader(next.getKey(),next.getValue());
				}
			}
			return buider;
		}
		private Request getRequest(Request.Builder buider){
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
			return buider.url(url).get().build();
		}
		private Request postRequest(Request.Builder buider){
			String paramsJSON = JSON.toJSONString(params);
			return buider.url(url)
					.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramsJSON))
					.build();
		}

	}

	/**
	 * @param url        下载链接
	 * @param startIndex 下载起始位置
	 * @param endIndex   结束为止
	 * @param callback   回调
	 */
	public void downloadFileByRange(String url, long startIndex, long endIndex, OkHttpCallback callback) throws IOException {
		// 创建一个Request
		// 设置分段下载的头信息。 Range:做分段数据请求,断点续传指示下载的区间。格式: Range bytes=0-1024或者bytes:0-1024
		Request request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + endIndex)
				.url(url)
				.build();
		getClient().newCall(request).enqueue(callback);
	}

	/**
	 * 上传图片
	 * @param url
	 * @param imagePath 图片路径
	 * @return 新图片的路径
	 */
	public static void uploadImage(String url, String imagePath,OkHttpCallback callback) {
		uploadImage(url,imagePath,"file",callback);
	}
	public static void uploadImage(String url, String imagePath,String imgKey,OkHttpCallback callback) {
		File file = new File(imagePath);
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart(imgKey, imagePath, RequestBody.create(MediaType.parse("image/png"), file))
				.build();
		Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();
		getClient().newCall(request).enqueue(callback);
	}

	public void getContentLength(String url, OkHttpCallback callback) {
		// 创建一个Request
		Request request = new Request.Builder()
				.url(url)
				.build();
		getClient().newCall(request).enqueue(callback);
	}

}
