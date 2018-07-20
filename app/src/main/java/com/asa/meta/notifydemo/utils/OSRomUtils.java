package com.asa.meta.notifydemo.utils;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class OSRomUtils {

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    public static final String SYS_OPPO = "sys_oppo";

    //小米
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    //华为
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";
    //oppp
    private static final String KEY_OPPO_VERSION = "ro.build.version.opporom";
    private static final String KEY_COLOROS_VERSION = "ro.oppo.theme.version"; // "703"
    private static final String KEY_COLOROS_THEME_VERSION = "ro.oppo.version"; // ""
    private static final String KEY_COLOROS_ROM_VERSION = "ro.rom.different.version"; // "ColorOS2.1"


    // vivo : FuntouchOS
    private static final String KEY_FUNTOUCHOS_BOARD_VERSION = "ro.vivo.board.version"; // "MD"
    private static final String KEY_FUNTOUCHOS_OS_NAME = "ro.vivo.os.name"; // "Funtouch"
    private static final String KEY_FUNTOUCHOS_OS_VERSION = "ro.vivo.os.version"; // "3.0"
    private static final String KEY_FUNTOUCHOS_DISPLAY_ID = "ro.vivo.os.build.display.id"; // "FuntouchOS_3.0"
    private static final String KEY_FUNTOUCHOS_ROM_VERSION = "ro.vivo.rom.version"; // "rom_3.1"


    // 魅族 : Flyme
    private static final String KEY_FLYME_PUBLISHED = "ro.flyme.published"; // "true"
    private static final String KEY_FLYME_SETUP = "ro.meizu.setupwizard.flyme"; // "true"
    private static final String VALUE_FLYME_DISPLAY_ID_CONTAIN = "Flyme"; // "Flyme OS 4.5.4.2U"

    // Samsung
    private static final String VALUE_SAMSUNG_BASE_OS_VERSION_CONTAIN = "samsung";

    // Sony
    private static final String KEY_SONY_PROTOCOL_TYPE = "ro.sony.irremote.protocol_type"; // "2"
    private static final String KEY_SONY_ENCRYPTED_DATA = "ro.sony.fota.encrypteddata"; // "supported"

    // 乐视 : eui
    private static final String KEY_EUI_VERSION = "ro.letv.release.version"; // "5.9.023S"
    private static final String KEY_EUI_VERSION_DATE = "ro.letv.release.version_date"; // "5.9.023S_03111"
    private static final String KEY_EUI_NAME = "ro.product.letv_name"; // "乐1s"
    private static final String KEY_EUI_MODEL = "ro.product.letv_model"; // "Letv X500"

    // 酷派 : yulong
    private static final String KEY_YULONG_VERSION_RELEASE = "ro.yulong.version.release"; // "5.1.046.P1.150921.8676_M01"
    private static final String KEY_YULONG_VERSION_TAG = "ro.yulong.version.tag"; // "LC"

    // 金立 : amigo
    private static final String KEY_AMIGO_ROM_VERSION = "ro.gn.gnromvernumber"; // "GIONEE ROM5.0.16"
    private static final String KEY_AMIGO_SYSTEM_UI_SUPPORT = "ro.gn.amigo.systemui.support"; // "yes"
    private static final String VALUE_AMIGO_DISPLAY_ID_CONTAIN = "amigo"; // "amigo3.5.1"

    // HTC : Sense
    private static final String KEY_SENSE_BUILD_STAGE = "htc.build.stage"; // "2"
    private static final String KEY_SENSE_BLUETOOTH_SAP = "ro.htc.bluetooth.sap"; // "true"

    // LG : LG
    private static final String KEY_LG_SW_VERSION = "ro.lge.swversion"; // "D85720b"
    private static final String KEY_LG_SW_VERSION_SHORT = "ro.lge.swversion_short"; // "V20b"
    private static final String KEY_LG_FACTORY_VERSION = "ro.lge.factoryversion"; // "LGD857AT-00-V20b-CUO-CN-FEB-17-2015+0"

    // 联想
    private static final String KEY_LENOVO_DEVICE = "ro.lenovo.device"; // "phone"
    private static final String KEY_LENOVO_PLATFORM = "ro.lenovo.platform"; // "qualcomm"
    private static final String KEY_LENOVO_ADB = "ro.lenovo.adb"; // "apkctl,speedup"

    private static SystemInfo systemInfoInstance;

    public static SystemInfo getSystemInfo() {
        if (systemInfoInstance == null) {
            synchronized (OSRomUtils.class) {
                if (systemInfoInstance == null) {
                    systemInfoInstance = new SystemInfo();
                    systemInfoInstance.os = getSystem();
                }
            }
        }
        return systemInfoInstance;
    }

    public static String getOSRomType() {
        return getSystemInfo().getOs();
    }

    public static String getSystem() {
        String SYS = Build.BRAND;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            if (!TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_CODE, ""))
                    || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_NAME, ""))
                    || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_INTERNAL_STORAGE, ""))) {
                SYS = SYS_MIUI;//小米
            } else if (!TextUtils.isEmpty(getSystemProperty(KEY_EMUI_API_LEVEL, ""))
                    || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_VERSION, ""))
                    || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, ""))) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            } else if (!TextUtils.isEmpty(getSystemProperty(KEY_OPPO_VERSION, ""))) {
                SYS = SYS_OPPO;
            }
            return SYS;
        } else {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                        || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                        || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                    SYS = SYS_MIUI;//小米
                } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                        || prop.getProperty(KEY_EMUI_VERSION, null) != null
                        || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                    SYS = SYS_EMUI;//华为
                } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                    SYS = SYS_FLYME;//魅族
                } else if (prop.getProperty(KEY_OPPO_VERSION, null) != null) {
                    SYS = SYS_OPPO;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return SYS;
            } finally {
                return SYS;
            }
        }
    }

    private static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }


    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static boolean isXiaoMi() {
        return getOSRomType().equals(SYS_MIUI);
    }

    public static boolean isHuaWei() {
        return getOSRomType().equals(SYS_EMUI);
    }

    public static boolean isOppo() {
        return getOSRomType().equals(SYS_OPPO);
    }

    public static class SystemInfo {
        private String os = "android";
        private String versionName = Build.VERSION.RELEASE;
        private int versionCode = Build.VERSION.SDK_INT;

        public String getOs() {
            return os;
        }

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        @Override
        public String toString() {
            return
                    "os='" + os + '\'' +
                    ", \nversionName='" + versionName + '\'' +
                    ", \nversionCode=" + versionCode
                   ;
        }
    }

    public static boolean isOSMore8() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isAndroid8_re1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    public static boolean isAndroid7_re1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    public static boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= 21;
    }
}