package com.wind.yuanbin.daily.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by yuanb on 2018/2/24.
 */

public class PackageUtils {
    /**
     * 获取应用的名称
     */
    public static String getApplicationName(String packageName,PackageManager packageManager) {
        String applicationName=null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return applicationName;
    }
    public static List<PackageInfo> getPackageInfos(PackageManager packageManager){
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        return packageInfoList;
    }
}
