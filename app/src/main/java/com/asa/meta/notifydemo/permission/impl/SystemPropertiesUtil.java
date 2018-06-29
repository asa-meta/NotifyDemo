package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;

public class SystemPropertiesUtil {

    public static int getUid(Context context) {
        return Process.myUid();
    }

    public static String getPackageLabel(Context context) {
        try {
            return (String) context.getApplicationInfo().loadLabel(context.getPackageManager());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String get(String str) {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Object invoke = cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{str});
            if (invoke == null || !(invoke instanceof String)) {
                return null;
            }
            return (String) invoke;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean get(Context context, String pkg) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(pkg, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return true;
        }
        return false;
    }


}