package com.gproject.android.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


public class GlobalSettingManager {
    private static final String METADATA_BASEURL = "BASE_URL";


    private static String BaseUrl = null;


    public static String GetBaseUrl(Context context) {
        if (BaseUrl == null) {
            ApplicationInfo appInfo = null;
            try {
                appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                BaseUrl = appInfo.metaData.getString(METADATA_BASEURL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return BaseUrl;
    }


    public static String GetApplicationId(Context context) {
        return GetPackageInfo(context).applicationInfo.packageName;
    }

    public static String GetVersionName(Context context) {
        return GetPackageInfo(context).versionName;
    }

    public static int GetVersionCode(Context context) {
        return GetPackageInfo(context).versionCode;
    }

    private static PackageInfo GetPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static boolean getPermission(Context context, String permission, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean r = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, packageName));
        if (r) {
            return true;
        } else {
            return false;
        }
    }
}
