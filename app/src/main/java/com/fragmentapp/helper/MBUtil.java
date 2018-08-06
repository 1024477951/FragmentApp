package com.fragmentapp.helper;

/**
 * Created by liuzhen on 2018/3/20.
 */

public class MBUtil {

    public static String formatSize(long size) {
        return size < 1024L ? String.format("%d B", new Object[]{Integer.valueOf((int) size)}) :
                (size < 1048576L ? String.format("%.2f KB", new Object[]{Float.valueOf((float) size / 1024.0F)}) :
                        (size < 1073741824L ? String.format("%.2f MB", new Object[]{Float.valueOf((float) size / 1048576.0F)}) :
                                String.format("%.2f G", new Object[]{Float.valueOf((float) size / 1.07374182E9F)})));
    }

}
