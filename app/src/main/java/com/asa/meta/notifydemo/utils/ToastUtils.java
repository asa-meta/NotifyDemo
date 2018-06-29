package com.asa.meta.notifydemo.utils;

import android.app.Activity;
import android.app.Application;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.meta.notifydemo.R;

public class ToastUtils {

    private static Toast toast;
    private static Application context;

    public static void init(Application application){
        context = application;
    }



    public static void showToast(int resId) {
        showToast(context.getString(resId));
    }

    public static synchronized void showToast(String message) {
        try {
            View layout = LayoutInflater.from(context).inflate(R.layout.ui_custom_toast, null);
            TextView textview = layout.findViewById(R.id.toast_message);
            textview.setText(message);
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } catch (Exception e) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}