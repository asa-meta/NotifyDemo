package com.asa.meta.notifydemo.permission;

import android.annotation.SuppressLint;

import com.asa.meta.notifydemo.permission.impl.SystemPropertiesUtil;

public class VivoHelper {
    @SuppressLint({"DefaultLocale"})
    public static boolean isFunTouch() {
        return getOS().toLowerCase().contains("funtouch");
    }

    public static String getOS() {
        return SystemPropertiesUtil.get("ro.vivo.os.name");
    }

    public static String getRomVersion() {
        return SystemPropertiesUtil.get("ro.vivo.os.version");
    }

    public static String getRomName() {
        return getOS() + " " + getRomVersion();
    }
}
