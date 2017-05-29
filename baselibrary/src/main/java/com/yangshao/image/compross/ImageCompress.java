package com.yangshao.image.compross;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import com.yangshao.base.BaseApplication;
import com.yangshao.utils.FileUtil;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.yangshao.utils.UIUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.R.attr.path;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/4/25/19:45
 **/
public class ImageCompress  {


    /**
     * 图片质量压缩算法
     *
     * @param bitmap
     * @param maxKb     最大多少KB
     * @param cachePath 压缩路径
     * @return
     */
    public static void cQutlity(Bitmap bitmap, int maxKb, String cachePath) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int beginRate = 100;
        /*第一个参数图片格式，第二个参数压缩比值  压缩后的流 */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        while (out.size() / 1024 / 1024 > maxKb) {
            beginRate -= 10;
            out.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, beginRate, out);
        }
        ByteArrayInputStream bInt = new ByteArrayInputStream(out.toByteArray());
        Bitmap newBitmap = BitmapFactory.decodeStream(bInt);
        bitmaptoPath(newBitmap, cachePath);

    }


    /**
     * 图片的质量压缩:
     * 图片质量的压缩思想大致如下:
     * 先将一张图片到一个字节数组输出流对象保存，
     * 然后通过不断压缩数据，直到图片大小压缩到某个具体大小时，然后再把
     * 字节数组输出流对象作为一个字节数组输入流参数对象传入得到一个字节数组输入流
     * 最后再将字节数组输入流得到Bitmap对象，最终拿到图片质量压缩后的图片
     */
    public static void  getImageCompress(String imagePaht,File cachePath) {

        Bitmap bitmap=BitmapFactory.decodeFile(imagePaht);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到字节数组输出流中。
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        //ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        try {
            FileOutputStream fileOut=new FileOutputStream(cachePath);
            baos.writeTo(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * 改变图片大小的压缩算法
    * */
    public static void getCacheImage(String imagePath, String cachePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;  //只读尺寸  不加载到内存
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        option.inJustDecodeBounds = false;
        int bWidth = option.outWidth;
        int bHeight = option.outHeight;
        int be = getRatioSize(bWidth, bHeight);
        option.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath, option);
        bitmaptoPath(bitmap, cachePath);
    }



    /**
     * 先比例压缩后质量压缩
     * @param imagePath  源文件
     * @param cachefile  压缩路径文件
     * @param maxKb  最大KB
     */
    public static void doCompressImage(String imagePath, File cachefile, int maxKb) {
        Bitmap bitmap = null;
        ByteArrayOutputStream baos = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);
            options.inJustDecodeBounds = false;
            int be = getRatioSize(options.outWidth, options.outHeight);
            options.inSampleSize = be;
            /*压缩之后的*/
            bitmap = BitmapFactory.decodeFile(imagePath, options);

            //再获取压缩路径进行进一步质量压缩
            baos = new ByteArrayOutputStream();
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到字节数组输出流中。
            while (baos.toByteArray().length / 1024 > maxKb) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                quality -= 10;//每次都减少10
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }
            FileOutputStream fileOut=new FileOutputStream(cachefile);
            baos.writeTo(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
            L_.e("我出问题了");
        } finally {

        }
    }



    /**
     * 通过图片返回一个可用路径
     *
     * @param bitmap
     * @param cachePath
     */
    public static void bitmaptoPath(Bitmap bitmap, File cachePath) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(cachePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    /**
     * 通过图片返回一个可用路径
     *
     * @param bitmap
     * @param cachePath
     */
    public static void bitmaptoPath(Bitmap bitmap, String cachePath) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(cachePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    public static Bitmap pathToBitmap(String imagePath) {
        return BitmapFactory.decodeFile(imagePath);
    }


    /**
     * 计算缩放比
     *
     * @param bitWidth  图片宽度
     * @param bitHeight 图片高度
     * @return 比例
     */
    private static int getRatioSize(int bitWidth, int bitHeight) {
        int size;
        // 图片最大分辨率   采用自己手机的配置
        int wHeight = UIUtils.WHD()[1];
        int wWidth = UIUtils.WHD()[0];
        // 缩放比
        if (bitWidth <= wWidth && bitHeight <= wHeight) {
            size = 1;
        } else {
            double scale = bitWidth >= bitHeight ? bitWidth / wWidth : bitHeight / wHeight;
            double log = Math.log(scale) / Math.log(2);
            double logCeil = Math.ceil(log);
            size = (int) Math.pow(2, logCeil);
        }
        return size;
    }


}
