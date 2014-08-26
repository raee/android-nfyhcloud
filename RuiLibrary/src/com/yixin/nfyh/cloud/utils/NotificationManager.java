package com.yixin.nfyh.cloud.utils;

import java.util.UUID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 通知管理
 * 
 * @author Chenrui
 * 
 */
public class NotificationManager
{

	private Context							mContext		= null;

	private android.app.NotificationManager	mNotification	= null;

	private Intent							mDefalutIntent	= null;

	private static NotificationManager		Manager;

	private static int getNotificationId()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.hashCode();
	}

	public static NotificationManager getManager(Context context)
	{
		if (Manager == null)
			Manager = new NotificationManager(context);
		return Manager;
	}

	private NotificationManager(Context context)
	{
		this.mContext = context.getApplicationContext();
		this.mNotification = (android.app.NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notify(int icon, String title, String content,
			boolean enableClean, Intent intent)
	{
		Notification notice = new Notification();
		notice.icon = icon;
		notice.tickerText = title;
		notice.defaults = Notification.DEFAULT_ALL;
		notice.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, 0);
		notice.setLatestEventInfo(mContext, title, content, contentIntent);
		mNotification.notify(getNotificationId(), notice);
	}

	public void notify(String title, String content, Intent intent)
	{
		if (intent == null)
			intent = mDefalutIntent;
		this.notify(mContext.getApplicationInfo().icon, title, content, true,
				intent);
	}

	/**
	 * 清除所有通知
	 */
	public void clear()
	{
		mNotification.cancelAll();
	}
}
