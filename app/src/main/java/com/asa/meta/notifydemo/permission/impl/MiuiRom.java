package com.asa.meta.notifydemo.permission.impl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.asa.meta.notifydemo.permission.MiuiHelper;
import com.asa.meta.notifydemo.permission.PermissionPolicy;
import com.asa.meta.notifydemo.permission.PermissionState;
import com.asa.meta.notifydemo.permission.Rom;

import java.lang.reflect.Method;

public class MiuiRom extends Rom {
    private final PackageManager packageManager;
    private int version = -1;
    private Class<?> AppOpsManagerc = null;
    private int alertWindow = -1;
    private int autoStart = -1;

    public MiuiRom(Context context) {
        super(context);
        this.packageManager = context.getPackageManager();
        try {
            String vName = SystemPropertiesUtil.get("ro.miui.ui.version.name");
            if (!TextUtils.isEmpty(vName)) {
                this.version = Integer.parseInt(vName.substring(1));
            }
            if (this.version == 5) {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.addCategory("android.intent.category.DEFAULT");
                if (this.packageManager.queryIntentActivities(intent, 0).size() > 0) {
                    this.version = 51;
                } else {
                    this.version = 52;
                }
            }
            if (this.version == 6) {
                this.version = 61;
                if (MiuiHelper.getStatus(context) == -1) {
                    this.version = 62;
                }
            }
            if (this.version == 7) {
                this.version = 71;
                if (MiuiHelper.getStatus(context) == -1) {
                    this.version = 72;
                }
            }
        } catch (Exception e) {
            this.version = -1;
        }
        try {
            this.AppOpsManagerc = Class.forName("android.app.AppOpsManager");
        } catch (ClassNotFoundException e2) {
            this.AppOpsManagerc = null;
        }
    }

    public boolean isRuning() {
        if (this.version == -1) {
            return false;
        }
        if (Build.FINGERPRINT.toLowerCase().contains("xiaomi") || Build.FINGERPRINT.toLowerCase().contains("miui")) {
            return true;
        }
        return false;
    }

