package com.yangshao.image.compross;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yangshao.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author KINCAI
 *
 */
public class ImageUtils {

	public static  File firstCompress(@NonNull File file) {
		int minSize = 60;
		int longSide = 720;
		int shortSide = 1280;

		String filePath = file.getAbsolutePath();
		String thumbFilePath=FileUtil.getImageOutputFile();
		long size = 0;
		long maxSize = file.length() / 5;

		int angle = getImageSpinAngle(filePath);
		int[] imgSize = getImageSize(filePath);
		int width = 0, height = 0;
		if (imgSize[0] <= imgSize[1]) {
			double scale = (double) imgSize[0] / (double) imgSize[1];
			if (scale <= 1.0 && scale > 0.5625) {
				width = imgSize[0] > shortSide ? shortSide : imgSize[0];
				height = width * imgSize[1] / imgSize[0];
				size = minSize;
			} else if (scale <= 0.5625) {
				height = imgSize[1] > longSide ? longSide : imgSize[1];
				width = height * imgSize[0] / imgSize[1];
				size = maxSize;
			}
		} else {
			double scale = (double) imgSize[1] / (double) imgSize[0];
			if (scale <= 1.0 && scale > 0.5625) {
				height = imgSize[1] > shortSide ? shortSide : imgSize[1];
				width = height * imgSize[0] / imgSize[1];
				size = minSize;
			} else if (scale <= 0.5625) {
				width = imgSize[0] > longSide ? longSide : imgSize[0];
				height = width * imgSize[1] / imgSize[0];
				size = maxSize;
			}
		}

		return compress(filePath, thumbFilePath, width, height, angle, size);
	}



	/**
	 * 压缩原图尺寸
	 * obtain the thumbnail that specify the size
	 *
	 * @param imagePath the target image path
	 * @param width     the width of thumbnail
	 * @param height    the height of thumbnail
	 * @return {@link Bitmap}
	 */
	private static Bitmap compress(String imagePath, int width, int height) {

		Log.i("TAG","compress_width---"+width+"compress_height---" + height);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		int outH = options.outHeight; //获取实际宽
		int outW = options.outWidth;  //获取实际宽
		int inSampleSize = 1;


		//当满足width 跟 height 结束递归 得到压缩比值
		if (outH > height || outW > width) {
			int halfH = outH / 2;
			int halfW = outW / 2;

			while ((halfH / inSampleSize) > height && (halfW / inSampleSize) > width) {
				inSampleSize *= 2;
			}
		}

		options.inSampleSize = inSampleSize;

		options.inJustDecodeBounds = false;

		Log.i("TAG","options.outHeight--->" + options.outHeight);
		Log.i("TAG","options.outWidth--->" + options.outWidth);
		Log.i("TAG","options.inSampleSize--->" + options.inSampleSize);

		int heightRatio = (int) Math.ceil(options.outHeight / (float) height);  //向上取整 获取原图比压缩图比值
		int widthRatio = (int) Math.ceil(options.outWidth / (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				options.inSampleSize = heightRatio;
			} else {
				options.inSampleSize = widthRatio;
			}
		}
		options.inJustDecodeBounds = false;
		Log.i("TAG","1options.outHeight--->" + options.outHeight);
		Log.i("TAG","1options.outWidth--->" + options.outWidth);
		Log.i("TAG","1options.inSampleSize--->" + options.inSampleSize);

		return BitmapFactory.decodeFile(imagePath, options);
	}


	/**
	 * 指定参数压缩图片
	 * create the thumbnail with the true rotate angle
	 *
	 * @param largeImagePath the big image path
	 * @param thumbFilePath  the thumbnail path
	 * @param width          width of thumbnail
	 * @param height         height of thumbnail
	 * @param angle          rotation angle of thumbnail
	 * @param size           the file size of image
	 */
	private static File compress(String largeImagePath, String thumbFilePath, int width, int height, int angle, long size) {
		Bitmap thbBitmap = compress(largeImagePath, width, height);

		thbBitmap = rotatingImage(angle, thbBitmap);

		return saveImage(thumbFilePath, thbBitmap, size);
	}


	/**
	 * 保存图片到指定路径
	 * Save image with specified size
	 *
	 * @param filePath the image file save path 储存路径
	 * @param bitmap   the image what be save   目标图片
	 * @param size     the file size of image   期望大小
	 */
	private static File saveImage(String filePath, Bitmap bitmap, long size) {
		File result = new File(filePath.substring(0, filePath.lastIndexOf("/")));

		if (!result.exists() && !result.mkdirs()) return null;

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int options = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);

		while (stream.toByteArray().length / 1024 > size && options > 6) {
			stream.reset();
			options -= 6;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
		}
		bitmap.recycle();

		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(stream.toByteArray());
			fos.flush();
			fos.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new File(filePath);
	}


	/**
	 * 旋转图片
	 * rotate the image with specified angle
	 *
	 * @param angle  the angle will be rotating 旋转的角度
	 * @param bitmap target image               目标图片
	 */
	private static Bitmap rotatingImage(int angle, Bitmap bitmap) {
		//rotate image
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		//create a new image
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}


	/**
	 * obtain the image's width and height
	 *
	 * @param imagePath the path of image
	 */
	public static  int[] getImageSize(String imagePath) {
		int[] res = new int[2];

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 1;
		BitmapFactory.decodeFile(imagePath, options);

		res[0] = options.outWidth;
		res[1] = options.outHeight;

		return res;
	}


	/**
	 * obtain the image rotation angle
	 *
	 * @param path path of target image
	 */
	private static int getImageSpinAngle(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
}
