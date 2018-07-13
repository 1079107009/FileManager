package com.lp.filemanager.utils;

import android.annotation.SuppressLint;

/**
 * @author lipin
 * @date 2018/7/13
 */
public class FileUtils {

    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            return "shouldn't be less than zero!";
        } else if (byteSize < MemoryConstants.KB) {
            return String.format("%.2fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB) {
            return String.format("%.2fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB) {
            return String.format("%.2fMB", (double) byteSize / MemoryConstants.MB);
        } else {
            return String.format("%.2fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

}
