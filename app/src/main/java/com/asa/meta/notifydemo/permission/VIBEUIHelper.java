package com.asa.meta.notifydemo.permission;

import android.content.Intent;
import android.os.Build;

import com.asa.meta.notifydemo.permission.impl.SystemPropertiesUtil;

public class VIBEUIHelper {
    public static boolean isVIBEUI() {
        return Build.FINGERPRINT.contains("VIBEUI_V2") || getRomName().contains("VIBEUI_V2");
    }

    public static String getRomName() {
        try {
            return SystemPropertiesUtil.get("ro.build.version.incremental");
        } catch (Exception e) {
            return "";
        }
    }

    public static Intent getAppNotificationOpenManagerIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.systemuiplus", "com.lenovo.systemuiplus.notifymanager.AppNotificationOpenManager");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getAndroidAppNotificationOpenManager() {
        Intent intent = new Intent();
        intent.setClassName("com.android.systemui", "com.android.systemui.lenovo.settings.AppNotificationOpenManager");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getLeSafeMainActivityIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.safecenter", "com.lenovo.safecenter.MainTab.LeSafeMainActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getBootSpeedActivityIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.safecenter", "com.lenovo.performancecenter.performance.BootSpeedActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
