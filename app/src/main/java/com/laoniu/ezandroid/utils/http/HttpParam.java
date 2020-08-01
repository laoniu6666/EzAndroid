package com.laoniu.ezandroid.utils.http;

import android.util.ArrayMap;

import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpParam {

    Map<String, Object> params;

    public static Map<String,Object> getSimpleMap(String key, Object value){
        Map map = new ArrayMap<String,Object>();
        map.put(key,value);
        return map;
    }

    /**
     * common param
     */
    public static Map<String,Object> getCommonRequestMap(){
        Map params = new ArrayMap<String,Object>();
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("uid",SPUtils.getInstance().getString("uid"));
        return params;
    }

}
