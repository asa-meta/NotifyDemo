package com.asa.meta.notifydemo.permission;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Method;

public class MiuiHelper {
    private static Object object = new Object();

    public static boolean getFLOAT_VIEWStatus(Context context) {
        try {
            int flag;
            ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo;
            if (Build.VERSION.SDK_INT < 19) {
                flag = 134217728;
            } else {
                flag = 33554432;
            }
            if ((flag & applicationInfo.flags) != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean modifyPermissionAutoStart(Context context, boolean enable) {
        try {
            Class cls = Class.forName("android.miui.AppOpsUtils");
            if (cls != null) {
                Method declaredMethod = cls.getDeclaredMethod("setApplicationAutoStart", new Class[]{Context.class, String.class, Boolean.TYPE});
                if (declaredMethod != null) {
                    declaredMethod.invoke(cls, new Object[]{context, context.getPackageName(), Boolean.valueOf(enable)});
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getPERMISSION_ROOTStatus(Context context) {
        return (getStatus(context) & 512) != 0;
    }

    public static boolean modifyPermissionRoot(Context context, boolean enable) {

        synchronized (MiuiHelper.object) {
            int status = getStatus(context);
            if (enable) {
                status |= 512;
            } else {
                status &= 67108351;
            }
            return modifyPermission(context, status);
        }

    }

    public static boolean modifyPermissionFloatViewDirect(Context context, boolean enable) {

        synchronized (MiuiHelper.object) {
            int status = getStatus(context);
            if (enable) {
                status |= 33554432;
            } else {
                status &= 33554431;
            }
            return modifyPermission(context, status);
        }

    }

    private static boolean modifyPermission(Context context, int flag) {
        String packageName = context.getPackageName();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userAccept", Integer.valueOf(flag));
            context.getContentResolver().update(Uri.parse("content://com.lbe.security.miui.permmgr/active"), contentValues, "pkgName=?", new String[]{packageName});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getStatus(Context context) {
        Cursor query = null;
        String packageName = context.getPackageName();
        try {
            query = context.getContentResolver().query(Uri.parse("content://com.lbe.security.miui.permmgr/active"), null, "pkgName=?", new String[]{packageName}, null);
            if (query == null) {
                return 0;
            }
            if (query.moveToFirst()) {
                int status = query.getInt(query.getColumnIndex("userAccept"));
                if (query == null) {
                    return status;
                }
            }
            query.close();
        } catch (Exception e) {

            return -1;
        }


        return -1;
    }
}