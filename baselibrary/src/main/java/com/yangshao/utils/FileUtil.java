/*
 * 
 * Copyright (c) 2015, alipay.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yangshao.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;

import static com.yangshao.utils.DateUtil.getCurrentDate;

/**
  * @author: gyymz1993
  * 创建时间：2017/3/27 20:19
  * @version
  *
 **/
public class FileUtil {

	/**
	 * 
	 * copy file
	 * 
	 * @param src
	 *            source file
	 * @param dest
	 *            target file
	 * @throws IOException
	 */
	public static void copyFile(File src, File dest) throws IOException {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			if (!dest.exists()) {
				dest.createNewFile();
			}
			inChannel = new FileInputStream(src).getChannel();
			outChannel = new FileOutputStream(dest).getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

	/**
	 * delete file
	 * 
	 * @param file
	 *            file
	 * @return true if delete success
	 */
	public static boolean deleteFile(File file) {
		if (!file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteFile(f);
			}
		}
		return file.delete();
	}



	/*
	*  创建压缩图片路径   放入缓存目录
	*
	*  路径：/data/data/com.lsjr.zizisteward/cache/
	*
	* */

	/** 图片存放根目录  /storage/emulated/0/jpeg_picture/  */
	public final static String TEMPPATH = Environment
			.getExternalStorageDirectory().getPath() + "/jpeg_picture/";

	public static File getTempPahtFile(){
		File file =new File(TEMPPATH);
		if (!file.exists()){
			L_.e("文件夹不存在");
		}
		return file;
	}

	public static File createCompressImageCacheFile(){
		File file =new File(TEMPPATH);
		if (!file.exists()){
			if (!file.mkdirs()){
				L_.e("创建文件夹失败  可能是权限问题吧");
				return null;
			}
		}

		String currentTime=DateUtil.getCurrentDate("ddMMyyyy_HHmm");
		String imageName="MI_"+ currentTime ;
		File tempFile = null;
		try {
			tempFile = File.createTempFile(imageName, ".jpg", file);
			if(!tempFile.exists()){
				if(tempFile.mkdirs()){
					L_.e("创建缓存文件成功，路径："+ TEMPPATH);
				}else {
					new Exception("创建文件夹失败  可能是权限问题吧");
				}
			}else {
				L_.e("缓存目录已存在，路径："+ TEMPPATH);
			}
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}



	/**
	 * 得到一个文件输出路径
	 * */
	public static String getImageOutputFile(){
		File file =new File(TEMPPATH);
		if (!file.exists()){
			if (!file.mkdirs()){
				L_.e("创建文件夹失败  可能是权限问题吧");
				return null;
			}
		}
		String currentTime=DateUtil.getCurrentDate("ddMMyyyy_HHmm");
		String imageName="MI_"+ currentTime+".jpg" ;
		return file.getAbsolutePath() + File.separator +imageName;
	}

	public  static File getTempFile(){
		String iTempFileNameString = "temp";
		File dir = Environment.getExternalStorageDirectory();
		File tempFile = null;
		try {
			tempFile = File.createTempFile(iTempFileNameString, ".jpg", dir);
			if(!tempFile.exists()){
				if(!tempFile.mkdirs()){
					return null;
				}
			}
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}

}
