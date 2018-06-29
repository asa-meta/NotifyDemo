package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

import java.util.Locale;

public class ZTERom extends Rom {


    private PackageManager packageManager = null;

    public ZTERom(Context context) {
        super(context);
        this.packageManager = context.getPackageManager();
    }

    public boolean isRuning() {

        if ((Build.MANUFACTURER.toLowerCase(Locale.US).contains("nubia") || Build.FINGERPRINT.toLowerCase(Locale.US).contains("nubia") || Build.MANUFACTURER.toLowerCase(Locale.US).contains("zte") || Build.FINGERPRINT.toLowerCase(Locale.US).contains("zte")) && SystemPropertiesUtil.get(this.mContext, "com.zte.heartyservice")) {
            return true;
        }
        return false;
    }

    public void initPermissionPolicy() {
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
        Intent intent = new Intent();
        intent.setAction("com.zte.heartyservice.intent.action.startActivity.PERMISSION_SCANNER");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = intent;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = SystemPropertiesUtil.getPackageLabel(this.mContext);
    }

    public boolean openSystemSettings(int permissionType) {
        try {
            this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        return false;
    }

    public String getRomName() {
        return "ZTE Rom";
    }

    public int getRomSdkVersion() {
        return 1;
    }
}