    public void initPermissionPolicy() {
        initOpStatus();
        if (this.version == 61) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mState = MiuiHelper.getPERMISSION_ROOTStatus(this.mContext) ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mModifyState = 3;
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.root.RootManagementActivity");
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mIntent = intent;
        } else {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mState = PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_ROOT).mModifyState = 0;
        }
        if (this.version == 61 || this.version == 62) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 0;
        } else if (this.version == 52) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = getAPPLICATION_DETAILS_SETTINGS();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
        } else if (this.version == 51) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mModifyState = 6;
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("extra_package_uid", SystemPropertiesUtil.getUid(this.mContext));
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mIntent = intent;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_TRUST).mTips = "";
        }
        if (this.version == 61 || this.version == 71) {
            updatePermissionStatus(PermissionPolicy.PERMISSION_AUTO_START, this.autoStart);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 3;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = getPermcenter();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        } else if (this.version == 62 || this.version == 72) {
            updatePermissionStatus(3, this.autoStart);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = getPermcenter();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        } else if (this.version == 52) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = getAPPLICATION_DETAILS_SETTINGS();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        } else if (this.version == 51) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mModifyState = 6;
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("extra_package_uid", SystemPropertiesUtil.getUid(this.mContext));
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mIntent = intent;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_AUTO_START).mTips = "";
        }
        if (this.version == 61 || this.version == 71) {
            updatePermissionStatus(4, this.alertWindow);
            if (this.version == 71) {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 3;
            } else {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 7;
            }
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", this.mContext.getPackageName());
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = intent;
        } else if (this.version == 62 || this.version == 72) {
            updatePermissionStatus(4, this.alertWindow);
            if (this.version == 72) {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 2;
            } else {
                this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
            }
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", this.mContext.getPackageName());
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = intent;
        } else if (this.version == 51 || this.version == 52) {
            boolean a = MiuiHelper.getFLOAT_VIEWStatus(this.mContext);
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mState = a ? PermissionState.ALLOWED : PermissionState.FORBIDDEN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mModifyState = 6;
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mIntent = intent;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_FLOAT_VIEW).mTips = "";
        }
        if (this.version == 61) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 2;
            ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationFilterActivity");
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.MAIN");
            intent.putExtra("appName", this.mContext.getApplicationInfo().loadLabel(this.packageManager));
            intent.putExtra("packageName", this.mContext.getPackageName());
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mIntent = intent;
        } else {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_NOTIFICATION).mModifyState = 0;
        }
        if (this.version == 61) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.putExtra("extra_pkgname", this.mContext.getPackageName());
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = intent;
        } else if (this.version == 52) {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = getAPPLICATION_DETAILS_SETTINGS();
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mTips = "";
        } else {
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mState = PermissionState.UNKNOWN;
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mModifyState = 2;
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("extra_package_uid", SystemPropertiesUtil.getUid(this.mContext));
            this.mPermissionPolicy.getPermission(PermissionPolicy.PERMISSION_PRIVACY).mIntent = intent;
        }
    }

    private void updatePermissionStatus(int permissionType, int status) {
        if (status == 0) {
            this.mPermissionPolicy.getPermission(permissionType).mState = PermissionState.ALLOWED;
        } else if (status == 1) {
            this.mPermissionPolicy.getPermission(permissionType).mState = PermissionState.FORBIDDEN;
        } else {
            this.mPermissionPolicy.getPermission(permissionType).mState = PermissionState.UNKNOWN;
        }
    }

    @SuppressLint("WrongConstant")
    public boolean openSystemSettings(int permissionType) {
        this.mContext.startActivity(this.mPermissionPolicy.getPermission(permissionType).mIntent.setFlags(1418002432));
        return true;
    }

    public boolean modifyPermissionDirect(int permissionType, PermissionState permissionState) {
        switch (permissionType) {
            case PermissionPolicy.PERMISSION_ROOT:
                return modifyPermissionRootDirect(permissionState);
            case PermissionPolicy.PERMISSION_AUTO_START:
                return modifyPermissionAutoStartDirect(permissionState);
            case PermissionPolicy.PERMISSION_FLOAT_VIEW:
                return modifyPermissionFloatViewDirect(permissionState);
            default:
                return false;
        }
    }

    private boolean modifyPermissionRootDirect(PermissionState permissionState) {
        if (this.version != 61) {
            return false;
        }
        if (permissionState == PermissionState.ALLOWED) {
            return MiuiHelper.modifyPermissionRoot(this.mContext, true);
        }
        if (permissionState == PermissionState.FORBIDDEN) {
            return MiuiHelper.modifyPermissionRoot(this.mContext, false);
        }
        return false;
    }

    private boolean modifyPermissionFloatViewDirect(PermissionState permissionState) {
        if (this.version != 61) {
            return false;
        }
        if (permissionState == PermissionState.ALLOWED) {
            return MiuiHelper.modifyPermissionFloatViewDirect(this.mContext, true);
        }
        if (permissionState == PermissionState.FORBIDDEN) {
            return MiuiHelper.modifyPermissionFloatViewDirect(this.mContext, false);
        }
        return false;
    }

    private boolean modifyPermissionAutoStartDirect(PermissionState permissionState) {
        if (this.version != 61) {
            return false;
        }
        if (permissionState == PermissionState.ALLOWED) {
            return MiuiHelper.modifyPermissionAutoStart(this.mContext, true);
        }
        if (permissionState == PermissionState.FORBIDDEN) {
            return MiuiHelper.modifyPermissionAutoStart(this.mContext, false);
        }
        return false;
    }

    private Intent getPermcenter() {
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.MainAcitivty");
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setComponent(componentName);
        return intent;
    }

    private Intent getAPPLICATION_DETAILS_SETTINGS() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", this.mContext.getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return intent;
    }

    public String getRomName() {
        String str = "";
        switch (this.version) {
            case 51:
                str = "V5_2";
                break;
            case 52:
                str = "V5_2S";
                break;
            case 61:
                str = "V6_KF";
                break;
            case 62:
                str = "V6_WD";
                break;
            case 71:
                str = "V7_KF";
                break;
            case 72:
                str = "V7_WD";
                break;
        }
        return "MiUi Rom " + str;
    }


    @SuppressWarnings("ResourceType")
    private void initOpStatus() {
        int uid = this.mContext.getApplicationInfo().uid;
        if (this.AppOpsManagerc != null) {
            try {
                Method method = this.AppOpsManagerc.getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
                Object systemService = this.mContext.getSystemService("appops");
                try {
                    this.alertWindow = ((Integer) method.invoke(systemService, new Object[]{Integer.valueOf(24), Integer.valueOf(uid), this.mContext.getPackageName()})).intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    this.autoStart = ((Integer) method.invoke(systemService, new Object[]{Integer.valueOf(50), Integer.valueOf(uid), this.mContext.getPackageName()})).intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.autoStart = 1;
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    public int getRomSdkVersion() {
        if (this.version == 51 || this.version == 52 || this.version == 61 || this.version == 62) {
            return 1;
        }
        return 0;
    }
}
