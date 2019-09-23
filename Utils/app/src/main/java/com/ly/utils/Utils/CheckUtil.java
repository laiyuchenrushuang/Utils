package com.ly.utils.Utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 检查是否频繁点击按钮
 */
public class CheckUtil {

    private static long lastClickTime;
    static String tag = CheckUtil.class.getName();


    public static boolean isFastClick() {
        boolean result = false;
        long currentClickTime = System.currentTimeMillis();
        long timeD = currentClickTime - lastClickTime;
        if (timeD > 0 && timeD < 500) {
            result = true;
        }
        lastClickTime = currentClickTime;
        return result;
    }

    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;

        }
        if (isEnoughMem()) {//memory is enough
            return true;

        }
        return false;
    }

    /**
     * 判断手机内存是否大于300k
     */
    public static boolean isEnoughMem() {
        File path = Environment.getDataDirectory();  // Get the path /data, this is internal storage path.
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long memSize = availableBlocks * blockSize;  // free size, unit is byte.

        if (memSize < 300) { //If phone available memory is less than 10M , kill activity,it will avoid force when phone low memory.
            return false;
        }
        return true;
    }

    public static long firstTime = 0;


}
