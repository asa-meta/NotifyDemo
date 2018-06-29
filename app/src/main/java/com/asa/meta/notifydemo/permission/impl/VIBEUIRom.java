package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.content.Intent;

import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;
import com.asa.meta.notifydemo.permission.VIBEUIHelper;

import java.util.List;

public class VIBEUIRom extends Rom {

    private boolean isVibeUI;

    public VIBEUIRom(Context context) {
        super(context);
        this.isVibeUI = false;
        this.isVibeUI = VIBEUIHelper.isVIBEUI();
    }

    public static boolean existIntent(Context context, Intent intent) {
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 65536);
        return queryIntentActivities != null && queryIntentActivities.size() > 0;
    }

    public boolean isRuning() {
        return this.isVibeUI;
    }

    public void initPermissionPolicy() {
        if (this.isVibeUI && existIntent(this.mContext, VIBEUIHelper.getLeSafeMainActivityIntent())) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = VIBEUIHelper.getLeSafeMainActivityIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = VIBEUIHelper.getAndroidAppNotificationOpenManager();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = VIBEUIHelper.getAppNotificationOpenManagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = VIBEUIHelper.getLeSafeMainActivityIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = "";
        }
    }

    public boolean openSystemSettings(int permissionType) {

        this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent.setFlags(1418002432).addFlags(32768));
        return true;
        //return false;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        return false;
    }

    public String getRomName() {
        return VIBEUIHelper.getRomName();
    }


    public int getRomSdkVersion() {
        return 1;
    }
}