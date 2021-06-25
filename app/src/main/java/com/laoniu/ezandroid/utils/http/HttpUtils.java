package com.laoniu.ezandroid.utils.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.T;
import com.laoniu.ezandroid.view.dialog.WKDialog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class HttpUtils {

    Handler handler;

    public static void get(String url, Map<String, Object> requestBody, HttpUtilsCallback call) {
        get(url, requestBody, null, call);
    }

    public static void get(String url, Map<String, Object> requestBody, Map<String, String> requestHeader,
            HttpUtilsCallback call) {
        http_Async(url, "GET", requestBody, requestHeader, call);
    }

    public static void post(String url, Map<String, Object> requestBody, HttpUtilsCallback call) {
        post(url, requestBody, null, call);
    }

    public static void post(String url, Map<String, Object> requestBody, Map<String, String> requestHeader,
            HttpUtilsCallback call) {
        http_Async(url, "POST", requestBody, requestHeader, call);
    }


    public static void http_Async(final String url, final String httpMethod, final Map<String, Object> requestBody,
            final Map<String, String> requestHeader, final HttpUtilsCallback call) {
        final HttpUtils http = new HttpUtils();
        final Handler handler = http.getHandler(call);
        new Thread(new Runnable() {
            @Override
            public void run() {
                http.http_Sync(url, httpMethod, requestBody, requestHeader, handler);
            }
        }).start();
    }

    public void http_Sync(String url, String httpMethod, Map<String, Object> param, Map<String, String> requestHeader,
            Handler handler) {
        HttpURLConnection httpConnection = null;
        StringBuilder builder = new StringBuilder();
        try {
            StringBuilder params = new StringBuilder();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                params.append(entry.getKey());
                params.append("=");
                params.append(entry.getValue().toString());
                params.append("&");
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.lastIndexOf("&"));
            }
            URL restServiceURL = new URL(url + (params.length() > 0 ? "?" + params.toString() : ""));
            Log.e("zwk", restServiceURL.toString());
            httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod(httpMethod);
            httpConnection.setRequestProperty("Content-type", "application/json");
            if (null != requestHeader) {
                for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                    httpConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Message errMsg = Message.obtain();
                errMsg.what = 1;
                errMsg.obj = httpConnection.getResponseCode();
                handler.sendMessage(errMsg);
            } else {
                InputStream inStrm = httpConnection.getInputStream();
                byte[] b = new byte[2048];
                int length = -1;
                while ((length = inStrm.read(b)) != -1) {
                    builder.append(new String(b, 0, length));
                }
                L.e("--------------------");
                L.e(builder.toString());
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = builder.toString();
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }


    private Handler getHandler(final HttpUtilsCallback call) {
        if (null == handler) {
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String resultJson = msg.obj + "";
                    if (msg.what == 0) {
                        call.OnCall(resultJson);
                    } else {
                        WKDialog.showProgressDialog("正在发送验证码..");
                        T.toast("服务器错误，code=" + resultJson);
                    }
                }
            };
        }
        return handler;
    }

    public interface HttpUtilsCallback {

        void OnCall(String jsonStr);
    }

}