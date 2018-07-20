package com.asa.meta.notifydemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class ServiceUtils {
    public static void startService(Context context, Intent intent) {
        if (OSRomUtils.isOSMore8()) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public static final String MyService = "com.asa.meta.notifydemo.servers.MyService";


    public static boolean isMyServiceWork(Context mContext) {
        return isServiceWork(mContext, MyService);
    }

    // 判断服务是否正在运行
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}