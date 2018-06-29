package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.text.TextUtils;

import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class HTCRom extends Rom {
    private float version = -1.0f;

    public HTCRom(Context context) {
        super(context);
    }

    public boolean isRuning() {
        String mVer = SystemPropertiesUtil.get("ro.build.sense.version");
        if (!TextUtils.isEmpty(mVer)) {
            try {
                this.version = Float.parseFloat(mVer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.version != -1.0f) {
            return true;
        }
        return false;
    }

    public void initPermissionPolicy() {
    }

    public boolean openSystemSettings(int permissionType) {
        return false;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        return false;
    }

    public String getRomName() {
        return "HTC Sense v" + this.version;
    }

    public int getRomSdkVersion() {
        return 1;
    }
}