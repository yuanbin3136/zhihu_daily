package com.wind.yuanbin.daily.utils;

import android.util.Log;

/**
 * Created by yuanb on 2018/2/5.
 */

public class L {
    public static void o(Object msg){
        System.out.println(msg);
    }
    //    public void showT(String sg){
//        if (mToast == null)
//            mToast = new Toast(BaseApplication);
//    }
    private static boolean isDebug = true;

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void ee(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
//                Log.e(TAG + i, msg.substring(start, end));
                Log.e(TAG, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag, msg);
        }
    }
    public static void i(Object object,String msg){
        if(isDebug){
            Log.i(object.getClass().getSimpleName(), msg);
        }
    }

    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag, msg);
        }
    }
    public static void e(Object object,String msg){
        if(isDebug){
            Log.e(object.getClass().getSimpleName(), msg);
        }
    }
}
