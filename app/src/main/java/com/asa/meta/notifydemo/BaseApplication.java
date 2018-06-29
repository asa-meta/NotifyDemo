package com.asa.meta.notifydemo;

import android.app.Application;

import com.asa.meta.notifydemo.utils.ToastUtils;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}
