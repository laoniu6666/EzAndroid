package com.laoniu.ezandroid.utils.http;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.laoniu.ezandroid.utils.L;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignUtils {

    static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SECRET = "MDkwMTk5";

    public static RequestBody getSignedData(Map<String, Object> request_map){
        String fromJson = GsonUtils.toJson(request_map);
//        String fromJson = GsonUtil.toJson(request_map);
        L.e("fromJson="+fromJson);
        //加密
        byte[] encrypt = encrypt(fromJson);

        Map params =new HashMap<String, Object>();
        params.put("request_data",SignUtils.byte2String(encrypt));

        String toJson = GsonUtils.toJson(params);
        L.e("toJson="+toJson);
        byte[] encryptByte = ConvertUtils.string2Bytes(toJson);
        return RequestBody.create(MEDIA_TYPE_JSON, encryptByte);
    }



    public static byte[] encrypt(String content) {
        try {
            byte[] data = content.getBytes("utf-8");
            //文本内容不足16字节倍数，补齐内容为差值
            int len = 16 - data.length % 16;
            for (int i = 0; i < len; i++) {
                data = ArrayUtils.add(data, " ".getBytes("utf-8"));
            }
            //key进行SHA384加密
            byte[] encryptSHA384 = new byte[0];
            encryptSHA384 = EncryptUtils.encryptSHA384(SECRET.getBytes("utf-8"));
            //前32位位key
            byte[] key = Arrays.copyOfRange(encryptSHA384, 0, 32);
            //后16位为偏移量
            byte[] iv = Arrays.copyOfRange(encryptSHA384, 32, 48);
            return EncryptUtils.encryptAES2Base64(data,key,"AES/CBC/PKCS5Padding",iv);
        } catch (UnsupportedEncodingException e) {
            L.e("编码失败");
            e.printStackTrace();
        }
;       return null;
    }

    public static String decrypt(String content) {
        try {
            byte[] encryptSHA384 = EncryptUtils.encryptSHA384(SECRET.getBytes("utf-8"));
            byte[] key = Arrays.copyOfRange(encryptSHA384, 0, 32);
            byte[] iv = Arrays.copyOfRange(encryptSHA384, 32, 48);
            return byte2String(EncryptUtils.decryptBase64AES(
                    content.getBytes("utf-8"), key, "AES/CBC/PKCS5Padding",
                    iv
            ));
        } catch (Exception e) {
            L.e("解码失败");
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] string2Byte(String str){
        try {
            return str.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String byte2String(byte[] data){
        return new String(data, Charset.forName("UTF-8"));
    }
}
