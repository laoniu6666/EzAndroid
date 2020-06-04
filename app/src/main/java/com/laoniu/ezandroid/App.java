package com.laoniu.ezandroid;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class App extends MyApplication {

    public static Application instance;
    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static Application getInstance(){
        return instance;
    }

}
