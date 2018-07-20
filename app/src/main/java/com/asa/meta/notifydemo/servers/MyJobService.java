package com.asa.meta.notifydemo.servers;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.asa.meta.notifydemo.utils.ServiceUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = "MyJobService";
    private static final int jobId1 = 0x1;
    public static int i;
    public Disposable disposables;
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static void startService(Context context) {
        context.startService(new Intent(context, MyJobService.class));
    }

    public static void setJobInfo(JobInfo info) {

        try {
            Class c = info.getClass();
            Field field = c.getDeclaredField("MIN_PERIOD_MILLIS");
            field.setAccessible(true);
            field.set(info, 5000L);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables = d;
            }

            @Override
            public void onNext(Long aLong) {
                Log.i(TAG, "onNext: MyJobService:" + aLong);
                if (!ServiceUtils.isMyServiceWork(MyJobService.this)) {
                    MyService.startServer(MyJobService.this, connection);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: ");
        return true;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (disposables != null) {
            disposables.dispose();
        }
        unbindService(connection);
        MyJobTwoService.startService(getBaseContext());
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        JobScheduler jobScheduler = (JobScheduler) getBaseContext().getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            jobInfo = new JobInfo.Builder(jobId1, new ComponentName(getBaseContext().getPackageName(), MyJobService.class.getName())).
                    setMinimumLatency(5000).
                    setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).
                    setPersisted(true).build();

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobInfo = new JobInfo.Builder(jobId1, new ComponentName(getBaseContext().getPackageName(), MyJobService.class.getName())).
                    setPeriodic(5000).
                    setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).
                    setPersisted(true).build();
        }

        jobScheduler.schedule(jobInfo);
        return START_STICKY;
    }
}
