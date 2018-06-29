package com.asa.meta.notifydemo.permission;

import android.content.Context;

import java.util.HashMap;

public class PermissionPolicy {
    public static final int PERMISSION_ROOT = 1;
    public static final int PERMISSION_TRUST = 2;
    public static final int PERMISSION_AUTO_START = 3;
    public static final int PERMISSION_FLOAT_VIEW = 4;
    public static final int PERMISSION_NOTIFICATION = 5;
    public static final int PERMISSION_PRIVACY = 6;
    public static final int PERMISSION_PROTECT = 7;
    private final HashMap permissions = new HashMap(PERMISSION_PRIVACY);
    private Context mContext = null;
    private String mPermissionPkg = null;

    public PermissionPolicy(Context context) {
        this.mContext = context;
        buildPermissions();
    }

    private void buildPermissions() {
        Permission permission = new Permission();
        permission.mPermissionId = PERMISSION_ROOT;
        this.permissions.put(Integer.valueOf(PERMISSION_ROOT), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_TRUST;
        this.permissions.put(Integer.valueOf(PERMISSION_TRUST), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_AUTO_START;
        this.permissions.put(Integer.valueOf(PERMISSION_AUTO_START), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_FLOAT_VIEW;
        this.permissions.put(Integer.valueOf(PERMISSION_FLOAT_VIEW), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_NOTIFICATION;
        this.permissions.put(Integer.valueOf(PERMISSION_NOTIFICATION), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_PRIVACY;
        this.permissions.put(Integer.valueOf(PERMISSION_PRIVACY), permission);
        permission = new Permission();
        permission.mPermissionId = PERMISSION_PROTECT;
        this.permissions.put(Integer.valueOf(PERMISSION_PROTECT), permission);
    }

    public HashMap getPermissions() {
        return this.permissions;
    }

    public Permission getPermission(int permissionType) {
        return (Permission) this.permissions.get(Integer.valueOf(permissionType));
    }

    public void setPermissPkg(String mPermissionPkg) {
        this.mPermissionPkg = mPermissionPkg;
    }
}