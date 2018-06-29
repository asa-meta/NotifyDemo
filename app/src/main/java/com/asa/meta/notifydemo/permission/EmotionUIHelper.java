package com.asa.meta.notifydemo.permission;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.asa.meta.notifydemo.permission.impl.SystemPropertiesUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EmotionUIHelper {


    public static boolean isEmotionUI_31() {
        return "EmotionUI_3.1".equalsIgnoreCase(getRomName());
    }

    public static boolean isEmotionUI_3() {
        return "EmotionUI_3.0".equalsIgnoreCase(getRomName());
    }

    @SuppressLint({"DefaultLocale"})
    public static boolean isEmotionUI_23() {

        return "EmotionUI_2.3".equalsIgnoreCase(getRomName()) || Build.DISPLAY.toLowerCase().contains("emui2.3");
    }

    @SuppressLint({"DefaultLocale"})
    public static boolean isEUI_23() {
        return "EMUI 2.3".equalsIgnoreCase(getRomName());
    }

    public static String getRomName() {
        try {
            return SystemPropertiesUtil.get("ro.build.version.emui");
        } catch (Exception e) {
            return "";
        }
    }

    public static Intent getHSM_PROTECTED_APPSIntent() {
        Intent intent = new Intent("huawei.intent.action.HSM_PROTECTED_APPS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getPermissionmanagerIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getBootStartIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getNotificationManageIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getAddViewMonitorIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static boolean updatePOST_NOTIFICATION(Context context, boolean z) {
        return updateAppOpsStatus(context, 11, z);
    }

    public static boolean updateSYSTEM_ALERT_WINDOW(Context context, boolean z) {
        return updateAppOpsStatus(context, 24, z);
    }

    public static boolean isSYSTEM_ALERT_WINDOW(Context context) {
        return getAppOpsStatus(context, 24) == 0;
    }

    public static boolean isPOST_NOTIFICATION(Context context) {
        return getAppOpsStatus(context, 11) == 0;
    }

    public static boolean updateAppOpsStatus(Context context, int permissionType, boolean status) {
        boolean tmp = true;
        if (!isEmotionUI_23() && !isEmotionUI_3() && !isEUI_23()) {
            return false;
        }
        @SuppressLint("WrongConstant") Object systemService = context.getSystemService("appops");
        if (systemService == null) {
            return false;
        }
        try {
            int i2;
            Method method = systemService.getClass().getMethod("setMode", new Class[]{Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE});
            if (status) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            method.invoke(systemService, new Object[]{Integer.valueOf(permissionType), Integer.valueOf(android.os.Process.myUid()), context.getPackageName(), Integer.valueOf(i2)});
        } catch (NoSuchMethodException e) {
            tmp = false;
        } catch (IllegalAccessException e2) {
            tmp = false;
        } catch (IllegalArgumentException e3) {
            tmp = false;
        } catch (InvocationTargetException e4) {
            tmp = false;
        } catch (Exception e5) {
            tmp = false;
        }
        return tmp;
    }

    public static int getAppOpsStatus(Context context, int permissionType) {
        if (!isEmotionUI_23() && !isEmotionUI_3() && !isEUI_23()) {
            return -1;
        }
        @SuppressLint("WrongConstant") Object systemService = context.getSystemService("appops");
        if (systemService == null) {
            return -1;
        }
        try {
            int ret;
            systemService = systemService.getClass().getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(systemService, new Object[]{Integer.valueOf(permissionType), Integer.valueOf(android.os.Process.myUid()), context.getPackageName()});
            if (systemService instanceof Integer) {
                ret = ((Integer) systemService).intValue();
            } else {
                ret = -1;
            }
            return ret;
        } catch (NoSuchMethodException e) {
            return -1;
        } catch (IllegalAccessException e2) {
            return -1;
        } catch (IllegalArgumentException e3) {
            return -1;
        } catch (InvocationTargetException e4) {
            return -1;
        } catch (Exception e5) {
            return -1;
        }
    }

    public static boolean updateAutoStart(Context context, boolean status) {
        boolean ret = true;
        if (!isEmotionUI_23()) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://com.huawei.android.batteryspriteprovider/startupallowapps");
        Uri parse2 = Uri.parse("content://com.huawei.android.batteryspriteprovider/forbiddenapps");
        ContentValues contentValues;
        if (status) {
            try {
                contentValues = new ContentValues();
                contentValues.put("package_name", context.getPackageName());
                contentValues.put("app_type", Integer.valueOf(1));
                contentResolver.insert(parse, contentValues);
                contentResolver.delete(parse2, "package_name=?", new String[]{context.getPackageName()});
            } catch (IllegalArgumentException e) {
                ret = false;
            } catch (Exception e2) {
                ret = false;
            }
        } else {
            contentValues = new ContentValues();
            contentValues.put("package_name", context.getPackageName());
            contentResolver.delete(parse, "package_name=?", new String[]{context.getPackageName()});
            contentResolver.insert(parse2, contentValues);
        }
        return ret;
    }


    public static boolean getAutuStartStatus(Context context) {
        Cursor query;
        if (!isEmotionUI_23()) {
            return false;
        }
        query = context.getContentResolver().query(Uri.parse("content://com.huawei.android.batteryspriteprovider/startupallowapps"), null, "package_name=?", new String[]{context.getPackageName()}, null);
        try {
            if (query.getCount() > 0) {
                query.moveToFirst();
                if (query.getInt(query.getColumnIndex("app_type")) == 1) {

                    if (query != null) {
                        query.close();
                    }
                    return true;
                }
            }
        } catch (Exception e) {

            return false;
        }
        return false;

    }

}
