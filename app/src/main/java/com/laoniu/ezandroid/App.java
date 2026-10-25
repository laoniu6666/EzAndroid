package com.laoniu.ezandroid;

import android.app.Application;


public class App extends MyApplication {

    public static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
    }



    public static Application getInstance() {
        return mInstance;
    }

}
