package com.asa.meta.notifydemo.permission.impl;

import android.content.Context;
import android.content.Intent;

import com.asa.meta.notifydemo.permission.EmotionUIHelper;
import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class EmotionUIRom extends Rom {
    private boolean isV31;
    private boolean isV30;
    private boolean isV23;
    private boolean isEMUIV23;

    public EmotionUIRom(Context context) {
        super(context);
        this.isV31 = false;
        this.isV30 = false;
        this.isV23 = false;
        this.isV31 = EmotionUIHelper.isEmotionUI_31();
        this.isV30 = EmotionUIHelper.isEmotionUI_3();
        this.isV23 = EmotionUIHelper.isEmotionUI_23();
        this.isEMUIV23 = EmotionUIHelper.isEUI_23();
    }

    public boolean isRuning() {
        return this.isV31 || this.isV30 || this.isV23 || this.isEMUIV23;
    }

    public void initPermissionPolicy() {
        if (this.isV30 || this.isV31) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = EmotionUIHelper.getBootStartIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mState = EmotionUIHelper.isSYSTEM_ALERT_WINDOW(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 7;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = EmotionUIHelper.getNotificationManageIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mTips = "";
            if (this.isV31) {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mState = PermissionState.UNKNOWN;
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = EmotionUIHelper.getAddViewMonitorIntent();
            }
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = EmotionUIHelper.isPOST_NOTIFICATION(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 3;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = EmotionUIHelper.getNotificationManageIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = "";
            if (this.isV31) {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PROTECT).mModifyState = 6;
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PROTECT).mIntent = EmotionUIHelper.getHSM_PROTECTED_APPSIntent();
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PROTECT).mTips = "";
            }
        } else if (this.isV23) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = EmotionUIHelper.getAutuStartStatus(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 1;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mState = EmotionUIHelper.isSYSTEM_ALERT_WINDOW(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 1;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = EmotionUIHelper.getAddViewMonitorIntent();
            boolean postNotification = EmotionUIHelper.isPOST_NOTIFICATION(this.mContext);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = postNotification ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 3;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = EmotionUIHelper.getNotificationManageIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = "";
        } else if (this.isEMUIV23) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = EmotionUIHelper.getAutuStartStatus(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 1;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mState = EmotionUIHelper.isSYSTEM_ALERT_WINDOW(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = EmotionUIHelper.getAddViewMonitorIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 1;
            boolean postNotification = EmotionUIHelper.isPOST_NOTIFICATION(this.mContext);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = postNotification ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 3;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = EmotionUIHelper.getNotificationManageIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = EmotionUIHelper.getPermissionmanagerIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = "";
        }
    }

    public boolean openSystemSettings(int permissionType) {
///
        //  1418002432
        this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return true;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        boolean status = false;
        Context context;
        switch (permissionType) {
            case PermissionPolicy.PERMISSION_AUTO_START:
                if (!this.isV23) {
                    return false;
                }
                context = this.mContext;
                if (permissionState != PermissionState.FORBIDDEN) {
                    status = true;
                }
                return EmotionUIHelper.updateAutoStart(context, status);
            case PermissionPolicy.PERMISSION_FLOAT_VIEW:
                context = this.mContext;
                if (permissionState != PermissionState.FORBIDDEN) {
                    status = true;
                }
                return EmotionUIHelper.updateSYSTEM_ALERT_WINDOW(context, status);
            case PermissionPolicy.PERMISSION_NOTIFICATION:
                context = this.mContext;
                if (permissionState != PermissionState.FORBIDDEN) {
                    status = true;
                }
                return EmotionUIHelper.updatePOST_NOTIFICATION(context, status);
            default:
                return false;
        }
    }

    public String getRomName() {
        return EmotionUIHelper.getRomName();
    }


    public int getRomSdkVersion() {
        if (this.isV30 || this.isV23) {
            return 1;
        }
        return 0;
    }
}