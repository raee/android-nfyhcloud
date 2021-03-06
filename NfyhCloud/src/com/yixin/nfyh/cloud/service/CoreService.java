package com.yixin.nfyh.cloud.service;

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

/**
 * 核心服务
 * 
 * @author 睿
 * 
 */
public class CoreService extends AlarmService {
	private Context mContext;
	private CoreBroadcastReceiver mSMSReceiver;
	private NfyhApplication mApplication;
	private CoreBinder binder;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new CoreBinder(this);
		mContext = getApplicationContext();
		mApplication = (NfyhApplication) getApplication();
		mApplication.showSOSinDesktop();
		regReceiver();
		startMonitor();
	}

	/**
	 * 开启设备监测
	 */
	public void startMonitor() {
		ConfigServer config = new ConfigServer(mContext);
		if (config.getBooleanConfig(ConfigServer.KEY_ENABLE_AUTO_RUN)) {
			Log.i("NfyhApplication", "自动启动监测服务。");
			mApplication.connect();
		}
	}

	/**
	 * 注册短信广播
	 */
	void regReceiver() {

		mSMSReceiver = new CoreBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				BroadcastReceiverFlag.ACTION_REC_SMS);
		intentFilter.setPriority(Integer.MAX_VALUE);

		this.registerReceiver(mSMSReceiver, intentFilter);
	}

	/**
	 * 注销广播
	 */
	void unregReceiver() {
		unregisterReceiver(mSMSReceiver); //
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregReceiver();
		mApplication.disconnect(); // 服务已经关闭，断开设备连接。
	}

}
