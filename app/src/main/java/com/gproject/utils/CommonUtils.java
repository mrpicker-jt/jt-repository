package com.gproject.utils;

import org.apache.commons.lang.time.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtils {

    public static String GetDateString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(timestamp));
    }

    public static String GetChineseDateString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("M月d日 HH:mm");
        return format.format(new Date(timestamp));
    }

    public static String GetDateAndTimeString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(timestamp));
    }

    public static String GetRefreshTimeString(long timestamp){
        SimpleDateFormat format=new SimpleDateFormat("今天HH:mm");
        return format.format(new Date(timestamp));
    }

    public static String getDateAndTimeSlashString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format.format(new Date(timestamp));
    }

    public static String getDateAndTimeDotString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return format.format(new Date(timestamp));
    }

    public static String GetIMDateString(long timestamp) {
        SimpleDateFormat format;
        if (DateUtils.isSameDay(new Date(timestamp), new Date(System.currentTimeMillis()))) {
            format = new SimpleDateFormat("HH:mm");
        } else {
            format = new SimpleDateFormat("M月d日");
        }
        return format.format(new Date(timestamp));
    }

    public static String GetRemainderTimeStr(long timestamp) {
        if (timestamp < 0) {
            timestamp = 0;
        }
        int hour = 0;
        int minute = 0;
        int second = 0;
        timestamp /= 1000;
        second = (int) (timestamp % 60);
        timestamp /= 60;
        minute = (int) (timestamp % 60);
        timestamp /= 60;
        hour = (int) timestamp;
        if (hour >= 100) {
            hour = 99;
            minute = 99;
            second = 99;
        }
        return String.format("%02d:%02d:%02d", hour, minute, second);

    }


    public static String GetFileSizeStr(long size) {
        if (size > 1000 * 1000 * 1000) {
            return String.format("%.2f GB", size / (double) (1000 * 1000 * 1000));
        } else if (size > 1000 * 1000) {
            return String.format("%.2f MB", size / (double) (1000 * 1000));
        } else if (size > 1000) {
            return String.format("%.2f KB", size / (double) (1000));
        } else {
            return String.format("%d B", size);
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteSum = 0;
            int byteRead = 0;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[15360]; // 文件大小不超过15m
                int length;
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead; //字节数 文件大小
                    System.out.println(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    public static boolean isNull(String str) {
        return str == null || str.isEmpty();
    }
}
