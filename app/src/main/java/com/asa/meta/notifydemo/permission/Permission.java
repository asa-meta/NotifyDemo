package com.asa.meta.notifydemo.permission;

import android.content.Intent;

public class Permission {
    public static final int PERMISSION_MODIFY_BY_ACTIVITY = 2;
    public static final int PERMISSION_MODIFY_BY_ACTIVITY_ACC = 4;
    public static final int PERMISSION_MODIFY_BY_DIRECT = 1;
    public static final int PERMISSION_MODIFY_NONE = 0;
    public Intent mIntent;
    public int mModifyState = 0;
    public int mPermissionId = 0;
    public PermissionState mState = PermissionState.UNKNOWN;
    public String mTips;
}