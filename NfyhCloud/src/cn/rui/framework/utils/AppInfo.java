package cn.rui.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.WindowManager;

public class AppInfo
{

	private Context			mContext;

	private WindowManager	mManager	= null;

	public AppInfo(Context context)
	{
		this.mContext = context;
		mManager = (WindowManager) this.mContext
				.getSystemService(Context.WINDOW_SERVICE);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public int getScreenWidth()
	{
		return mManager.getDefaultDisplay().getWidth();
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @return
	 */
	public int getScreenHeight()
	{
		return mManager.getDefaultDisplay().getHeight();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public String getModel()
	{
		return android.os.Build.MODEL;
		// String phoneInfo = "Product: " + android.os.Build.PRODUCT;
		// phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
		// phoneInfo += ", TAGS: " + android.os.Build.TAGS;
		// phoneInfo += ", VERSION_CODES.BASE: "
		// + android.os.Build.VERSION_CODES.BASE;
		// phoneInfo += ", MODEL: " + android.os.Build.MODEL;
		// phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;
		// phoneInfo += ", VERSION.RELEASE: " +
		// android.os.Build.VERSION.RELEASE;
		// phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
		// phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
		// phoneInfo += ", BRAND: " + android.os.Build.BRAND;
		// phoneInfo += ", BOARD: " + android.os.Build.BOARD;
		// phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
		// phoneInfo += ", ID: " + android.os.Build.ID;
		// phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
		// phoneInfo += ", USER: " + android.os.Build.USER;
		// return phoneInfo;
	}

	/**
	 * 获取安卓版本
	 * 
	 * @return
	 */
	public String getSDKVersion()
	{
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取内置SDCard大小
	 * 
	 * @return
	 */
	public String getSDCardSize()
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			File sdcard = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcard.getPath());
			int size = sf.getBlockSize(); // 每一块占用的空间
			long avail = (long) sf.getAvailableBlocks() * size; // 可用空间
			long count = (long) sf.getBlockCount() * size;// 总空间
			String sdcardSize = Formatter.formatFileSize(mContext, count); // 总大小
			String sdcardAvail = Formatter.formatFileSize(mContext, avail);
			return sdcardSize + "(可用：" + sdcardAvail + ",已用："
					+ Formatter.formatFileSize(mContext, count - avail) + ")";
		}
		else
		{
			return "null";
		}
	}

	/**
	 * 获取上网类型
	 * 
	 * @return
	 */
	public String getConnectState()
	{
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo == null)
		{
			return "没打开网络连接";
		}
		switch (netInfo.getType())
		{
			case ConnectivityManager.TYPE_WIFI:
				return "WIFI";
			case ConnectivityManager.TYPE_MOBILE:
				return "移动";
			case ConnectivityManager.TYPE_BLUETOOTH:
				return "蓝牙";
			default:
				break;
		}
		return netInfo.getTypeName();
	}

	/**
	 * 获取手机厂商
	 * 
	 * @return
	 */
	public String getOME()
	{
		return android.os.Build.BRAND;
	}

	/**
	 * 获取可用内存大小
	 * 
	 * @return
	 */
	public String getAvailMemory()
	{
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo memory = new MemoryInfo();
		am.getMemoryInfo(memory);
		return Formatter.formatFileSize(mContext, memory.availMem); // 可用内存
	}

	/**
	 * 获取手机内存大小
	 * 
	 * @return
	 */
	public String getTotalMemory()
	{
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try
		{
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
			arrayOfString = str2.split("\\s+");
			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();
		}
		catch (Exception e)
		{
		}
		return Formatter.formatFileSize(mContext, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/**
	 * 获取手机CPU信息
	 * 
	 * @return [0]CPU型号，[1]CPU频率
	 */
	public String[] getCpuInfo()
	{
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };
		String[] arrayOfString;
		try
		{
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++)
			{
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		}
		catch (IOException e)
		{
		}
		return cpuInfo;
	}

	/**
	 * 版本
	 * 
	 * @return 版本号
	 */
	public String getVersion()
	{
		String version = "0.0";
		try
		{
			PackageManager manager = this.mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					this.mContext.getPackageName(), 0);
			version = info.versionName;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return version;
	}

	@Override
	public String toString()
	{
		String[] cpu = getCpuInfo();
		StringBuilder sb = new StringBuilder();
		append(sb, "当前程序版本", getVersion());
		append(sb, "安卓系统版本", getSDKVersion());
		append(sb, "手机型号", getModel());
		append(sb, "厂商", getOME());
		append(sb, "CPU", cpu[0]);
		append(sb, "CPU频率", cpu[1]);
		append(sb, "屏幕大小", getScreenWidth() + "*" + getScreenHeight());
		append(sb, "内存", getTotalMemory() + "(可用：" + getAvailMemory() + ")");
		append(sb, "SD卡容量", getSDCardSize());
		append(sb, "当前网络", getConnectState());
		return sb.toString();
	}

	private void append(StringBuilder sb, String key, String val)
	{
		sb.append(key);
		sb.append(":");
		sb.append(val);
		sb.append("\r\n");
	}
}
