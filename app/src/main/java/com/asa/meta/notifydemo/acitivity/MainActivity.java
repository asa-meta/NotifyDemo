package com.asa.meta.notifydemo.acitivity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asa.meta.notifydemo.R;
import com.asa.meta.notifydemo.databinding.ActivityMainBinding;
import com.asa.meta.notifydemo.utils.NotifyHelper;
import com.asa.meta.notifydemo.utils.NotifySettingUtils;
import com.asa.meta.notifydemo.utils.OSRomUtils;
import com.asa.meta.notifydemo.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setOnClickEvent(new OnClickEvent(this));
        this.mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class OnClickEvent {
        private Context context;
        private int i;

        public OnClickEvent(Context context) {
            this.context = context;
        }

        public void onClickXiaoMi(View view) {
            if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_MIUI)) {
                ToastUtils.showToast("不是小米手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
                return;
            }

            try {
                NotifySettingUtils.openMIUINotifySetting(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onClickNotify1(View view) {
            i++;
            NotifyHelper.buildNotifyHelper(context).setNotificationId(110).setNotifyBuilder("channel1", NotifyHelper.NotifyInfo.build().setTitle("我是第" + i).setContent("巴巴变")).sent();
        }


        public void onClickNotify2(View view) {
            NotifyHelper.buildNotifyHelper(context).setNotificationId(111).setNotifyBuilder("channel2", NotifyHelper.NotifyInfo.build().setTitle("噶苏").setContent("巴巴变")).sent();
        }

        public void onClickHuaWei(View view) {
            if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_EMUI)) {
                ToastUtils.showToast("不是华为手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
                return;
            }

            try {
                NotifySettingUtils.openEMUINotifySetting(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onClickOther(View view) {
            NotifySettingUtils.openOtherNotifySetting(context);
        }

    }
}

