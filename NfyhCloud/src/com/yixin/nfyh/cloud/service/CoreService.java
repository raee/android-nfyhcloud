package com.yixin.nfyh.cloud.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.rae.core.alarm.AlarmService;
import com.yixin.nfyh.cloud.BroadcastReceiverFlag;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.receiver.CoreBroadcastReceiver;
import com.yixin.nfyh.cloud.receiver.PullMessageReceiver;

/**
 * 核心服务
 * 
 * @author 睿
 * 
 */
public class CoreService extends AlarmService {
	private Context					mContext;
	private CoreBroadcastReceiver	mSMSReceiver;
	private NfyhApplication			mApplication;
	private PullMessageReceiver		mPullMessageReceiver;
	private int						mCheckMessageTimeSpan	= 3000;										// 消息推送监听间隔
	private int						mCheckMessageId			= 13800;
	private String					mPullMessageAction		= "com.yixin.nfyh.cloud.action.pullmessage";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		mApplication = (NfyhApplication) getApplication();
		mApplication.showSOSinDesktop();
		
		regReceiver();
		startMonitor();
		startPullMessage();
	}
	
	// 开启设备监测
	private void startMonitor() {
		ConfigServer config = new ConfigServer(mContext);
		if (config.getBooleanConfig(ConfigServer.KEY_ENABLE_AUTO_RUN)) {
			Log.i("NfyhApplication", "自动启动监测服务。");
			mApplication.connect();
		}
	}
	
	// 开启消息监听服务
	private void startPullMessage() {
		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(mPullMessageAction);
		PendingIntent operation = PendingIntent.getBroadcast(mContext, mCheckMessageId, intent, 0);
		
		alarmManager.cancel(operation); // 先取消上次遗留的
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), mCheckMessageTimeSpan, operation);
	}
	
	void regReceiver() {
		
		// 注册短信广播
		mSMSReceiver = new CoreBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BroadcastReceiverFlag.ACTION_REC_SMS);
		intentFilter.setPriority(Integer.MAX_VALUE);
		
		// 消息推送广播
		mPullMessageReceiver = new PullMessageReceiver();
		
		this.registerReceiver(mPullMessageReceiver, new IntentFilter(mPullMessageAction));
		this.registerReceiver(mSMSReceiver, intentFilter);
	}
	
	void unregReceiver() {
		unregisterReceiver(mSMSReceiver); //注销广播
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregReceiver();
	}
	
}
