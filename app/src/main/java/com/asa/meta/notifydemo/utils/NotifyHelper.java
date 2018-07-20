package com.asa.meta.notifydemo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.asa.meta.notifydemo.R;

@SuppressLint("NewApi")
public class NotifyHelper {
    public int notificationId;
    public NotificationManager notificationManager;
    private Notification notification;
    public NotificationCompat.Builder cBuilder;
    public Context mContext;
    public static final String channelId = "sigma邮件提醒";
    public static final String LOG_TAG = "NotifyUtils";
    public NotificationChannel channel;

    public static final String channelForegroundName = "sigma service";
    public static final String channelForegroundId = "sigma service";
    public static final String notifyContent = "Sigma Email Service";


    public static final int FOREGROUND_NOTIFY_ID = 0x2; //服务运行后台Id 兼容startForeground

    public NotifyHelper(Context mContext, int notificationId) {
        initData(mContext, notificationId, false);
    }

    public NotifyHelper(Context mContext, int notificationId, boolean isForeground) {
        initData(mContext, notificationId, isForeground);
    }

    public static void initChannel(Context context) {
        if (OSRomUtils.isOSMore8()) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(buildChannel(channelForegroundId, channelForegroundName));
            notificationManager.createNotificationChannel(buildChannel(channelId, channelId));
        }
    }

    //  是否有通知的权限 （未完成适配）
    public static boolean hasNotifyPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else {
            return false;
        }

    }

    //创建默认前台Foreground提醒
    //创建默认Foreground提醒 需要取消“正在运行通知”
    public static Notification buildForegroundNotify(Context context) {
        NotifyHelper notifyUtils = new NotifyHelper(context, NotifyHelper.FOREGROUND_NOTIFY_ID, true);
        boolean isAndroid8 = OSRomUtils.isOSMore8();
        boolean isAndroid8_re1 = OSRomUtils.isAndroid8_re1();
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);

        if (isAndroid8) {
            if (OSRomUtils.isOppo()) {
                notifyUtils.notifyForeground(mPendingIntent, NotifyInfo.build().setSmallIcon(0).setContent(notifyContent));
                return notifyUtils.getNotification();
            } else if (isAndroid8_re1) {
                notifyUtils.notifyForeground(mPendingIntent, NotifyInfo.build().setContent(notifyContent));
            } else if (OSRomUtils.isXiaoMi()) {
                return new Notification();
            } else {
                notifyUtils.notifyForeground(mPendingIntent, NotifyInfo.build().setContent(notifyContent));
            }
        } else {
            return new Notification();
        }

        return notifyUtils.getNotification();
    }

    public static NotificationChannel buildChannel(String channelId, String channelName) {
        NotificationChannel channel = null;
        if (OSRomUtils.isAndroid8()) {
            channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED);//小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(true);
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
        }
        return channel;
    }

    //华为打开通知设置 com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationSettingsActivity:
    //Displayed com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationAllChannelSettingsActivity
    public static void openEMUINotifySetting(Context mContext) throws ClassNotFoundException, ActivityNotFoundException {
        String cls = OSRomUtils.isOSMore8() ? "com.huawei.notificationmanager.ui.NotificationAllChannelSettingsActivity" : "com.huawei.notificationmanager.ui.NotificationSettingsActivity";
        ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationSettingsActivity");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        intent.setAction("huawei.intent.action.NOTIFICATIONSETTING");
        intent.putExtra("appName", mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()));
        intent.putExtra("packageName", mContext.getPackageName());

        mContext.startActivity(intent);
    }

    private void initData(Context mContext, int NOTIFICATION_ID, boolean isForeground) {
        String id = "";
        String name = "";

        if (isForeground) {
            id = channelForegroundId;
            name = channelForegroundName;
        } else {
            id = channelId;
            name = channelId;
        }

        this.mContext = mContext;
        notificationManager = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
        if (OSRomUtils.isOSMore8()) {
            notificationManager.createNotificationChannel(buildChannel(id, name));
        }
        cBuilder = new NotificationCompat.Builder(mContext, id);
    }

    public NotifyHelper setCompatBuilder(PendingIntent pendingIntent, NotifyInfo notifyInfo) {
        if (pendingIntent != null) {
            cBuilder.setContentIntent(pendingIntent);
        }

        cBuilder.setSmallIcon(notifyInfo.getSmallIcon());
        cBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        cBuilder.setTicker(notifyInfo.getContent());

        cBuilder.setContentTitle(notifyInfo.getTitle());
        cBuilder.setContentText(notifyInfo.getContent());
        cBuilder.setWhen(System.currentTimeMillis());

        cBuilder.setAutoCancel(true);
        int defaults = 0;

        if (notifyInfo.isSound()) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (notifyInfo.isVibrate()) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (notifyInfo.isLights()) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        cBuilder.setDefaults(defaults);
        return this;
    }

    public void notifyProgress(PendingIntent pendingIntent, NotifyInfo notifyInfo) {
        setCompatBuilder(pendingIntent, notifyInfo);
    }

    public void notifyNormalSingleline(PendingIntent pendingIntent, NotifyInfo notifyInfo) {
        setCompatBuilder(pendingIntent, notifyInfo);
        sent();
    }


    public Notification getNotification() {
        notification = cBuilder.build();
        return notification;
    }

    public void notifyForeground(PendingIntent pendingIntent, NotifyInfo notifyInfo) {
        setCompatBuilder(pendingIntent, notifyInfo);
        cBuilder.setPriority(Notification.PRIORITY_MIN);
    }


    /**
     * 根据id清除通知
     */

    public static void clear(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public void clear() {
        notificationManager.cancel(notificationId);
    }

    /**
     * 发送通知
     */
    public void sent() {
        notification = cBuilder.build();

        notificationManager.notify(notificationId, notification);
    }

    //打开通知设置
    public static boolean openNotifySetting(Context mContext) {
        try {
            if (OSRomUtils.getSystemInfo().getOs().equals(OSRomUtils.SYS_MIUI)) {
                openMIUINotifySetting(mContext);
                return true;
            } else {
                openOtherNotifySetting(mContext);
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //小米打开通知设置
    public static void openMIUINotifySetting(Context mContext) throws ClassNotFoundException, ActivityNotFoundException {
        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationFilterActivity");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("appName", mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()));
        intent.putExtra("packageName", mContext.getPackageName());
        mContext.startActivity(intent);
    }

    //清除所有通知
    public void clearAll() {
        notificationManager.cancelAll();
    }

    //打开其他通知设置
    public static void openOtherNotifySetting(Context mContext) {
        Intent intent = new Intent();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", mContext.getPackageName());
            intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        }
        mContext.startActivity(intent);
    }


    //小米的神隐模式
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setComponent(new ComponentName("com.miui.powerkeeper","com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
//        startActivityForResult(intent,110);

    public static class NotifyInfo {
        private int smallIcon = R.mipmap.ic_launcher;
        private String title = "NotifyDemo";
        private String content = "NotifyDemoContent";
        private boolean isSound = true;
        private boolean isVibrate = true;
        private boolean isLights = true;

        public static NotifyInfo build() {
            return new NotifyInfo();
        }

        public int getSmallIcon() {
            return smallIcon;
        }

        public NotifyInfo setSmallIcon(int smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public NotifyInfo setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public NotifyInfo setContent(String content) {
            this.content = content;
            return this;
        }

        public boolean isSound() {
            return isSound;
        }

        public NotifyInfo setSound(boolean sound) {
            isSound = sound;
            return this;
        }

        public boolean isVibrate() {
            return isVibrate;
        }

        public NotifyInfo setVibrate(boolean vibrate) {
            isVibrate = vibrate;
            return this;
        }

        public boolean isLights() {
            return isLights;
        }

        public NotifyInfo setLights(boolean lights) {
            isLights = lights;
            return this;
        }

    }
}
