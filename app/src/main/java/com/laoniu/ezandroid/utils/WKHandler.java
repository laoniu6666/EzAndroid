package com.laoniu.ezandroid.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WKHandler extends Handler {
    private WeakReference<Callback> mWeakReference;

    public WKHandler(Callback callback) {
        mWeakReference = new WeakReference<Callback>(callback);
    }

    public WKHandler(Callback callback, Looper looper) {
        super(looper);
        mWeakReference = new WeakReference<Callback>(callback);
    }
    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            Callback callback = mWeakReference.get();
            callback.handleMessage(msg);
        }
    }
}