package com.asa.meta.notifydemo.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.asa.meta.notifydemo.R;

import java.util.HashMap;
import java.util.Map;

public class NotifyHelper {
    private static final String LOG_TAG = "NotifyUtils";
    private static Map<String, String> mChannelMap = new HashMap<>();
    private int notificationId;
    private Notification notification;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder mBuilder;
    private Context mContext;


    public NotifyHelper(Context mContext) {
        this.mContext = mContext;
        notificationManager = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    public static NotifyHelper buildNotifyHelper(Context context) {
        return new NotifyHelper(context);
    }

    public static void initChannel(Context context, Channel... channel) {
        if (OSRomUtils.isOSMore8() && channel != null) {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
            for (Channel chanel : channel) {
                mChannelMap.put(chanel.id, chanel.name);
                notificationManager.createNotificationChannel(buildChannel(chanel.id, chanel.name));
            }
        }
    }

    public static Channel channel(String id, String name) {
        return new Channel(id, name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel buildChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED);//小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        channel.enableVibration(true);
        return channel;
    }

    //根据id清除通知
    public static void clear(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public NotifyHelper setNotifyBuilder(String channelId, NotifyInfo notifyInfo) {
        setCompatBuilder(channelId, notifyInfo);
        return this;
    }

    public NotifyHelper setNotificationId(int notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public NotifyHelper setCompatBuilder(String channelId, NotifyInfo notifyInfo) {
        mBuilder = new NotificationCompat.Builder(mContext, channelId);
        if (notifyInfo.pendingIntent != null) {
            mBuilder.setContentIntent(notifyInfo.getPendingIntent());
        }

        mBuilder.setSmallIcon(notifyInfo.getSmallIcon());
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        mBuilder.setTicker(notifyInfo.getContent());
        mBuilder.setContentTitle(notifyInfo.getTitle());
        mBuilder.setContentText(notifyInfo.getContent());
        mBuilder.setAutoCancel(true);
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

        mBuilder.setDefaults(defaults);
        return this;
    }

    public void notifyProgress(int progress) {
        mBuilder.setProgress(100, progress, false);
        sent();
    }

    public void notifyNormal() {
        sent();
    }


    public Notification getNotification() {
        notification = mBuilder.build();
        return notification;
    }

    /**
     * 发送通知
     */
    public void sent() {
        notification = mBuilder.build();
        notificationManager.notify(notificationId, notification);
    }

    public void clear() {
        notificationManager.cancel(notificationId);
    }

    public static class Channel {
        String id;
        String name;

        public Channel(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }


    //清除所有通知
    public void clearAll() {
        notificationManager.cancelAll();
    }

    public final static class NotifyInfo {
        private int smallIcon = R.mipmap.ic_launcher;
        PendingIntent pendingIntent;
        private String title = "";
        private boolean isSound = true;
        private boolean isVibrate = true;
        private boolean isLights = true;
        private String content = "";

        public PendingIntent getPendingIntent() {
            return pendingIntent;
        }

        public NotifyInfo setPendingIntent(PendingIntent pendingIntent) {
            this.pendingIntent = pendingIntent;
            return this;
        }

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
