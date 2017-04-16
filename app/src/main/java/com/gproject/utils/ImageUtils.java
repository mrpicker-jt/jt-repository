package com.gproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    private static final int WIDTH = 100;   //设置图片的宽度

    public static Bitmap GetScaledBitmap(String path, int scaleSize) {
        int scale = 1;
        double realScale = 1.0;
        Bitmap showBitmap = null;
        InputStream input = null;
        File file = new File(path);
        Options options;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        } finally {
            options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int w, h;
        if (options.outHeight > scaleSize || options.outWidth > scaleSize) {
            // calculate scale
            if (options.outHeight > options.outWidth) {
                h = scaleSize;
                realScale = (double) scaleSize / (double) options.outHeight;
                w = (int) (realScale * options.outWidth);
            } else {
                w = scaleSize;
                realScale = (double) scaleSize / (double) options.outWidth;
                h = (int) (realScale * options.outHeight);

            }
            scale = (int) Math.floor(Math.max(options.outHeight / scaleSize, options.outWidth / scaleSize));
            if (scale < 1) {
                scale = 1;
            }
            // change size
            Log.d("UploadImage", "Scale=" + scale);
            try {
                input = new FileInputStream(file);
                Options o2 = new Options();
                o2.inSampleSize = scale;
                Bitmap temp = BitmapFactory.decodeStream(input, null, o2);
                showBitmap = Bitmap.createScaledBitmap(temp, w, h, true);
                temp.recycle();
                temp = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                input = new FileInputStream(file);
                showBitmap = BitmapFactory.decodeStream(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("ImageUtils", "Success Scale Image");

        return showBitmap;
    }

    public static int GetPhotoOrientation(String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            return orientation;
        } catch (Exception e) {

        }
        return ExifInterface.ORIENTATION_NORMAL;
    }

    public static boolean ScaleBmp2Jpg(String oriPath, String savePath, int scaleSize, int rate) {
        int orientation = GetPhotoOrientation(oriPath);
        Bitmap showBitmap = GetScaledBitmap(oriPath, scaleSize);
        if (showBitmap == null) {
            return false;
        }
        Matrix matrix = null;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            matrix = new Matrix();
            matrix.postRotate(180);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            matrix = new Matrix();
            matrix.postRotate(270);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            matrix = new Matrix();
            matrix.postRotate(90);
        }
        if (matrix != null) {
            Bitmap rotatedMBitmap = Bitmap.createBitmap(showBitmap, 0, 0, showBitmap.getWidth(), showBitmap.getHeight(), matrix, true);
            showBitmap.recycle();
            showBitmap = rotatedMBitmap;
        }

        boolean result = WriteJpg(showBitmap, savePath, rate);
        showBitmap.recycle();
        showBitmap = null;
        return result;
    }

    public static boolean WriteJpg(Bitmap bitmap, String path, int rate) {
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, rate, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (b != null) {
                try {
                    b.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    public static Point GetImageFileSize(String path) {
        Point point = new Point();
        File file = new File(path);
        InputStream input = null;
        Options options;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } finally {
            options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            point.x = options.outWidth;
            point.y = options.outHeight;
        }

        return point;
    }

    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



    public static Bitmap strToBitmap(String str) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void saveBitmapToFile(Bitmap bitmap, String savePath) {
        File file = new File(savePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 200, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
