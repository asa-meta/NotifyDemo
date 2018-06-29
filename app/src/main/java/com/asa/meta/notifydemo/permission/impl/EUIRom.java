package com.asa.meta.notifydemo.permission.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class EUIRom extends Rom {
    private int version = -1;
    private String romName = "EUI ";

    public EUIRom(Context context) {
        super(context);
        if ("5.0".equalsIgnoreCase(SystemPropertiesUtil.get("ro.letv.eui"))) {
            this.version = 50;
            this.romName += SystemPropertiesUtil.get("ro.letv.release.version");
        }
    }

    public boolean isRuning() {
        if (this.version == 50) {
            return true;
        }
        return false;
    }

    public void initPermissionPolicy() {
        if (this.version == 50) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            Intent intent = new Intent();
            intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity");
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = intent;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        }
        if (this.version == 50) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
            Intent intent = new Intent();
            intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AppActivity");
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = intent;
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
        return this.romName;
    }

    public int getRomSdkVersion() {
        return 1;
    }
}