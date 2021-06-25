package com.laoniu.ezandroid;

import android.app.Application;

import com.laoniu.ezandroid.utils.WKHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class App extends MyApplication {

    public static Application mInstance;
    private static RefWatcher refWatcher;
    public static WKHandler mHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
    }

    public static Application getInstance() {
        return mInstance;
    }

    public static WKHandler getHandler() {
        return mHandler;
    }

}
