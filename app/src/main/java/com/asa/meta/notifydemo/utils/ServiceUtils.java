package com.asa.meta.notifydemo.utils;

import android.content.Context;
import android.content.Intent;

public class ServiceUtils {
    public static void startService(Context context, Intent intent) {
        if (OSRomUtils.isOSMore8()) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}