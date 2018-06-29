package com.asa.meta.notifydemo.permission.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.asa.meta.notifydemo.permission.MeiZuHelper;
import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

public class MeiZuRom extends Rom {

    private int version;
    private Class<?> mAppOpsManager;
    private int alertWindows;

    public MeiZuRom(Context context) {
        super(context);
        this.version = -1;
        this.mAppOpsManager = null;
        this.alertWindows = -1;
        this.version = MeiZuHelper.getVersion(this.mContext);
        try {
            this.mAppOpsManager = Class.forName("android.app.AppOpsManager");
        } catch (ClassNotFoundException e) {
            this.mAppOpsManager = null;
        }
    }

    public boolean isRuning() {
        return this.version != -1;
    }

    public void initPermissionPolicy() {
        getFloatWindowPermission();
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mState = PermissionState.FORBIDDEN;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mModifyState = 0;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 0;
        if (this.version >= 33 && this.version < 40) {
            switch (MeiZuHelper.getPermissionStatus(this.mContext)) {
                case 0:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.ALLOWED;
                    break;
                case 1:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.FORBIDDEN;
                    break;
                default:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
                    break;
            }
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = getAppControlIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        } else if (this.version >= 40) {
            switch (MeiZuHelper.getPermissionAbove40(this.mContext)) {
                case 0:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.ALLOWED;
                    break;
                case 1:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.FORBIDDEN;
                    break;
                default:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
                    break;
            }
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 7;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = getSHOW_APPSECIntent();
        }
        if (this.version >= 33 && this.version < 40) {
            updatePermissionStatus(4, this.alertWindows);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = getAppControlIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mTips = "";
        } else if (this.version >= 40) {
            updatePermissionStatus(PermissionPolicy.PERMISSION_FLOAT_VIEW, this.alertWindows);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = getSHOW_APPSECIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mTips = "";
        }
        if (this.version >= 33 && this.version < 40) {
            switch (MeiZuHelper.getPermissionStatusNOTIFICATION(this.mContext)) {
                case 0:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.ALLOWED;
                    break;
                case 1:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.FORBIDDEN;
                    break;
                default:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.UNKNOWN;
                    break;
            }
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = getAppControlIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
        } else if (this.version >= 40) {
            switch (MeiZuHelper.getPermissionStatusNOTIFICATIONAbove40(this.mContext)) {
                case 0:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.ALLOWED;
                    break;
                case 1:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.FORBIDDEN;
                    break;
                default:
                    this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.UNKNOWN;
                    break;
            }
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 3;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = getSHOW_APPSECIntent();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mTips = "";
        }
        if (this.version < 33 || this.version >= 40) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = getSHOW_APPSECIntent();
            return;
        }
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = getAppControlIntent();
        this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = SystemPropertiesUtil.getPackageLabel(this.mContext);
    }

    @SuppressLint("WrongConstant")
    public boolean openSystemSettings(int permissionType) {

        this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent.setFlags(1418002432));
        return true;
    }

    private Intent getAppControlIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$AppControlSettingsActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private Intent getSHOW_APPSECIntent() {
        Intent intent = new Intent();
        intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", this.mContext.getPackageName());
        return intent;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        switch (permissionType) {
            case PermissionPolicy.PERMISSION_AUTO_START:
                return updatePERMISSION_AUTO_START(permissionState);
            case PermissionPolicy.PERMISSION_NOTIFICATION:
                return updatePERMISSION_NOTIFICATION(permissionState);
            default:
                return false;
        }
    }

    private boolean updatePERMISSION_NOTIFICATION(PermissionState permissionState) {
        if (this.version < 40) {
            return false;
        }
        if (permissionState == PermissionState.ALLOWED) {
            return MeiZuHelper.updatePERMISSION_NOTIFICATION(this.mContext, true);
        }
        if (permissionState == PermissionState.FORBIDDEN) {
            return MeiZuHelper.updatePERMISSION_NOTIFICATION(this.mContext, false);
        }
        return false;
    }

    private boolean updatePERMISSION_AUTO_START(PermissionState permissionState) {
        if ((this.version >= 33 && this.version < 40) || this.version < 40) {
            return false;
        }
        if (permissionState == PermissionState.ALLOWED) {
            return MeiZuHelper.updatePERMISSION_AUTO_START(this.mContext, true);
        }
        if (permissionState == PermissionState.FORBIDDEN) {
            return MeiZuHelper.updatePERMISSION_AUTO_START(this.mContext, false);
        }
        return false;
    }

    public String getRomName() {
        return "Flyme Rom v" + this.version;
    }

    private void updatePermissionStatus(int permssionType, int status) {
        if (status == 0) {
            this.mPermissionPolicy.getPermission(permssionType).mState = PermissionState.ALLOWED;
        } else if (status == 1) {
            this.mPermissionPolicy.getPermission(permssionType).mState = PermissionState.FORBIDDEN;
        } else {
            this.mPermissionPolicy.getPermission(permssionType).mState = PermissionState.UNKNOWN;
        }
    }

    private void getFloatWindowPermission() {
        int uid = this.mContext.getApplicationInfo().uid;
        if (this.mAppOpsManager != null) {

            try {
                this.alertWindows = ((Integer) this.mAppOpsManager.getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(this.mContext.getSystemService("appops"), new Object[]{Integer.valueOf(24), Integer.valueOf(uid), this.mContext.getPackageName()})).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public int getRomSdkVersion() {
        if ((this.version < 33 || this.version >= 40) && this.version < 40) {
            return 0;
        }
        return 1;
    }

}
