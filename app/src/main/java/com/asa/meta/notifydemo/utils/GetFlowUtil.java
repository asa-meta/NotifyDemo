package com.asa.meta.notifydemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.text.TextUtils;

import java.util.List;

public class GetFlowUtil {

    private static Context context;

    public static void init(Context c) {
        context = c;
    }

    public static FlowInfo getAppFlowInfo() {
        //获取到配置权限信息的应用程序
        PackageManager pms = context.getPackageManager();
        String pakageName = context.getPackageName();
        List<PackageInfo> packinfos = pms
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
        //存放具有Internet权限信息的应用
        FlowInfo flowInfo = new FlowInfo();
        for (PackageInfo packinfo : packinfos) {
            String appName = packinfo.packageName;
            if (!TextUtils.isEmpty(appName)) {
                if (appName.equals(pakageName)) {
                    //用于封装具有Internet权限的应用程序信息
                    //封装应用信息
                    flowInfo.setPackageName(packinfo.packageName);
                    flowInfo.setIcon(packinfo.applicationInfo.loadIcon(pms));
                    flowInfo.setAppname(packinfo.applicationInfo.loadLabel(pms).toString());
                    //获取到应用的uid（user id）
                    int uid = packinfo.applicationInfo.uid;
                    //TrafficStats对象通过应用的uid来获取应用的下载、上传流量信息
                    //发送的 上传的流量byte
                    flowInfo.setUpKb(TrafficStats.getUidRxBytes(uid));
                    //下载的流量 byte
                    flowInfo.setDownKb(TrafficStats.getUidTxBytes(uid));
                    break;
                }
            }
        }
        return flowInfo;
    }

    public static class FlowInfo {
        String packageName;
        Drawable icon;
        String appname;
        long upKb;
        long downKb;

        public static String merge(FlowInfo older, FlowInfo newer) {
            return "消耗量：" + ((newer.upKb + newer.downKb) - (older.upKb + older.downKb)) / 1024 + "kb";
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public long getUpKb() {
            return upKb;
        }

        public void setUpKb(long upKb) {
            this.upKb = upKb;
        }

        public long getDownKb() {
            return downKb;
        }

        public void setDownKb(long downKb) {
            this.downKb = downKb;
        }

        @Override
        public String toString() {
            return "FlowInfo{" +
                    "上传=" + upKb / 1024 + "kb" +
                    ", 下载=" + downKb / 1024 + "kb" +
                    ", 全部=" + (downKb + upKb) / 1024 + "kb" +
                    '}';
        }
    }
}