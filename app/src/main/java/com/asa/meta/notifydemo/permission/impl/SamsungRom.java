package com.asa.meta.notifydemo.permission.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class SamsungRom extends Rom {

    public SamsungRom(Context context) {
        super(context);
    }

    public boolean isRuning() {
        return Build.BRAND.equalsIgnoreCase("samsung") || Build.MANUFACTURER.equalsIgnoreCase("samsung") || Build.FINGERPRINT.toLowerCase().contains("samsung");

    }

    public void initPermissionPolicy() {
        if (SystemPropertiesUtil.get(this.mContext, "com.samsung.android.sm")) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.sm", "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = intent;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        }
    }

    @SuppressLint("WrongConstant")
    public boolean openSystemSettings(int permissionType) {

        this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent.setFlags(1418002432));
        return true;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        return false;
    }

    public String getRomName() {
        return Build.MODEL;
    }


    public int getRomSdkVersion() {
        return 1;
    }
}