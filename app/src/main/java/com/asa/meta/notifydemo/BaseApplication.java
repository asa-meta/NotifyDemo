package com.asa.meta.notifydemo;

import android.app.Application;

import com.asa.meta.notifydemo.utils.GetFlowUtil;
import com.asa.meta.notifydemo.utils.NotifyHelper;
import com.asa.meta.notifydemo.utils.ToastUtils;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        GetFlowUtil.init(this);
        NotifyHelper.initChannel(this, NotifyHelper.channel("channel1", "第一个"), NotifyHelper.channel("channel2", "第二个"));
    }
}
