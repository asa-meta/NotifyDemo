package com.asa.meta.notifydemo.permission;

import android.content.Context;

public abstract class Rom {
    public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    protected Context mContext = null;
    protected PermissionPolicy mPermissionPolicy = null;

    public Rom(Context context) {
        this.mContext = context;
    }

    public abstract String getRomName();

    public abstract int getRomSdkVersion();

    public abstract void initPermissionPolicy();

    public abstract boolean modifyPermissionDirect(int permissionType, PermissionState permissionState);

    public abstract boolean openSystemSettings(int permissionType);

    public abstract boolean isRuning();

    public PermissionPolicy getPermissionPolicy(boolean isNew) {
        if (this.mPermissionPolicy != null && !isNew) {
            return this.mPermissionPolicy;
        }
        this.mPermissionPolicy = new PermissionPolicy(this.mContext);
        initPermissionPolicy();
        modifyPermissionDirect(PermissionPolicy.PERMISSION_FLOAT_VIEW, PermissionState.ALLOWED);
        return this.mPermissionPolicy;
    }


}