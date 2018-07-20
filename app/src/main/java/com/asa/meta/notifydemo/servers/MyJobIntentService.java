package com.asa.meta.notifydemo.servers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class MyJobIntentService extends JobIntentService {
    private static final String TAG = "MyJobIntentService";

    public static void startService(Context context) {
        MyJobIntentService.enqueueWork(context, MyJobIntentService.class, 111, new Intent());
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(TAG, "onHandleWork: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
