package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class OppoRom extends Rom {
    private PackageManager packageManager = null;

    public OppoRom(Context context) {
        super(context);
        this.packageManager = context.getPackageManager();
    }

    public boolean isRuning() {
        String a = SystemPropertiesUtil.get("ro.product.brand");
        if (!TextUtils.isEmpty(a) && "oppo".equals(a.toLowerCase()) && SystemPropertiesUtil.get(this.mContext, "com.oppo.safe")) {
            return true;
        }
        return false;
    }

    public void initPermissionPolicy() {
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
        Intent intent = new Intent();
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
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
        return "OPPO Color Rom";
    }

    public int getRomSdkVersion() {
        return 1;
    }
}