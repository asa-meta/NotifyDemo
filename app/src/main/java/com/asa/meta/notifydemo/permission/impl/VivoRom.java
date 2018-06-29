package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;

import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;
import com.asa.meta.notifydemo.permission.VivoHelper;

public class VivoRom extends Rom {


    public VivoRom(Context context) {
        super(context);
    }

    public boolean isRuning() {
        return VivoHelper.isFunTouch();
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
        return VivoHelper.getRomName();
    }


    public int getRomSdkVersion() {
        return 1;
    }

}