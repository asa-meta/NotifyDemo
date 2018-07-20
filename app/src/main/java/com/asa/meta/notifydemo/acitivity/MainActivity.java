package com.asa.meta.notifydemo.acitivity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asa.meta.notifydemo.R;
import com.asa.meta.notifydemo.databinding.ActivityMainBinding;
import com.asa.meta.notifydemo.servers.MyJobService;
import com.asa.meta.notifydemo.utils.NotifyHelper;
import com.asa.meta.notifydemo.utils.OSRomUtils;
import com.asa.meta.notifydemo.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setOnClickEvent(new OnClickEvent(this));

        this.mContext = this;
        ToastUtils.showToast(OSRomUtils.getSystemInfo().toString());

//        EmailService.startServer(this);
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
                NotifyHelper.openMIUINotifySetting(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onClickNotify1(View view) {
            i++;
            NotifyHelper notifyUtils = new NotifyHelper(mContext, 110);
            notifyUtils.notifyNormalSingleline(null, NotifyHelper.NotifyInfo.build().setContent(i + "：测试"));

        }


        public void onClickNotify2(View view) {
            NotifyHelper notifyUtils = new NotifyHelper(mContext, 111);
            notifyUtils.notifyNormalSingleline(null, NotifyHelper.NotifyInfo.build().setContent(i + "：测试"));
        }

        public void onClickHuaWei(View view) {
            if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_EMUI)) {
                ToastUtils.showToast("不是华为手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
                return;
            }

            try {
                NotifyHelper.openEMUINotifySetting(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onClickOther(View view) {
            NotifyHelper.openOtherNotifySetting(context);
        }

        public void onClickOpenServer(View view) {
            if (OSRomUtils.isAndroid5()) {
                MyJobService.startService(context);
            }

//            MyJobIntentService.startService(context);

        }

        public void onClickCloseServer(View view) {
//            MyJobService.startService(context);
//            EmailService.instance().stopSelf();
        }
    }
}
