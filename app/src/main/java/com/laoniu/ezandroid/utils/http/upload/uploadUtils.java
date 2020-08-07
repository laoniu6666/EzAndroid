package com.laoniu.ezandroid.utils.http.upload;

import com.laoniu.ezandroid.utils.L;
import com.laoniu.ezandroid.utils.http.WKHttp;
import com.laoniu.ezandroid.utils.http.WKOkhttpClient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Time:2020/05/27 22:54
 * Author: laoniu
 * Description:
 */
public class uploadUtils {

    final int UploadType_All=0;
    final int UploadType_hasParams=1;
    final int UploadType_hasProgress=2;

    /**
     *  上传文件
     * @param url
     * @param contentType
     * @param callBack
     */
    public void upLoadFile(String url, final MediaType contentType, String filepath, UploadCallback callBack) {
        upLoadFile_withParamAndProgress(url,contentType,null,filepath,0,callBack);
    }

    /**
     * 带参数上传
     * @param url
     * @param contentType
     * @param paramsMap
     * @param callBack
     */
    public void upLoadFile_withParam(String url, final MediaType contentType,HashMap<String, Object> paramsMap, UploadCallback callBack) {
        upLoadFile_withParamAndProgress(url,contentType,paramsMap,null, 1,callBack);
    }

    /**
     * 带参数带进度上传
     * @param url 接口地址
     * @param paramsMap 参数
     * @param filePath 文件路径
     * @param contentType 文件类型，例如：MediaType.parse("image/png")
     * @param callBack 回调
     */
    public void upLoadFile_withParamAndProgress(String url,final MediaType contentType, HashMap<String, Object> paramsMap,String filePath,int UploadType,  final UploadCallback callBack) {
        try {
            RequestBody body;
            if(UploadType==0){
                body = RequestBody.create(contentType, filePath);
            }else{
                MultipartBody.Builder builder = new MultipartBody.Builder();
                //设置类型
                builder.setType(MultipartBody.FORM);
                //追加参数
                for (String key : paramsMap.keySet()) {
                    Object object = paramsMap.get(key);

                    if (!(object instanceof File)) {
                        builder.addFormDataPart(key, object.toString());
                    } else {
                        File file = (File) object;
                        //是否带进度
                        if(UploadType==1){
                            builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                        }else{
                            builder.addFormDataPart(key, file.getName(), createProgressRequestBody(contentType, file, callBack));
                        }
                    }
                }
                //创建RequestBody
                body = builder.build();
            }

            //创建Request
            final Request request = new Request.Builder().url(url).post(body).build();
            final Call call = WKOkhttpClient.getInstance().newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    L.e(e.toString());
                    callBack.onFail("上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        L.e("response ----->" + string);
                        callBack.onSuccess(string);
                    } else {
                        callBack.onFail("上传失败");
                    }
                }
            });
        } catch (Exception e) {
            L.e(e.toString());
        }
    }

    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param callBack 回调
     * @param <T>
     * @return
     */
    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file, final UploadCallback callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long maxLength = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        L.e( "current------>" + current);
                        callBack.onProgress(maxLength, current);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    interface UploadCallback{
        void onProgress(long maxLength,long current);
        void onSuccess(String msg);
        void onFail(String msg);
    }
}
