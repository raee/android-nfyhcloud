package com.yixin.nfyh.cloud.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;

import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.receiver.PullMessageReceiver;

public class CoreBinder extends Binder {
	private int mCheckMessageTimeSpan = 3000; // 消息推送监听间隔
	private int mCheckMessageId = 13800; // 消息通知id
	private String mPullMessageAction = "com.yixin.nfyh.cloud.action.pullmessage";
	private ConfigServer mConfig;
	private AlarmManager mAlarmManager;
	private Context mContext;
	private PullMessageReceiver mPullMessageReceiver;

	public CoreBinder(Context context) {
		this.mContext = context;
		mConfig = new ConfigServer(mContext);
		mAlarmManager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);

		// 消息推送广播
		mPullMessageReceiver = new PullMessageReceiver();
		mContext.registerReceiver(mPullMessageReceiver, new IntentFilter(
				mPullMessageAction));

		startPullMessage();
	}

	public void stopPullMessage() {
		Intent intent = new Intent(mPullMessageAction);
		PendingIntent operation = PendingIntent.getBroadcast(mContext,
				mCheckMessageId, intent, 0);
		mAlarmManager.cancel(operation);
	}

	// 开启消息监听服务
	public void startPullMessage() {

		// 允许消息推送
		if (mConfig.getBooleanConfig(ConfigServer.KEY_ENABLE_PULLMSG)) {

			Intent intent = new Intent(mPullMessageAction);
			PendingIntent operation = PendingIntent.getBroadcast(mContext,
					mCheckMessageId, intent, 0);

			mAlarmManager.cancel(operation); // 先取消上次遗留的
			mAlarmManager.setRepeating(AlarmManager.RTC,
					System.currentTimeMillis(), mCheckMessageTimeSpan,
					operation);
		}
	}

	public void destroy() {
		mContext.unregisterReceiver(mPullMessageReceiver);
	}
}
