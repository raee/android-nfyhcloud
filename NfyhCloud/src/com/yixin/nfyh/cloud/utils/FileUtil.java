/**
 * 
 */
package com.yixin.nfyh.cloud.utils;

import java.io.File;

import android.os.Environment;

/**
 * 文件帮助类
 * 
 * @author MrChenrui
 * 
 */
public class FileUtil
{

	/**
	 * SDCard路径
	 */
	public static File	SDCard	= Environment.getExternalStorageDirectory();

	/**
	 * 检查文件夹是否存在
	 * 
	 * @param path
	 *            文件夹路径
	 * @return
	 */
	public static boolean checkDirExists(String path)
	{
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 在创建文件夹
	 * 
	 * @param path
	 *            不用加Sdcard路径
	 * @return 如果创建成功，返回创建的路径，否则返回空。
	 */
	public static String createSdCardDirs(String path)
	{
		if (checkSdCardState())
		{
			File dataDir = Environment.getExternalStorageDirectory();
			if (!dataDir.exists())
				dataDir.mkdirs();
			path = dataDir.getPath() + "/" + path;
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();
			return file.getPath();
		}
		else
		{
			return null;
		}
	}

	/**
	 * 检查Sdcard状态
	 * 
	 * @return 如果可用返回路径，否则返回空。
	 */
	public static boolean checkSdCardState()
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) // 已经加载内存卡
		{
			return true;
		}
		else
		{
			return false;
		}
		// // 检查Sdcard状态
		// String state = Environment.getExternalStorageState();
		// if (state.equals(Environment.MEDIA_MOUNTED) && SDCard.exists())
		// {
		// // 可以访问Sdcard
		// rootPath = SDCard.getPath();
		// } else if (!new File(rootPath).exists())
		// {
		// // 没有扩展卡
		// rootPath = null;
		// } else
		// {
		// rootPath = null;
		// }
		// return rootPath;
	}

	/**
	 * 获取扩展名
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static String getExtensionName(String path)
	{
		int index = path.lastIndexOf(".");
		if (index > 0)
		{
			return path.substring(index);
		}
		else
		{
			return null;
		}
	}
}
