package com.asa.meta.notifydemo.servers;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.asa.meta.notifydemo.utils.NotifyUtils;
import com.asa.meta.notifydemo.utils.OSRomUtils;
import com.asa.meta.notifydemo.utils.ServiceUtils;

public final class EmailService extends Service {

    private final static String LOG_TAG = "EmailService";
    private static EmailService ins;

    public static EmailService instance() {
        return ins;
    }

    public static void startServer(Context context) {
        ServiceUtils.startService(context, new Intent(context, EmailService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public final void onCreate() {
        ins = this;
        super.onCreate();

        if (OSRomUtils.isOSMore8()) {
            startForeground(NotifyUtils.FOREGROUND_NOTIFY_ID, NotifyUtils.buildForegroundNotify(this).getNotification());
        } else {
            startForeground();
        }
    }

    @Override
    public final void onDestroy() {
        stopForeground();
        super.onDestroy();
    }


    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }


    protected void startForeground() {
        if (Build.VERSION.SDK_INT > 18) {
            startService(new Intent(this, InnerService.class));
            return;
        }
        startForeground(NotifyUtils.FOREGROUND_NOTIFY_ID, new Notification());
    }

    protected void stopForeground() {
        stopForeground(true);
    }

    //android7.1已失效
    public static class InnerService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
            if (EmailService.instance() != null) {
                EmailService.instance().startForeground(NotifyUtils.FOREGROUND_NOTIFY_ID, NotifyUtils.buildForegroundNotify(this).getNotification());
                startForeground(NotifyUtils.FOREGROUND_NOTIFY_ID, NotifyUtils.buildForegroundNotify(this).getNotification());
                stopSelf();
            }
            return START_NOT_STICKY;
        }
    }
}