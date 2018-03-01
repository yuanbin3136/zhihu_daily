package com.wind.yuanbin.daily.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;

import com.wind.yuanbin.daily.BuildConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.wind.yuanbin.daily.utils.PackageUtils.getPackageInfos;

/**
 * Created by yuanb on 2018/2/24.
 */

public class ShareUtils {

    /**
     * 发送文件 可以选择目标包名
     * @param context
     * @param path
     */
    public static void sendFileByOtherApp(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            String type = getMimeType(path);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType(getMimeType(path));
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                ArrayList<Intent> targetIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    ActivityInfo activityInfo = info.activityInfo;
                    if (activityInfo.packageName.contains("com.tencent.mobileqq")
                            ||activityInfo.packageName.contains("com.tencent.mm")) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setPackage(activityInfo.packageName);
                        intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                        intent.setClassName(activityInfo.packageName, activityInfo.name);
                        targetIntents.add(intent);
                    }


                }
                Intent chooser = Intent.createChooser(targetIntents.remove(0), "Send mail...");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooser);

            }
        }

    }
    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param filePath
     */
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }
    public static void shareText(Activity activity, String s){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);
        shareIntent.setType("text/plain");
        activity.startActivity(shareIntent);
    }

    public static void shareThisAPP(Context context){
        List<PackageInfo> packageInfoList = getPackageInfos(context.getPackageManager());
        String packageName = context.getPackageName();
        PackageInfo packageInfo = packageInfoList.stream()
                .filter(info -> info.packageName.equals(packageName)).findFirst().get();
//file:///data/app/com.wind.yuanbin.daily-2/base.apk

//        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
        File apkFile = new File(packageInfo.applicationInfo.sourceDir);
        Uri uri = FileProvider7.getUriForFile24(context,apkFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("*/*");
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile));
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(intent);
    }
}
