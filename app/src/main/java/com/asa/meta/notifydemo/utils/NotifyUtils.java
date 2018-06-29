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
public class NotifyUtils {
    public int notificationId;
    public NotificationManager notificationManager;
    private Notification notification;
    public NotificationCompat.Builder cBuilder;
    public Context mContext;
    public static final String channelId = "sigma邮件提醒";
    public static final String LOG_TAG = "NotifyUtils";
    public NotificationChannel channel;

    public NotifyUtils(Context mContext, int notificationId) {
        this.mContext = mContext;
        this.notificationId = notificationId;
        notificationManager = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
        cBuilder = new NotificationCompat.Builder(mContext, channelId);
    }

    public static boolean hasNotifyPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else {
            return false;
        }

    }


    /**
     * 设置在顶部通知栏中的各种信息
     *
     * @param pendingIntent
     * @param smallIcon
     */
    public void setCompatBuilder(PendingIntent pendingIntent, int smallIcon, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        if (pendingIntent != null) {
            cBuilder.setContentIntent(pendingIntent);
        }

        cBuilder.setSmallIcon(smallIcon);
        cBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        cBuilder.setTicker(content);

        cBuilder.setContentTitle(title);
        cBuilder.setContentText(content);
        cBuilder.setWhen(System.currentTimeMillis());

        cBuilder.setAutoCancel(true);
        cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        cBuilder.setDefaults(defaults);
    }

    /**
     * 通知欄通知顯示
     */
    public void notifyProgress(PendingIntent pendingIntent, int smallIcon, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        setCompatBuilder(pendingIntent, smallIcon, title, content, sound, vibrate, lights);
    }

    /**
     * 普通的通知
     * 1. 侧滑即消失，下拉通知菜单则在通知菜单显示
     */
    public void notifyNormalSingleline(PendingIntent pendingIntent, int smallIcon, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        setCompatBuilder(pendingIntent, smallIcon, title, content, sound, vibrate, lights);
        sent();
    }

    /**
     * 发送通知
     */
    public void sent() {
        notification = cBuilder.build();
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(buildChannel("邮件提醒", "sigma"));
        }
        notificationManager.notify(notificationId, notification);
    }


    public Notification getNotification() {
        notification = cBuilder.build();
        return notification;
    }

    public NotificationChannel buildChannel(String description, String name) {
        NotificationChannel channel = new NotificationChannel(channelId, "sigma", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED);//小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        channel.enableVibration(true);
        channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        channel.setName(name);
        this.channel = channel;
        return channel;
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
     * 清除通知
     */
    public void clearAll() {
        notificationManager.cancelAll();
    }

    public interface DownLoadListener {
        void OnSuccess();

        void onFailure(Throwable t);
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

    //打开其他通知设置
    public static void openOtherNotifySetting(Context mContext) {
        Intent intent = new Intent();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
            mContext.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", mContext.getPackageName());
            intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
            mContext.startActivity(intent);
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

}
