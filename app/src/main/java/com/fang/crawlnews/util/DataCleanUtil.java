package com.fang.crawlnews.util;

import android.content.Context;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DataCleanUtil {
    /**
            * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
            *
            * @param context
    */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
        deleteFilesByDirectory(new File(context.getCacheDir() + "/webviewCacheChromium"));
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDataBases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 根据目录删除文件 *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if(directory != null && directory.exists() && directory.isDirectory()) {
            for(File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static void deleteAllCache(Context context) {
        cleanInternalCache(context);
        cleanDataBases(context);
        cleanSharedPreference(context);
    }

    /**
     * 得到文件大小
     *
     * @param file
     * @return
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 得到缓存大小
     *
     * @param file
     * @return
     */
    public static String getCacheSize(File file) {
        String size = null;
        try {
            size = getFormatSize(getFolderSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
