package com.asa.meta.notifydemo.permission;

import android.content.Context;

import com.asa.meta.notifydemo.permission.impl.EUIRom;
import com.asa.meta.notifydemo.permission.impl.EmotionUIRom;
import com.asa.meta.notifydemo.permission.impl.HTCRom;
import com.asa.meta.notifydemo.permission.impl.MeiZuRom;
import com.asa.meta.notifydemo.permission.impl.MiuiRom;
import com.asa.meta.notifydemo.permission.impl.OppoRom;
import com.asa.meta.notifydemo.permission.impl.SamsungRom;
import com.asa.meta.notifydemo.permission.impl.VIBEUIRom;
import com.asa.meta.notifydemo.permission.impl.VivoRom;
import com.asa.meta.notifydemo.permission.impl.YulongRom;
import com.asa.meta.notifydemo.permission.impl.ZTERom;

public class RomFactory {

    static Rom mRom = null;


    public static Rom createRom(Context context) {
        return createRom(context, false);
    }

    private static Rom createRom(Context context, boolean forceNew) {
        if (mRom == null || forceNew) {
            mRom = loadRomInternal(context);
        }
        return mRom;
    }


    private static Rom loadRomInternal(Context context) {
        AppOpsManagerHelper.init();
        Rom mRom = new MiuiRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new EmotionUIRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new MeiZuRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new ZTERom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new YulongRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new VIBEUIRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new HTCRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new OppoRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new VivoRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new SamsungRom(context);
        if (mRom.isRuning()) {
            return mRom;
        }
        mRom = new EUIRom(context);
        return mRom.isRuning() ? mRom : null;

    }
}