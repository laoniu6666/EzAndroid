package com.laoniu.ezandroid.utils.http;

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class MyInterceptor
        implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String url = original.url().toString();
        Response response = chain.proceed(original);
        Log.e("zwk",String.format("...\n请求链接：%s\n请求参数：%s\n请求响应%s", original.url(), getRequestInfo(original), getResponseInfo(response)));
        return response;
    }
 
    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private String getRequestInfo(Request request) {
        String str = "";
        if (request == null) {
            return str;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return str;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = Charset.forName("utf-8");
            str = bufferedSink.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decryptData(str,"request_data");
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private String getResponseInfo(Response response) {
        String str = "";
        if (response == null || !response.isSuccessful()) {
            return str;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("utf-8");
        if (contentLength != 0) {
            str = buffer.clone().readString(charset);
        }
        return decryptData(str,"return_data");
    }


    public static String decryptData(String str,String key) {
        String result="";
        try {
            JSONObject jo = new JSONObject(str);
            String request_data = jo.optString(key);
            result = decrypt(request_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decrypt(String content) {

        try {
            byte[] encryptSHA384 = EncryptUtils.encryptSHA384(SignUtils.SECRET.getBytes("utf-8"));
            byte[] key = Arrays.copyOfRange(encryptSHA384, 0, 32);
            byte[] iv = Arrays.copyOfRange(encryptSHA384, 32, 48);

            return byte2String(EncryptUtils.decryptBase64AES(
                    content.getBytes("utf-8"), key, "AES/CBC/PKCS5Padding",
                    iv
            ));
        } catch (Exception e) {
            Log.e("zwk","解码失败");
            e.printStackTrace();
        }
        return null;
    }
    public static String byte2String(byte[] data){
        return new String(data, Charset.forName("UTF-8"));
    }
 
}