package com.yixin.nfyh.cloud.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

import com.yixin.nfyh.cloud.BroadcastReceiverFlag;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.OneKeySoSActivity;
import com.yixin.nfyh.cloud.bll.ConfigServer;

/**
 * 核心服务
 * 
 * @author 睿
 * 
 */
public class CoreService extends Service {
	//	public static final String		TYPE_UPLOAD_PHOTO	= "TYPE_UPLOAD_PHOTO";
	private Context					mContext;
	private CoreBroadcastReceiver	receiver;
	private NfyhApplication			app;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		receiver = new CoreBroadcastReceiver();
		// 注册短信广播
		IntentFilter intentFilter = new IntentFilter(BroadcastReceiverFlag.ACTION_REC_SMS);
		intentFilter.setPriority(Integer.MAX_VALUE);
		this.registerReceiver(receiver, intentFilter);
		
		app = (NfyhApplication) getApplication();
		app.showSOSinDesktop();
		ConfigServer config = new ConfigServer(mContext);
		if (config.getBooleanConfig(ConfigServer.KEY_ENABLE_AUTO_RUN)) {
			Log.i("NfyhApplication", "自动启动监测服务。");
			app.connect();
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver); //注销广播
	}
	
	/**
	 * 跌倒信息接受者
	 * 
	 * 
	 * @author Chenrui
	 * 
	 */
	private class CoreBroadcastReceiver extends BroadcastReceiver {
		private Intent	intent;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			mContext = context;
			this.intent = intent;
			String actionName = intent.getAction();
			
			if (actionName.equals(BroadcastReceiverFlag.ACTION_REC_SMS)) {
				receiveSMS();
			}
		}
		
		/**
		 * 接受跌倒报警信息
		 */
		private void receiveSMS() {
			Log.v("cr", "接受跌倒报警信息");
			Bundle bun = intent.getExtras();
			if (bun == null) { return; }
			
			Object[] contents = (Object[]) bun.get("pdus");
			
			for (Object obj : contents) {
				byte[] pdu = (byte[]) obj;
				SmsMessage sms = SmsMessage.createFromPdu(pdu);
				String content = sms.getMessageBody(); // 信息内容
				
				if (content.contains("自动报警") || content.contains("手动报警")) {
					Log.v("cr", "发生跌倒情况");
					if (mContext == null) {
						Log.v("cr", "启动急救失败!");
						return;
					}
					Intent intentSend = new Intent(mContext, OneKeySoSActivity.class);
					intentSend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intentSend.putExtra(OneKeySoSActivity.EXTRA_EVENT_TYPE, "用户发生跌倒情况~");
					mContext.startActivity(intentSend);
				}
				if (content.contains("状态正常") || content.contains("报警通过按键主动解除")) {
					Log.v("cr", "跌倒被解除");
				}
				
			}
		}
		
	}
	
}
