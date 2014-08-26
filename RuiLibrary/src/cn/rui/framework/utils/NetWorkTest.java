package cn.rui.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetWorkTest
{
	public static final int		TYPE_NONE			= -1;
	public static final int		TYPE_MOBILE			= 0;
	public static final int		TYPE_WIFI			= 1;
	public static final int		TYPE_MOBILE_CMNET	= 2;
	public static final int		TYPE_MOBILE_CMWAP	= 3;
	private Context				context				= null;

	private ConnectivityManager	net;
	private WifiManager			mWifiManager;
	private NetworkInfo			currentInfo;

	public NetWorkTest(Context context)
	{
		this.context = context;
		net = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		currentInfo = net.getActiveNetworkInfo();
	}

	/**
	 * 获取当前活动的网络类型
	 * 
	 * @return 参考NetWorkTest的常量值
	 */
	public int getActiveNetWorkType()
	{
		int type = TYPE_NONE;
		if (currentInfo == null)
		{
			return type;
		}

		type = currentInfo.getType();

		if (type == ConnectivityManager.TYPE_MOBILE)
		{
			if (currentInfo.getExtraInfo().equals("cmwap"))
				type = TYPE_MOBILE_CMWAP;
			else
				type = TYPE_MOBILE_CMNET;
		}

		return type;
	}

	/**
	 * 是否为Wifi连接
	 * 
	 * @return
	 */
	public boolean isWIFI()
	{
		return getActiveNetWorkType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 是否为移动网络
	 * 
	 * @return
	 */
	public boolean is3G()
	{
		return getActiveNetWorkType() == ConnectivityManager.TYPE_MOBILE;
	}

	/**
	 * 是否能打开了网络连接，不能知道能不能上网
	 * 
	 * @return
	 */
	public boolean isConnected()
	{
		if (net == null)
			return false;

		for (NetworkInfo info : net.getAllNetworkInfo())
		{
			if (info.getState() == NetworkInfo.State.CONNECTED
					&& info.isAvailable())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否能上网，无论使用哪一种网络
	 * 
	 * @return
	 */
	public boolean isAvailable()
	{
		if (isConnected())
		{
			return true;
			// // 请求一个网络
			// HttpUtil http = new HttpUtil("http://www.baidu.com");
			//
			// http.request(null);
			// http.setResponseListener(this);
			//
			// int timeout = 1000;
			//
			// // 网络超时处理
			// new Timer().schedule(new TimerTask()
			// {
			//
			// @Override
			// public void run()
			// {
			// isResonsed = true;
			// }
			// }, timeout);
			//
			// // 等待网络结果
			// while (!isResonsed)
			// {
			// }

			// return available;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 打开网络异常提示，并跳转置设置页面
	 */
	public void openNetWorkErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("网络异常！");
		builder.setMessage("当前网络不可用，是否设置网络连接？");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				context.startActivity(new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS));
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}

	/**
	 * 打开，或关闭Wifi
	 */
	public void openWIFI(boolean value)
	{
		try
		{
			if (!this.mWifiManager.isWifiEnabled())
			{
				this.mWifiManager.setWifiEnabled(value);
			}
		}
		catch (Exception e)
		{
			Log.e("cr", "打开wifi失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 打开或关闭移动网络
	 * 
	 * @param value
	 */
	public void open3G(boolean value)
	{
		Class<?> conMgrClass = null; // ConnectivityManager类
		Field iConMgrField = null; // ConnectivityManager类中的字段
		Object iConMgr = null; // IConnectivityManager类的引用
		Class<?> iConMgrClass = null; // IConnectivityManager类
		Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法
		try
		{
			// 取得ConnectivityManager类
			conMgrClass = Class.forName(net.getClass().getName());
			// 取得ConnectivityManager类中的对象mService
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// 设置mService可访问
			iConMgrField.setAccessible(true);
			// 取得mService的实例化类IConnectivityManager
			iConMgr = iConMgrField.get(net);
			// 取得IConnectivityManager类
			iConMgrClass = Class.forName(iConMgr.getClass().getName());
			// 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			// 设置setMobileDataEnabled方法可访问
			setMobileDataEnabledMethod.setAccessible(true);
			// 调用setMobileDataEnabled方法
			setMobileDataEnabledMethod.invoke(iConMgr, value);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 关闭WIFI
	 */
	public void closeWIFI()
	{
		try
		{
			if (!this.mWifiManager.isWifiEnabled())
			{
				this.mWifiManager.setWifiEnabled(false);
			}
		}
		catch (Exception e)
		{
			Log.e("cr", "打开wifi失败！");
			e.printStackTrace();
		}
	}

	void show(Object msg)
	{
		Log.i("nettest", msg.toString());
	}

}
