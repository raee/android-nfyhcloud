/**
 * 
 */
package com.yixin.nfyh.cloud.server;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 程序为处理的异常
 * 
 * @author MrChenrui
 * 
 */
public class NfyhCloudUnHanderException implements UncaughtExceptionHandler
{
	
	private UncaughtExceptionHandler	defaultHandler;
	
	private Context						context;
	
	private ILog						log	= LogUtil.getLog();
	
	private String						tag	= "SystemRuntime";
	
	public NfyhCloudUnHanderException(Context context)
	{
		this.context = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	public static void init(Context context)
	{
		new NfyhCloudUnHanderException(context);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		if (!handleException(ex) && defaultHandler != null)
		{
			// 如果用户没有处理则让系统默认的异常处理器来处理
			defaultHandler.uncaughtException(thread, ex);
		}
		else
		{
			try
			{
				Thread.sleep(3000);
				// 退出程序
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			catch (InterruptedException e)
			{
				log.setExcetion(tag, e);
			}
		}
	}
	
	/**
	 * 用户处理异常
	 * 
	 * @param ex
	 * @return
	 */
	private boolean handleException(Throwable ex)
	{
		if (ex == null)
		{
			return false;
		}
		try
		{
			String msg = Log.getStackTraceString(ex);
			log.error(tag, msg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.setExcetion(tag, e);
		}
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				Looper.prepare();
				Toast.makeText(context, "南方院后云服务，程序异常退出。", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
		return true;
	}
}
