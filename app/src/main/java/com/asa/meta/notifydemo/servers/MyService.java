package com.asa.meta.notifydemo.servers;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.asa.meta.notifydemo.utils.NotifyHelper;
import com.asa.meta.notifydemo.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class MyService extends Service {

    private final static String LOG_TAG = "MyService";
    public static MyService ins;
    public IBinder mBinder = new MyBinder();
    private int i;
    private Disposable disposables;

    public static MyService instance() {
        return ins;
    }

    public static void startServer(Context context, ServiceConnection connection) {
//        ServiceUtils.startService(context, new Intent(context, MyService.class));
        context.bindService(new Intent(context, MyService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public final void onCreate() {
        ins = this;
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        startEvent();
//        if (OSRomUtils.isOSMore8()) {
//            startForeground(NotifyHelper.FOREGROUND_NOTIFY_ID, NotifyHelper.buildForegroundNotify(this));
//        } else {
//            startForeground();
//        }
//
    }

    @Override
    public final void onDestroy() {
//        stopForeground();
        Log.i(LOG_TAG, "onDestroy");
        if (disposables != null) {
            disposables.dispose();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MyJobService.startService(this);
        }
        super.onDestroy();
    }

    private void startEvent() {
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables = d;
            }

            @Override
            public void onNext(Long aLong) {
                Log.i(LOG_TAG, "onStartCommand:>>> " + i);
                i++;
                ToastUtils.showToast("MyService:" + i);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");

        startEvent();

        return START_STICKY;
    }

    protected void startForeground() {
        if (Build.VERSION.SDK_INT > 18) {
            startService(new Intent(this, InnerService.class));
            return;
        }
        startForeground(NotifyHelper.FOREGROUND_NOTIFY_ID, new Notification());
    }

    //android7.1已失效
    public static class InnerService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
            if (MyService.instance() != null) {
                MyService.instance().startForeground(NotifyHelper.FOREGROUND_NOTIFY_ID, NotifyHelper.buildForegroundNotify(this));
                startForeground(NotifyHelper.FOREGROUND_NOTIFY_ID, NotifyHelper.buildForegroundNotify(this));
                stopSelf();
            }
            return START_NOT_STICKY;
        }
    }

    protected void stopForeground() {
        stopForeground(true);
    }

    public class MyBinder extends Binder {
        MyBinder getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyBinder.this;
        }
    }
}