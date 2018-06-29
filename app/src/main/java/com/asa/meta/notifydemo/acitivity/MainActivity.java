package com.asa.meta.notifydemo.acitivity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asa.meta.notifydemo.R;
import com.asa.meta.notifydemo.databinding.ActivityMainBinding;
import com.asa.meta.notifydemo.servers.EmailService;
import com.asa.meta.notifydemo.utils.NotifyUtils;
import com.asa.meta.notifydemo.utils.OSRomUtils;
import com.asa.meta.notifydemo.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.mContext = this;
        EmailService.startServer(this);
    }

    public void onClickXiaoMi(View view) {
        if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_MIUI)) {
            ToastUtils.showToast("不是小米手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
            return;
        }


        try {
            NotifyUtils.openMIUINotifySetting(mContext);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClickNotify1(View view) {
        i++;
        NotifyUtils notifyUtils = new NotifyUtils(mContext, 110);
        notifyUtils.notifyNormalSingleline(null, R.mipmap.ic_launcher, getString(R.string.app_name), "第一个测试" + i, false, false, false);
    }


    public void onClickNotify2(View view) {
        NotifyUtils notifyUtils = new NotifyUtils(mContext, 111);
        notifyUtils.notifyNormalSingleline(null, R.mipmap.ic_launcher, getString(R.string.app_name), "第二个测试", false, false, false);
    }

    public void onClickHuaWei(View view) {

    }

    public void onClickOpenServer(View view) {
        EmailService.startServer(this);
    }
}
