package com.yixin.nfyh.cloud.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.i.IPushMessageCallback;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.utils.NotificationManager;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;

public class PushNotificationService extends Service implements IPushMessageCallback, Runnable
{
	
	private NfyhApplication		mApplication;
	
	private int					mInterval	= 10;		// 定期检查消息，单位/秒
														
	private NotificationManager	mNotificationManager;
	
	private IPushMessage		mPushMessage;
	
	public void init()
	{
		mPushMessage = NfyhWebserviceFactory.getFactory(getApplicationContext()).getPushMessage();
		mPushMessage.setPushMessageListener(this);
		mNotificationManager = NotificationManager.getManager(getApplicationContext());
		mApplication = (NfyhApplication) getApplication();
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	Handler	handler;
	String	cookie;
	
	@Override
	public void onCreate()
	{
		Log.i("PUshNotificationService", "启动消息推送服务");
		
		init();
		new Thread(this).start();
		handler = new Handler(new Handler.Callback()
		{
			
			@Override
			public boolean handleMessage(Message msg)
			{
				mPushMessage.setCookie(getCookie());
				mPushMessage.check();
				return false;
			}
		});
	}
	
	private String getCookie()
	{
		if (cookie == null)
		{
			cookie = mApplication.getCurrentUser().getCookie();
		}
		return cookie;
	}
	
	@Override
	public void onPushNewMessage(int count, int index, Messages model, Intent intent)
	{
		// 发出通知
		mNotificationManager.notify(model.getTitle(), model.getSummary(), intent);
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				// 不能在线程中再开线程
				Thread.sleep(mInterval * 1000);
				
				if (!mApplication.isLogin())
				{
					continue;
				}
				Message.obtain(handler).sendToTarget();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
