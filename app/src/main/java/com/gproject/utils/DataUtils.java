package com.gproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DataUtils {
    private static Gson gson = new GsonBuilder().create();
    private SharedPreferences sharedPreferences;

    public static Gson GetGson() {
        return gson;
    }

    public static String Obj2JsonStr(Object obj) {
        if (obj == null) return null;
        return GetGson().toJson(obj);
    }

    public static <T> T JsonStr2Obj(String jsonStr, Class objClass) {
        if (jsonStr == null) return null;
        return (T) GetGson().fromJson(jsonStr, objClass);
    }

    public static Object JsonStr2Obj(String jsonStr, Type type) {
        if (jsonStr == null) return null;
        try {
            Object o = GetGson().fromJson(jsonStr, type);
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    public static void SetPreference(Context context, String prefBase, String prefName, String data) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (data == null) {
            editor.remove(prefName);
            editor.commit();
        } else {
            editor.putString(prefName, data);
            editor.commit();
        }
    }

    public static String GetPreference(Context context, String prefBase, String prefName) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        return pref.getString(prefName, null);
    }


    public static void SetPreferenceList(Context context, String prefBase, String prefName, List<?> data) {
        String jsonStr = null;
        if (data != null) {
            jsonStr = Obj2JsonStr(data);
        }
        SetPreference(context, prefBase, prefName, jsonStr);
    }

    public static Object GetPreferenceList(Context context, String prefBase, String prefName, Type token) {
        String jsonStr = GetPreference(context, prefBase, prefName);
        if (jsonStr != null) {
            return JsonStr2Obj(jsonStr, token);
        }
        return null;
    }

    public static void SetPreferenceObject(Context context, String prefBase, String prefName, Object data) {
        String jsonStr = null;
        if (data != null) {
            jsonStr = Obj2JsonStr(data);
        }
        SetPreference(context, prefBase, prefName, jsonStr);
    }

    // class 不能是ArrayList<T>形式
    public static Object GetPreferenceObject(Context context, String prefBase, String prefName, Class cls) {
        String jsonStr = GetPreference(context, prefBase, prefName);
        if (jsonStr != null) {
            return JsonStr2Obj(jsonStr, cls);
        }
        return null;
    }

    public static void SetPreference(Context context, String prefBase, String prefName, int data) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(prefName, data);
        editor.commit();
    }

    public static int GetPreference(Context context, String prefBase, String prefName, int def) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        return pref.getInt(prefName, def);
    }


    public static void SetPreference(Context context, String prefBase, String prefName, long data) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(prefName, data);
        editor.commit();
    }

    public static long GetPreference(Context context, String prefBase, String prefName, long def) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        return pref.getLong(prefName, def);
    }


    public static void SetPreference(Context context, String prefBase, String prefName, boolean data) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(prefName, data);
        editor.commit();
    }


    public static boolean GetPreference(Context context, String prefBase, String prefName, boolean def) {
        SharedPreferences pref = context.getSharedPreferences(prefBase, context.MODE_PRIVATE);
        return pref.getBoolean(prefName, def);
    }


    public static long GetFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + GetFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return size;
    }


    public static boolean DeleteFolderFile(String filePath, boolean deleteThisPath) {

        try {
            File file = new File(filePath);
            if (file.isDirectory()) {// 处理目录
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    DeleteFolderFile(files[i].getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {// 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getRequestSignature(Map<String, String> paramMap, String key) {
        return md5(getSignBaseString(paramMap, key));
    }

    /**
     * 获得签名的基础字符串
     *
     * @param paramMap
     * @param key
     * @return
     */
    public static String getSignBaseString(Map<String, String> paramMap, String key) {
        StringBuffer sb = new StringBuffer();
        for (Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        sb.append(key);
        return sb.toString();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes("utf-8"));
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
